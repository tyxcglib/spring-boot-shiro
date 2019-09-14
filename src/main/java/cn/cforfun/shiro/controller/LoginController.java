package cn.cforfun.shiro.controller;

import cn.cforfun.shiro.model.SysRole;
import cn.cforfun.shiro.model.UserInfo;
import cn.cforfun.shiro.model.dto.UserToken;
import cn.cforfun.shiro.sevice.RoleService;
import cn.cforfun.shiro.sevice.UserInfoService;
import cn.cforfun.shiro.util.GithubRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class LoginController {

    @RequestMapping("/tologin")
    public String tologin(){
        return "/login";
    }

    @RequestMapping({"/","/index"})
    public String index(){
        return"/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request,String userName, String password) throws Exception{
        UserToken token = new UserToken(userName,password,"Form");
        try {
            SecurityUtils.getSubject().login(token);
            return "/index";
        } catch (UnknownAccountException e) {
            request.setAttribute("msg","用户不存在");
        }catch (IncorrectCredentialsException e){
            request.setAttribute("msg","密码不正确");
        }
        return "forward:/tologin";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        return "403";
    }

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleService roleService;

    /**
     * github 登陆
     */
    @RequestMapping("/login/github")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = GithubRequest.getAuthRequest();
        // 重定向到授权页面
        response.sendRedirect(authRequest.authorize());
    }

    @RequestMapping("/github/login")
    public String login(String code) {
        AuthRequest authRequest = GithubRequest.getAuthRequest();
        AuthResponse authResponse = authRequest.login(code);
        JSONObject response = JSON.parseObject(JSON.toJSONString(authResponse)).getJSONObject("data");
        String uuid = response.get("uuid").toString();
        String nickname = response.get("nickname").toString();
        UserInfo user = userInfoService.findByGithubId(uuid);
        /**
         *  当用户不存在
         */
        if(user==null){
            SysRole role = roleService.findById(2);
            user = new UserInfo();
            user.setName(nickname);
            user.setPassword(nickname);
            user.setRoleList(Arrays.asList(role));
            Long salt = System.currentTimeMillis();
            user.setSalt(salt.toString());
            user.setUsername(nickname);
            user.setGithubid(uuid);
            String encodePasswd = new Md5Hash(nickname, user.getUsername() + user.getSalt(),2).toString();
            user.setPassword(encodePasswd);
            userInfoService.addUser(user);
        }
        // 返回的是用户信息
        UserToken token = new UserToken( user.getUsername(), user.getUsername(), "Github");
        try {
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return "forward:/tologin";
        }
        return "/index";
    }

}
