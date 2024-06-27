package com.communify.global.util;

import com.communify.global.dao.BulkInsertable;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;

public final class BulkInsertUtil {

    private BulkInsertUtil() {
    }

    public static <T> void forceBulkInsert(final BulkInsertable<T> bulkInsertable, final Collection<T> collection) {
        try {
            bulkInsertable.bulkInsert(collection);
        } catch (DataIntegrityViolationException ignored) {
            insertEach(bulkInsertable, collection);
        }
    }

    private static <T> void insertEach(final BulkInsertable<T> bulkInsertable, final Collection<T> collection) {
        for (T t : collection) {
            try {
                bulkInsertable.insert(t);
            } catch (DataIntegrityViolationException ignored) {
            }
        }
    }
}
