package cn.cforfun.shiro;

import cn.cforfun.shiro.model.SysRole;
import cn.cforfun.shiro.model.UserInfo;
import cn.cforfun.shiro.sevice.RoleService;
import cn.cforfun.shiro.sevice.UserInfoService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShiroApplicationTests {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void addUser(){
        SysRole role = roleService.findById(2);
        UserInfo info = new UserInfo();
        info.setName("vipUser");
        info.setRoleList(Arrays.asList(role));
        Long salt = System.currentTimeMillis();
        info.setSalt(salt.toString());
        info.setUsername("vipUser");
        String encodePasswd = new Md5Hash("vipUser", info.getUsername() + info.getSalt(),2).toString();
        info.setPassword(encodePasswd);
        userInfoService.addUser(info);
    }
}
