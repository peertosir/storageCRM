package dev.peertosir.storagecrm.common;

import dev.peertosir.storagecrm.exception.http.LimitOffsetException;

public class LimitOffset {
    private final int limit;
    private final int offset;

    public LimitOffset(int limit, int offset) {
        if (limit <= 0) {
            throw LimitOffsetException.invalidLimit();
        }

        if (offset < 0) {
            throw LimitOffsetException.invalidOffset();
        }

        this.limit = limit;
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LimitOffset that = (LimitOffset) o;

        if (limit != that.limit) return false;
        return offset == that.offset;
    }

    @Override
    public int hashCode() {
        int result = limit;
        result = 31 * result + offset;
        return result;
    }
}
