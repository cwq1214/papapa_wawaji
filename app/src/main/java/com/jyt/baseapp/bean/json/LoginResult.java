package com.jyt.baseapp.bean.json;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class LoginResult {
    private String userId;
    private String tokenSession;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenSession() {
        return tokenSession;
    }

    public void setTokenSession(String tokenSession) {
        this.tokenSession = tokenSession;
    }
}
