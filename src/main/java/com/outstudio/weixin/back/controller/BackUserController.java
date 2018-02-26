package com.outstudio.weixin.back.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/back/users")
public class BackUserController {

    private static final int pageSize = 10;

    @Resource
    private UserService userService;

    @GetMapping("/page/{pageNum}")
    @ResponseBody
    public MessageVo getUsers(@PathVariable int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserEntity> userEntities = userService.getAllUsers();
        return MessageVoUtil.success(userEntities);
    }

    @PutMapping
    @ResponseBody
    public MessageVo setAgent(@RequestBody UserEntity userEntity) {

        LoggerUtil.fmtDebug(getClass(), JSON.toJSONString(userEntity));
        userService.editUser(userEntity);

        return MessageVoUtil.success();
    }

    @GetMapping("/pageNum")
    @ResponseBody
    public MessageVo getPages() {
        long counts = userService.getCounts();
        long ans = counts / pageSize;
        if (counts % pageSize != 0) {
            ans++;
        }
        return MessageVoUtil.success(ans);
    }

    @GetMapping("/info/{id}")
    public ModelAndView onePage(@PathVariable("id") Integer id) {
        ModelAndView view = new ModelAndView();
        UserEntity user = userService.getUserById(id);
        view.addObject("user", user);
        view.setViewName("hide/back/editUser");
        return view;
    }

    @GetMapping("/userInfo/{id}")
    @ResponseBody
    public MessageVo getUserInfo(@PathVariable Integer id) {
        return MessageVoUtil.success(userService.getUserById(id));
    }

}
