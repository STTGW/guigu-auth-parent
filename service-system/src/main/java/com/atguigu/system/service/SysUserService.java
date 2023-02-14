package com.atguigu.system.service;


import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-12-13
 */
public interface SysUserService extends IService<SysUser> {

    //用户列表方法
    IPage<SysUser> selectPage(Page<SysUser> pageParam, @Param("vo")SysUserQueryVo sysUserQueryVo);


    void updateStatus(String id, Integer status);

    SysUser getUserInfoByUserName(String username);

    Map<String, Object> getUserInfo(String username);
}
