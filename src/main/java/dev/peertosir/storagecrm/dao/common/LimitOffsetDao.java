package dev.peertosir.storagecrm.dao.common;

import dev.peertosir.storagecrm.common.LimitOffset;

public class LimitOffsetDao {
    public static String getLimitAndOffsetQuery(LimitOffset limitOffset) {
        return String.format(" LIMIT %s OFFSET %s", limitOffset.getLimit(), limitOffset.getOffset());
    }
}
