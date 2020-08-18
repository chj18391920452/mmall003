package com.chj.exception;

import com.chj.enums.ResultEnum;

public class MallException extends RuntimeException {
    public MallException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
    }
}
