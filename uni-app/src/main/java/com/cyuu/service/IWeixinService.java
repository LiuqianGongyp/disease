package com.cyuu.service;

import com.cyuu.dataObject.WXAuth;
import com.cyuu.utils.Result;

public interface IWeixinService {
    String getSessionId(String code);

    Result authLogin(WXAuth wxAuth);
}
