package cn.cforfun.shiro.util;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;

/**
 * Create By C  2019-09-14 15:57
 */
public class GithubRequest {

    public static AuthRequest getAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId("7c6591e3f3909b920e1f")
                .clientSecret("93654b56f01e53b3f7539e67bd4db34cba726c1a")
                // github配置的回调映射
                .redirectUri("http://localhost:9003/github/login")
                .build());
    }
}
