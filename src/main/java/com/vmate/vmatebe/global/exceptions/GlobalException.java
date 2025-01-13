package com.vmate.vmatebe.global.exceptions;

import com.vmate.vmatebe.global.rsData.RsData;
import com.vmate.vmatebe.global.standard.base.Empty;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final RsData<Empty> rsData;

    public GlobalException(String resultCode, String msg) {
        super("resultCode=" + resultCode + ",msg=" + msg);
        this.rsData = RsData.of(resultCode, msg);
    }
}