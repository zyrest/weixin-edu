package com.outstudio.weixin.wechat.utils;

import com.outstudio.weixin.common.utils.EncodeUtil;
import com.outstudio.weixin.common.utils.StringUtil;
import com.outstudio.weixin.wechat.config.MessageType;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.SignaturePo;
import com.outstudio.weixin.wechat.dto.message.*;
import com.outstudio.weixin.wechat.dto.message.media.*;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by 96428 on 2017/7/13.
 */
public class MessageUtil {


    public static boolean checkSignature(SignaturePo po) {
        String[] values = {WeixinProperties.TOKEN, po.getTimestamp(), po.getNonce()};
        Arrays.sort(values);
        String str = StringUtil.array2String(values);
        String encodedStr = EncodeUtil.encode(str);

        return encodedStr.equals(po.getSignature());
    }

    public static Map<String, String> xml2Map(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        Document document = reader.read(request.getInputStream());

        Element root = document.getRootElement();
        List<Element> nodes = root.elements();

        for (Element e : nodes) {
            map.put(e.getName(), e.getText());
        }
        request.getInputStream().close();

        return map;
    }

    public static String createTextMessageXml(String fromUser, String toUser, String content) {

        TextMessage message = new TextMessage();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setMsgType(MessageType.MESSAGE_TEXT);

        message.setFromUserName(fromUser);
        message.setToUserName(toUser);
        message.setContent(content);

        return object2Xml(message);
    }

    public static String createImageMessageXml(String fromUser, String toUser, Image image) {

        ImageMessage message = new ImageMessage();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setMsgType(MessageType.MESSAGE_IMAGE);

        message.setFromUserName(fromUser);
        message.setToUserName(toUser);
        message.setImage(image);

        return object2Xml(message);
    }

    public static String createVoiceMessageXml(String fromUser, String toUser, Voice voice) {

        VoiceMessage message = new VoiceMessage();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setMsgType(MessageType.MESSAGE_VOICE);

        message.setFromUserName(fromUser);
        message.setToUserName(toUser);
        message.setVoice(voice);

        return object2Xml(message);
    }

    public static String createMusicMessageXml(String fromUser, String toUser, Music music) {

        MusicMessage message = new MusicMessage();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setMsgType(MessageType.MESSAGE_MUSIC);

        message.setFromUserName(fromUser);
        message.setToUserName(toUser);
        message.setMusic(music);

        return object2Xml(message);
    }

    public static String createVideoMessageXml(String fromUser, String toUser, Video video) {

        VideoMessage message = new VideoMessage();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setMsgType(MessageType.MESSAGE_VIDEO);

        message.setFromUserName(fromUser);
        message.setToUserName(toUser);
        message.setVideo(video);

        return object2Xml(message);
    }

    public static String createArticlesMessageXml(String fromUser, String toUser, List<Item> items) {

        ArticlesMessage message = new ArticlesMessage();
        message.setCreateTime(String.valueOf(new Date().getTime()));
        message.setMsgType(MessageType.MESSAGE_NEWS);

        message.setFromUserName(fromUser);
        message.setToUserName(toUser);
        message.setArticleCount(String.valueOf(items.size()));
        message.setArticles(items);

        return object2Xml(message);
    }

    private static String object2Xml(Object o) {
        XStream xStream = new XStream();

        xStream.alias("xml", o.getClass());
        xStream.alias("Image", Image.class);
        xStream.alias("Voice", Voice.class);
        xStream.alias("Video", Video.class);
        xStream.alias("Music", Music.class);
        xStream.alias("item", Item.class);

        return xStream.toXML(o);
    }
}
