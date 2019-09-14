package cn.cforfun.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    /**
     * 用户查询.
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    @RequiresRoles("admin")
    public String userInfo(){
        return "userInfo";
    }


    /**
     * 用户添加;
     */
    @RequestMapping("/userAdd")
    public String userInfoAdd(){
        return "userInfoAdd";
    }

    /**
     * 用户删除;
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(){
        return "userInfoDel";
    }
}
