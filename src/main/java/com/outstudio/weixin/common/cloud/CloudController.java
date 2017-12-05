package com.outstudio.weixin.common.cloud;

import com.outstudio.weixin.common.cloud.util.CloudUtil;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud")
public class CloudController {

    @RequestMapping("/signature")
    public MessageVo getSignature() {
        try {
            return MessageVoUtil.created("", CloudUtil.getUploadSignature());
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageVo().setStatus(ResponseStatus.SYSTEM_ERROE).setMessage("签名创建失败");
        }
    }
}
