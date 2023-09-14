package ru.railshop.onlineshop.filter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class CharsetFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
