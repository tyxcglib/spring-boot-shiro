package cn.cforfun.shiro.config;

import cn.cforfun.shiro.model.SysPermission;
import cn.cforfun.shiro.model.UserInfo;
import cn.cforfun.shiro.sevice.PermissionService;
import cn.cforfun.shiro.sevice.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FormRealm extends AuthorizingRealm {

    @Resource
    private UserInfoService userInfoService;

    @Autowired
    private PermissionService permissionService;
    /**
     *  授权    principals为用户名
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName = principals.getPrimaryPrincipal().toString();
        UserInfo info = userInfoService.findByUsername(userName);
        Set<String> roleList = info.getRoleList().stream().map(r -> r.getRole()).collect(Collectors.toSet());
        authorizationInfo.setRoles(roleList);

        List<SysPermission> permissionList;
        List<String> stringList=new ArrayList<>();
        // 查权限
        for (String roleName : roleList) {
            permissionList = permissionService.findPerssionByRoleName(roleName);
            stringList.addAll(permissionList.stream().map(p -> p.getPermission()).collect(Collectors.toList()));
        }
        authorizationInfo.addStringPermissions(stringList);
        return authorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        UserInfo userInfo = userInfoService.findByUsername(username);
        if(userInfo == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getUsername(), //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()) ,//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
