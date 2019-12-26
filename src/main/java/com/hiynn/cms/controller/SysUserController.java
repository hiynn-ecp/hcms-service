package com.hiynn.cms.controller;

import com.hiynn.cms.common.HCMSConstants;
import com.hiynn.cms.common.aop.syslog.SysLog;
import com.hiynn.cms.common.util.BeanUtils;
import com.hiynn.cms.common.util.IDUtils;
import com.hiynn.cms.common.util.ShiroUtils;
import com.hiynn.cms.dao.ValidatorMapper;
import com.hiynn.cms.entity.SysLogEntity;
import com.hiynn.cms.entity.SysUserEntity;
import com.hiynn.cms.model.dto.UserDTO;
import com.hiynn.cms.model.vo.UserVO;
import com.hiynn.cms.service.SysLogService;
import com.hiynn.cms.service.SysUserService;
import com.hiynn.cms.validator.HCMSValidator;
import com.hiynn.component.common.core.Result;
import com.hiynn.component.requestAddressUtils.RequestAddressUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.Length;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * 用户表
 *
 * @author 张朋
 * @date 2019-10-24 17:19:52
 */
@Api(tags = "C-[用户]-控制器")
@RestController
@RequestMapping("sys/user")
@Slf4j
@Validated
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysLogService sysLogService;
    private final ValidatorMapper validatorMapper;

    @Autowired
    public SysUserController(SysUserService sysUserService,
                             SysLogService sysLogService,
                             HCMSValidator validatorService,
                             ValidatorMapper validatorMapper
    ) {
        this.sysUserService = sysUserService;
        this.sysLogService = sysLogService;
        this.validatorMapper = validatorMapper;
    }


    /**
     * 未登录提示 用户未登录 shiro 返回未登录信息  不在swagger展示 ，内部使用 不限制请求方式 @RequestMapping("unauth") 请不要配置请求方式
     */
    @RequestMapping(value = "unauth")
    @ApiIgnore
    public Result unauth() {
        return HCMSConstants.ResultTemplate.NOT_LOGIN;
    }

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result login(
            @ApiParam(name = "account", value = "用户名", required = true) @RequestParam String account,
            @ApiParam(name = "password", value = "密码", required = true) @RequestParam String password,
            HttpServletRequest request) throws DbMakerConfigException, IOException {

        // 非空校验
        HCMSValidator.isBlank(account, password);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        subject.login(token);

        // 记录登录日志 如果登录失败 不会走到这一步 无需判断
        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setCreateTime(new Date());
        sysLogEntity.setId(IDUtils.getJavaUUID());
        sysLogEntity.setUserAccount(account);
        sysLogEntity.setUserName(sysLogService.getUser().getUserName());
        sysLogEntity.setProduct("C-[用户]-控制器");
        sysLogEntity.setOperation("用户登录成功");
        String ip = RequestAddressUtils.getIp(request);
        sysLogEntity.setIp(ip);
        sysLogEntity.setAttribution(RequestAddressUtils.getAttribution(ip));
        sysLogService.insert(sysLogEntity);

        return Result.success().setData(subject.getSession().getId());
    }

    /**
     * 退出登录
     */
    @GetMapping("logout")
    @ApiOperation(value = "退出登录")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }


    /**
     * 当前用户信息
     */
    @GetMapping("info")
    @ApiOperation(value = "个人信息", response = UserVO.class)
    public Result info() {
        SysUserEntity user = ShiroUtils.getUser(SysUserEntity.class);
        return Result.success(BeanUtils.copy(user, UserVO.class));
    }

    /**
     * 分页查询列表
     */
    @RequiresPermissions(value = {"User_Get_list_page", "Department_Get_list_page"}, logical = Logical.OR)
    @GetMapping(value = "list/page")
    @ApiOperation(value = "分页查询列表", response = UserVO.class)
    public Result selectByPage(
            @ApiParam(name = "page", value = "页码", required = false)
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "每页数量", required = false)
            @RequestParam(defaultValue = "0", required = false) Integer pageSize) {
        return Result.success().setData(sysUserService.listPage(page, pageSize));
    }

    /**
     * ID查询
     */
    @RequiresPermissions("User_Get_id")
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询单个用户", notes = "id为 url一部分 如 sys/user/{id} sys/user/123")
    public Result selectById(
            @ApiParam(name = "id", value = "主键ID", required = true)
            @PathVariable("id") @Length(min = 1, max = 32, message = "id无效,长度1-32") String id) {

        SysUserEntity select = sysUserService.select(id);
        if (select == null) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success().setData(BeanUtils.copy(select, UserVO.class));
    }

    /**
     * 保存
     */
    @RequiresPermissions("User_Post")
    @SysLog
    @PostMapping()
    @ApiOperation(value = "新增", notes = "新增用户的FormData对象，注意文件的命名 picture")
    public Result insert(@ApiParam(name = "user", value = "用户的FormData对象")
                         @Validated(Insert.class) UserDTO user, @ApiParam(name = "picture", value = "用户头像文件")
                         @RequestParam(required = false, name = "picture") MultipartFile picture) throws IOException {
        SysUserEntity userEntity = sysUserService.selectByAccount(user.getAccount());
        // 账号存在
        if (userEntity != null) {
            return HCMSConstants.ResultTemplate.ACCOUNT_EXISTS;
        }
        SysUserEntity copy = BeanUtils.copy(user, SysUserEntity.class);
        sysUserService.insert(copy, picture);
        return Result.success();
    }

    /**
     * 修改
     */
    @RequiresPermissions("User_Put")
    @SysLog
    @PutMapping()
    @ApiOperation(value = "修改")
    public Result update(@ApiParam(name = "user", value = "用户的FormData对象")
                         @Validated(Update.class) UserDTO user,
                         @ApiParam(name = "picture", value = "用户头像文件")
                         @RequestParam(name = "picture", required = false) MultipartFile picture) throws IOException {
        int update = sysUserService.update(BeanUtils.copy(user, SysUserEntity.class), picture);

        // 失败
        if (0 == update) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }


    /**
     * 删除
     */
    @RequiresPermissions("User_Delete")
    @SysLog
    @DeleteMapping("{id}")
    @ApiOperation(value = "删除", notes = "删除")
    public Result delete(@ApiParam(name = "id", value = "用户id", required = true)
                         @PathVariable @Length(message = "id无效，长度1-32") String id) {

        int delete = sysUserService.delete(id);
        // 失败
        if (0 == delete) {
            return HCMSConstants.ResultTemplate.EXECUT_ERROR;
        }
        return Result.success();
    }


}
