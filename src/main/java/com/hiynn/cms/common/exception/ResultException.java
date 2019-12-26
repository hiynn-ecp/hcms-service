package com.hiynn.cms.common.exception;

import com.hiynn.component.common.core.Result;

/**
 * @author 张朋
 * @date 2019/11/19 10:48
 */
public class ResultException extends RuntimeException {

    private static final long serialVersionUID = 5283118736885232417L;
    /**
     * result 结果
     */
    private Result result;

    /**
     * 设置result
     *
     * @param result
     * @return
     */
    public ResultException setResult(Result result) {
        this.result = result;
        return this;
    }

    public Result getResult() {
        return this.result;
    }

    public ResultException() {
        super();
    }

    public ResultException(String message) {
        super(message);
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultException(Throwable cause) {
        super(cause);
    }

    protected ResultException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
