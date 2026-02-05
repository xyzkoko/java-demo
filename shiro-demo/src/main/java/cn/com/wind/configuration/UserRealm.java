package cn.com.wind.configuration;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collections;
import java.util.HashSet;

/**
 * 自定义Realm
 * 处理用户认证和授权逻辑
 */
public class UserRealm extends AuthorizingRealm {

    // 用户授权
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 放入角色信息
        authorizationInfo.setRoles(new HashSet<>(Collections.singletonList("Role1")));
        // 放入权限信息
        authorizationInfo.setStringPermissions(new HashSet<>(Collections.singletonList("Permission1")));

        return authorizationInfo;
    }

    // 用户认证
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        // 默认登录成功
        System.out.println("登录成功");
        return new SimpleAuthenticationInfo("admin", "123456", "管理员");
    }
}
