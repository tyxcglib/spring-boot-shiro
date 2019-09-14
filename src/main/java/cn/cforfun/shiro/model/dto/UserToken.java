package cn.cforfun.shiro.model.dto;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Create By C  2019-09-14 15:45
 */
public class UserToken extends UsernamePasswordToken {

    private String loginType;

    public UserToken(String username, String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
