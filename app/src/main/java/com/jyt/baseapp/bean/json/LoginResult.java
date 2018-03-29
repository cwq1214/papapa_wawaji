package com.jyt.baseapp.bean.json;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class LoginResult {
    private String userId;
    private String tokenSession;
    private String inviteShare;
    private String nickname;

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

    public String getInviteShare() {
        return inviteShare;
    }

    public void setInviteShare(String inviteShare) {
        this.inviteShare = inviteShare;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "userId='" + userId + '\'' +
                ", tokenSession='" + tokenSession + '\'' +
                '}';
    }
}
