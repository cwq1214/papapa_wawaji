package com.jyt.baseapp.bean.json;

/**
 * @author LinWei on 2017/11/13 17:16
 */
public class HomeToyResult {
    private String toyId;
    private String toyImg;
    private String toyName;
    private String needPay;
    private String leisure;
    private String useing;

    public String getToyId() {
        return toyId;
    }

    public void setToyId(String toyId) {
        this.toyId = toyId;
    }

    public String getToyImg() {
        return toyImg;
    }

    public void setToyImg(String toyImg) {
        this.toyImg = toyImg;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public String getNeedPay() {
        return needPay;
    }

    public void setNeedPay(String needPay) {
        this.needPay = needPay;
    }

    public String getLeisure() {
        return leisure;
    }

    public void setLeisure(String leisure) {
        this.leisure = leisure;
    }

    public String getUseing() {
        return useing;
    }

    public void setUseing(String useing) {
        this.useing = useing;
    }

    public static class HomeToyDetailData{
        private String machineStatus;
        private String machineId;

        public String getMachineStatus() {
            return machineStatus;
        }

        public void setMachineStatus(String machineStatus) {
            this.machineStatus = machineStatus;
        }

        public String getMachineId() {
            return machineId;
        }

        public void setMachineId(String machineId) {
            this.machineId = machineId;
        }
    }
}
