package com.iot.kiwicontent.service.impl;

import com.iot.kiwicontent.model.dto.PublishArticleDTO;
import com.iot.kiwicontent.model.pojo.Article;
import com.iot.kiwicontent.repository.ArticleRepository;
import com.iot.kiwicontent.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wan
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * 发表文章
     *
     * @param userId 用户ID
     * @param publishArticleDTO 发表文章参数
     */
    @Override
    public void publishArticle(String userId, PublishArticleDTO publishArticleDTO) {
        Article article = Article.builder()
                .authorId(userId)
                .title(publishArticleDTO.getTitle())
                .content(publishArticleDTO.getContent())
                .build();
        boolean hasText = StringUtils.hasText(publishArticleDTO.getContentType());
        String contentType = hasText ? publishArticleDTO.getContentType() : null;
        article.setContentType(contentType);
        List<String> ossUrls = publishArticleDTO.getOssUrls().isEmpty() ? null : publishArticleDTO.getOssUrls();
        article.setOssUrls(ossUrls);
        List<String> tags = publishArticleDTO.getTags().isEmpty() ? null : publishArticleDTO.getTags();
        article.setTags(tags);
        articleRepository.save(article);
        // TODO: RabbitMQ 通知粉丝
    }
}
