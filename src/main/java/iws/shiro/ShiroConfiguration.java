package iws.shiro;


import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class ShiroConfiguration {
	
	 //将自己的验证方式加入容器
	 @Bean
	 public MyShiroRealm myShiroRealm() {
	        MyShiroRealm myShiroRealm = new MyShiroRealm();
	        return myShiroRealm;
	 }
	 
	//权限管理，配置主要是Realm的管理认证
	 @Bean
	 public DefaultWebSecurityManager securityManager(){
			DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
			securityManager.setRealm(myShiroRealm());
			return securityManager;
	}
	 
	 @Bean
	 public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
	        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
	        shiroFilterFactoryBean.setSecurityManager(securityManager);
	        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
	        //登出
	        filterChainDefinitionMap.put("/logout","logout");
	       
	        //配置不会被拦截的链接 顺序判断
	       // anon:所有url都都可以匿名访问
			filterChainDefinitionMap.put("/static/**", "anon");
			filterChainDefinitionMap.put("/css/**", "anon");
			filterChainDefinitionMap.put("/fonts/**", "anon");
			filterChainDefinitionMap.put("/images/**", "anon");
			filterChainDefinitionMap.put("/js/**", "anon");
			filterChainDefinitionMap.put("/lostpassword", "anon");
			filterChainDefinitionMap.put("/forgetpassword", "anon");
			filterChainDefinitionMap.put("/resetpassword", "anon");
	        //authc:所有url都必须认证通过才可以访问
			filterChainDefinitionMap.put("/**","authc");
	        
	       
	        //登录
	        shiroFilterFactoryBean.setLoginUrl("/login");
	        //首页
	        shiroFilterFactoryBean.setSuccessUrl("/welcome");
	        //错误页面，认证不通过跳转
	        
	         
	        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
	        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	        return shiroFilterFactoryBean;
	    }
	//加入注解的使用，不加入这个注解不生效
	 
	    @Bean
	    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
	        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
	        return authorizationAttributeSourceAdvisor;
	    }
	    
	    @Bean(name = "simpleMappingExceptionResolver")
	    public SimpleMappingExceptionResolver CreateSimpleMappingExceptionResolver(){
	        SimpleMappingExceptionResolver r=new SimpleMappingExceptionResolver();
	        Properties mappings=new Properties();
	        mappings.setProperty("DatabaseException","DatabaseError");
	        mappings.setProperty("UnAuthorizationException","403");
	        r.setExceptionMappings(mappings);
	        r.setDefaultErrorView("error");
	        r.setExceptionAttribute("ex");
	        return r;
	    }
	    

}
