package com.hiynn.cms.common.shiro;

import com.hiynn.cms.entity.SysMenuEntity;
import com.hiynn.cms.entity.SysRoleEntity;
import com.hiynn.cms.entity.SysUserEntity;
import com.hiynn.cms.service.SysMenuService;
import com.hiynn.cms.service.SysRoleService;
import com.hiynn.cms.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * shiroRealm 注入顶级级别 200 shiro  核心类 继承AuthorizingRealm 后登录鉴权都依赖本类
 *
 * @author 张朋
 * @date 2019/10/28 17:13
 */
@Component
@Slf4j
@Order(203)
public class ShiroRealm extends AuthorizingRealm {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;

    @Autowired
    public ShiroRealm(SysUserService sysUserService, SysRoleService sysRoleService, SysMenuService sysMenuService, HashedCredentialsMatcher hashedCredentialsMatcher) {
        log.info("ShiroRealm 初始化 注入配置");
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.sysMenuService = sysMenuService;
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    /**
     * 身份认证，验证用户输入的账号和密码是否正确
     *
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author 张朋
     * @date 2019/10/31 16:29
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("Shiro身份验证开始");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String userName = token.getUsername();
        log.info("登录用户：{}", userName);
        // 获取用户实体
        SysUserEntity user = sysUserService.selectByAccount(userName);

        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }

        if (user.getDataStatus() == 0) {
            throw new LockedAccountException("用户被锁定");
        }

        log.debug("用户信息：{}", user.toString());

        // 根据账号获取的实体 + 用户密码 进行与用户输入的密码做比较 来验证登录
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
    }


    /**
     * 权限配置
     *
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author 张朋
     * @date 2019/10/31 16:28
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("shiro 权限配置");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 当前用户
        SysUserEntity user = (SysUserEntity) principalCollection.getPrimaryPrincipal();
        // 查询用户角色
        List<SysRoleEntity> sysRoleEntities = sysRoleService.listByUserId(user.getId());
        // 获取角色集合
        Set<String> roleIds = sysRoleEntities.stream().map(SysRoleEntity::getId).collect(Collectors.toSet());
        // 加入
        authorizationInfo.addRoles(roleIds);
        // 查询用户权限
        List<SysMenuEntity> sysMenuEntities = sysMenuService.listPermsByUser();
        // 转换菜单权限集合
        Set<String> perms = new HashSet<>();
        for (SysMenuEntity sysMenuEntity : sysMenuEntities) {
            String tmp = sysMenuEntity.getPerms();
            if (StringUtils.isNotBlank(tmp)) {
                perms.add(tmp);
            }
        }
        // 加入
        authorizationInfo.addStringPermissions(perms);
        return authorizationInfo;
    }
}
