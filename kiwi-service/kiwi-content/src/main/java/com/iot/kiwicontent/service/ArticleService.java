package com.iot.kiwicontent.service;

import com.iot.kiwicontent.model.dto.PublishArticleDTO;

/**
 * 文章服务接口
 * @author wan
 */
public interface ArticleService {
    /**
     * 发表文章
     *
     * @param userId 用户ID
     * @param publishArticleDTO 发表文章参数
     */
    void publishArticle(String userId, PublishArticleDTO publishArticleDTO);
}
