package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.service.SysRoleService;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-12-13
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("用户列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       SysUserQueryVo sysUserQueryVo){
        //创建一个page对象
        Page<SysUser> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam,sysUserQueryVo);

        return  Result.ok(pageModel);
    }

    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser sysUser){
        String encrypt = MD5.encrypt(sysUser.getPassword());
        sysUser.setPassword(encrypt);
        boolean isSuccess = sysUserService.save(sysUser);
        if(isSuccess){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @ApiOperation("根据id查询")
    @GetMapping("getUser/{id}")
    public Result findRoleById(@PathVariable String id){
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation("最终修改角色")
    @PostMapping("update")
    public Result update(@RequestBody SysUser user){
        boolean isSuccess = sysUserService.updateById(user);
        if(isSuccess){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @ApiOperation("逻辑删除接口")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        //调用方法删除
        boolean isSuccess = sysUserService.removeById(id);
        if(isSuccess){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @ApiOperation(value = "更改用户状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id, @PathVariable Integer status) {
        sysUserService.updateStatus(id, status);
        return Result.ok();
    }
}

