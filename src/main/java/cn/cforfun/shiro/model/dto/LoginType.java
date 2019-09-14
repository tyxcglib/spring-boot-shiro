package cn.cforfun.shiro.model.dto;

/**
 * Create By C  2019-09-14 15:43
 */
public enum LoginType {
    form("form"),
    github("github");

    String type;

    LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }}
