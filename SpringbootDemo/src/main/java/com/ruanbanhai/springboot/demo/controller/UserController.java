package com.ruanbanhai.springboot.demo.controller;

import com.ruanbanhai.springboot.demo.bean.User;
import com.ruanbanhai.springboot.demo.service.UserService;
import com.zengtengpeng.common.bean.DataRes;
import com.zengtengpeng.common.utils.ExcelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * user controller
 */
@Controller
public class UserController {


    @Resource
    private UserService userService;

    /**
     * 删除-user
     */
    @ResponseBody
    @RequestMapping("user/deleteByPrimaryKey")
    public DataRes deleteByPrimaryKey(User user, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(userService.deleteByPrimaryKey(user));
    }


    /**
     * 保存 (主键为空则增加 否则 修改)-> user
     */
    @ResponseBody
    @RequestMapping("user/save")
    public DataRes save(User user, HttpServletRequest request, HttpServletResponse response) {
        if (user.getId() == null) {
            return DataRes.success(userService.insert(user));
        }
        return DataRes.success(userService.update(user));

    }


    /**
     * 根据主键查询->user
     */
    @ResponseBody
    @RequestMapping("user/selectByPrimaryKey")
    public DataRes selectByPrimaryKey(User user, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(userService.selectByPrimaryKey(user));
    }


    /**
     * 根据条件查询->user
     */
    @ResponseBody
    @RequestMapping("user/selectByCondition")
    public DataRes selectByCondition(User user, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(userService.selectByCondition(user));
    }


    /**
     * 分页查询 (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->user
     */
    @ResponseBody
    @RequestMapping("user/selectAllByPaging")
    public DataRes selectAllByPaging(User user, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(userService.selectAllByPaging(user));
    }


    /**
     * 导出报表->user
     */
    @RequestMapping("user/export")
    public void export(User user, HttpServletRequest request, HttpServletResponse response) {
        List<User> list = userService.selectAll(user);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("username", "username");
        header.put("password", "password");
        header.put("id", "id");
        ExcelUtils.exportExcel("user", header, list, response, request);

    }


}
