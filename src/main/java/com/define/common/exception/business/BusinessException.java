package com.define.common.exception.business;

/**
 * 这里继承RuntimeException异常
 **/
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -981757883236123952L;

    /**
     * 错误码、错误信息
     **/
    private String errorMsg;
    private Object errorObj;
    private int errorCode = 500;

    /**
     * @param cause
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * @param errorCode
     */
    public BusinessException(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @param message
     * @param cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param errorCode
     * @param cause
     */
    public BusinessException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode
     * @param errorMsg
     */
    public BusinessException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * @param errorMsg
     * @param errorObj
     */
    public BusinessException(String errorMsg, Object errorObj) {
        this.errorMsg = errorMsg;
        this.errorObj = errorObj;
    }

    /**
     * @param errorCode
     * @param errorMsg
     * @param errorObj
     */
    public BusinessException(int errorCode, String errorMsg, Object errorObj) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorObj = errorObj;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorObj() {
        return errorObj;
    }

    public void setErrorObj(Object errorObj) {
        this.errorObj = errorObj;
    }
}
