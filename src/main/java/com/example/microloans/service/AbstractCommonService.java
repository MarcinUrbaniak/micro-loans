package com.example.microloans.service;

import com.example.microloans.common.MsgSource;

public abstract class AbstractCommonService {
    protected MsgSource msgSource;

    public AbstractCommonService(MsgSource msgSource) {
        this.msgSource = msgSource;
    }
}
