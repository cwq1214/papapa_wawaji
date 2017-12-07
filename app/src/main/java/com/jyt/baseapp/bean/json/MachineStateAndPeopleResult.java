package com.jyt.baseapp.bean.json;

import java.util.List;

/**
 * Created by chenweiqi on 2017/12/1.
 */

public class MachineStateAndPeopleResult {
    private String machineStatus;//1代表被占用，0代表空闲
    private String userCount;//当前人数
    private List<User> user;//当前房间用户

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }


    public String getRoomPeopleCount(){
        if (user!=null){
            return user.size()+"人";
        }
        return 0+"人";
    }
}
