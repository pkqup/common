package com.pkqup.commonlibrary.net.exception;

/**
 * Created by 康颢曦 on 2017/11/7.
 */

public enum ApiError {
    SIG_CHECK_WRONG, //签名错误
    SIG_CHECK_EXPIRED, //签名过期
    ERROR_OTHER, //其他错误
    NO_NETWORK_ERROR //无网络错误
}
