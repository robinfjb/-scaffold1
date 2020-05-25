package robin.scaffold.baisc.api.bean;

import java.io.Serializable;


public class TokenInfo implements Serializable {
    private String accessToken;
    private long updateTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
