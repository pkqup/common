package com.pkqup.commonlibrary.net.exception;


public class ApiException extends RuntimeException {

    public final static int NO_NETWORK = -1;//无网络

    private int code;

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
