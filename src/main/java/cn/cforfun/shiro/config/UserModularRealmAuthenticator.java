package cn.cforfun.shiro.config;

import cn.cforfun.shiro.model.dto.UserToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Create By C  2019-09-14 15:46
 */
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {


    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        UserToken userToken = (UserToken) authenticationToken;
        // 登录类型
        String loginType = userToken.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        List<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().contains(loginType))
            typeRealms.add(realm);
        }
        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            return doSingleRealmAuthentication(typeRealms.get(0), userToken);
        }
            return doMultiRealmAuthentication(typeRealms, userToken);

    }

}
