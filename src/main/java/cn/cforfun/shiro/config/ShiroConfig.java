package cn.cforfun.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//拦截器.
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		filterChainDefinitionMap.put("/login/**", "anon");
		filterChainDefinitionMap.put("/github/**", "anon");
		filterChainDefinitionMap.put("/user/**", "roles[user]");
		filterChainDefinitionMap.put("/admin/**", "perms[admin]");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setLoginUrl("/tologin");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		hashedCredentialsMatcher.setHashIterations(2);
		return hashedCredentialsMatcher;
	}

	/**
	 * 系统自带的Realm管理，主要针对多realm
	 * */
	@Bean
	public ModularRealmAuthenticator modularRealmAuthenticator(){
		//自己重写的ModularRealmAuthenticator
		UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
		modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		return modularRealmAuthenticator;
	}

	@Bean
	public FormRealm myShiroRealm(){
		FormRealm myShiroRealm = new FormRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		// 设置明文比较
//		myShiroRealm.setCredentialsMatcher(new SimpleCredentialsMatcher());
		return myShiroRealm;
	}

	@Bean
	public GithubRealm githubRealm(){
		GithubRealm githubRealm = new GithubRealm();
		githubRealm.setCredentialsMatcher(new SimpleCredentialsMatcher());
		return githubRealm;
	}



	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setAuthenticator(modularRealmAuthenticator());
		List<Realm> realms = new ArrayList<>();
		realms.add(myShiroRealm());
		realms.add(githubRealm());
		securityManager.setRealms(realms);
		return securityManager;
	}

	/**
	 *  开启shiro aop注解支持.
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
