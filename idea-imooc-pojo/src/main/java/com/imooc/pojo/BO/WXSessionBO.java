package com.imooc.pojo.BO;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.pojo.BO
 * @ClassName: WXSessionBO
 * @Author: hulincloud
 * @Description: BO
 * @Date: 2019/5/25 19:10
 * @Version: 1.0
 */
public class WXSessionBO {
    private String session_key;
    private String openid;

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
