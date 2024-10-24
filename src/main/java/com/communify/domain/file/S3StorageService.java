package com.communify.domain.file;

import com.communify.domain.file.dto.UploadFile;
import com.communify.domain.file.exception.FileDeleteFailException;
import com.communify.domain.file.exception.FileUploadFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    public static final String SLASH = "/";

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void storeFiles(File directory, List<UploadFile> uploadFileList) {
        String prefix = resolveDirectoryPath(directory);

        uploadFileList.forEach(uploadFile -> {
            PutObjectRequest por = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(prefix + uploadFile.getFullStoredFilename())
                    .contentType(uploadFile.getContentType())
                    .contentLength(uploadFile.getSize())
                    .build();

            try {
                s3Client.putObject(por, RequestBody.fromBytes(uploadFile.getBytes()));
            } catch (AwsServiceException | SdkClientException | IOException e) {
                throw new FileUploadFailException(new File(prefix), uploadFile, e);
            }
        });
    }

    @Override
    public void deleteFiles(File directory) {
        String prefix = resolveDirectoryPath(directory);

        try {
            ListObjectsResponse lor = listObjects(prefix);

            List<ObjectIdentifier> objectIdentifierList = lor.contents()
                    .stream()
                    .map(object -> ObjectIdentifier.builder().key(object.key()).build())
                    .toList();

            DeleteObjectsRequest dor = DeleteObjectsRequest.builder()
                    .bucket(bucket)
                    .delete(Delete.builder().objects(objectIdentifierList).build())
                    .build();

            s3Client.deleteObjects(dor);
        } catch (AwsServiceException | SdkClientException e) {
            throw new FileDeleteFailException(new File(prefix), e);
        }
    }

    private ListObjectsResponse listObjects(String prefix) {
        ListObjectsRequest lor = ListObjectsRequest.builder()
                .bucket(bucket)
                .prefix(prefix)
                .build();

        return s3Client.listObjects(lor);
    }

    private String resolveDirectoryPath(File subDirectory) {
        StringBuilder sb = new StringBuilder();

        String path = subDirectory.getPath();
        StringTokenizer st = new StringTokenizer(path, File.separator);

        while (st.hasMoreTokens()) {
            sb.append(st.nextToken()).append(SLASH);
        }

        return sb.toString();
    }
}
