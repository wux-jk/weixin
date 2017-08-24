package org.jk.weixin.entity;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/8/22.
 * AccessToken的数据模型
 */
public class AccessToken implements Serializable {

    //获取到的凭证
    private String accessToken;
    //凭证有效时间，单位：秒
    private int expiresin;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(int expiresin) {
        this.expiresin = expiresin;
    }

}
