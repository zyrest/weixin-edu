package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.*;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.ExhibitionEntity;
import com.outstudio.weixin.common.service.ExhibitionService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lmy on 2018/2/19.
 */
@RestController
@RequestMapping("/page/exhibition")
public class ExhibitionController {

    private final int pageSize = 15;

    @Resource
    private ExhibitionService exhibitionService;

    @GetMapping("/page/{page}/{type}")
    public MessageVo getAll(@PathVariable String type,
                            @PathVariable Integer page) {
        PageHelper.startPage(page, pageSize);
        List<ExhibitionEntity> entities = exhibitionService.getAllByType(type);
        return MessageVoUtil.success(entities);
    }

    @GetMapping("/{id}")
    public MessageVo getById(@PathVariable Integer id) {
        return MessageVoUtil.success(exhibitionService.getById(id));
    }

    @GetMapping("/search")
    public MessageVo search(@RequestParam("searchParam") String searchParam) {
        List<ExhibitionEntity> entities = exhibitionService.getBySearchParam(searchParam);
        return MessageVoUtil.success(entities);
    }

    @GetMapping("/pageNum/{type}")
    public MessageVo getTotalPage(@PathVariable String type) {
        return MessageVoUtil.success(exhibitionService.getCountByType(type));
    }

    @PutMapping("/upvote/{id}")
    public MessageVo upvote(@PathVariable Integer id,
                            HttpServletRequest request) {
        boolean result = exhibitionService.upvote(id, request.getRemoteAddr());
        if (result) {
            return MessageVoUtil.success();
        } else {
            return new MessageVo().setStatus(ResponseStatus.EXCESSIVE_ATTEMPT).setMessage("该用户已经投票");
        }
    }
}