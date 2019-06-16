package com.example.demo.shiro;

import com.example.demo.util.TokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

/**
 *  自定义实现realm
 */
@Service
public class MyRealm extends AuthorizingRealm {

    /**
     *  支持转换JWTToken
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        try{
            String token2 = (String) principals.getPrimaryPrincipal();
            System.out.println("token2" + token2);
            //解析 认证 授权......
            // 添加角色
            //simpleAuthorizationInfo.addRole("");
            // 添加perm 权限
            //simpleAuthorizationInfo.setStringPermissions(); //集合
            //simpleAuthorizationInfo.addStringPermission(""); //单个
        } catch (Exception e){

        }
        return simpleAuthorizationInfo;
    }

    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //获取token2
        String token2 = (String)authenticationToken.getCredentials();
        //解析
        try {
            //TokenUtil.getClaimsFromToken(token2);
            System.out.println("验证通过，恭喜你");
        } catch (Exception e) {
            throw new AuthenticationException("token invalid");
        }
        return  new SimpleAuthenticationInfo(token2,token2,getName());
    }
}
