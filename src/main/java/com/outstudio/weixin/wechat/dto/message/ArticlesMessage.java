package com.outstudio.weixin.wechat.dto.message;


import com.outstudio.weixin.wechat.dto.message.media.Item;

import java.util.List;

/**
 * Created by 96428 on 2017/7/16.
 */
public class ArticlesMessage extends BaseMessage {
    private String ArticleCount;
    private List<Item> Articles;

    public String getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(String articleCount) {
        ArticleCount = articleCount;
    }

    public List<Item> getArticles() {
        return Articles;
    }

    public void setArticles(List<Item> articles) {
        Articles = articles;
    }
}
