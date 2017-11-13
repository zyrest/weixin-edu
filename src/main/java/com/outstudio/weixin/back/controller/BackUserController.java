package com.outstudio.weixin.back.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

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

    @PutMapping("")
    @ResponseBody
    public MessageVo setAgent(@RequestBody Map<String, String> maps) {
        Integer id = Integer.valueOf(maps.get("id"));
        Double balance = Double.valueOf(maps.get("balance"));
        Integer level = Integer.valueOf(maps.get("level"));

        userService.setAgent(id, level);
        userService.setBalance(id, balance);

        return MessageVoUtil.success();
    }

//    @PutMapping("/users/{id}/balance")
//    @ResponseBody
//    public MessageVo setBalance(@PathVariable("id") Integer id,
//                                @RequestParam("balance") double balance) {
//        return MessageVoUtil.created("",);
//    }

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


}
