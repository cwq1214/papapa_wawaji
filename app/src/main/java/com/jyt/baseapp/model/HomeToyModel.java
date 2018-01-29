package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * @author LinWei on 2017/11/13 17:04
 */
public interface HomeToyModel extends BaseModel {
    void getHomeToyData(String count, String type, String sequeue, Callback callback);
}
