package ru.railshop.onlineshop.exception;

public class DaoException extends RuntimeException{
    public DaoException(Throwable e){
        super(e);
    }
}
