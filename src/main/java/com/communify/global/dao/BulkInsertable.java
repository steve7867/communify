package com.communify.global.dao;

import java.util.Collection;

public interface BulkInsertable<T> {

    void bulkInsert(Collection<T> collection);

    void insert(T t);
}
