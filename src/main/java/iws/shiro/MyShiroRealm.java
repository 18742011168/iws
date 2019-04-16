package iws.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


import iws.beans.permission;
import iws.beans.role;
import iws.beans.user;
import iws.service.userService;

public class MyShiroRealm extends AuthorizingRealm{
	@Resource
	private userService userservice;
	
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//System.out.println("配置权限");
        //获取登录用户名
        String username= (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        List<user> userlist = userservice.FindUserByName(username);
        
        user user=userlist.get(0);
        //System.out.println("用户名"+user.getUsername());
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
       // System.out.println("获取角色");
            role role=user.getRole();
            if(role==null) {
            	System.out.println("角色为空");
            }
           // System.out.println("角色名"+role.getRoleName());
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (permission permission:role.getPermissions()) {
                //添加权限
            	//System.out.println("配置权限"+permission.getPermissionName());
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());
            }
        
        return simpleAuthorizationInfo;
    }
	
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String username = authenticationToken.getPrincipal().toString();
        List<user> userlist = userservice.FindUserByName(username);
        
        
        if (userlist.isEmpty()) {
            //这里返回后会报出对应异常
            return null;
        } 
        user user=userlist.get(0);
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        
    }

}
