package com.atguigu.system.test;


import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //1  查询表所有记录
    @Test
    public void findAll(){

        List<SysRole> list = sysRoleMapper.selectList(null);

        for (SysRole sysRole:list) {
            System.out.println(sysRole);
        }
    }

    //2  添加操作
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色1");
        sysRole.setRoleCode("testManager");
        sysRole.setDescription("测试角色");
        int rows = sysRoleMapper.insert(sysRole);
        System.out.println(rows);
    }

    //3  修改操作
    @Test
    public void update(){
        //根据id查询
        SysRole sysRole = sysRoleMapper.selectById(1);

        //设置修改值
        sysRole.setDescription("系统管理员尚硅谷");

        //调用方法实现修改
        sysRoleMapper.updateById(sysRole);
    }

    //4  id删除
    @Test
    public void deleteId(){
        int rows = sysRoleMapper.deleteById(2);

    }

    //5 批量删除
    @Test
    public void testBatchDelete(){
        sysRoleMapper.deleteBatchIds(Arrays.asList(1,2));

    }
}
