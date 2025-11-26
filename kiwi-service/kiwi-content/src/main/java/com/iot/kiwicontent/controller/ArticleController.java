package com.iot.kiwicontent.controller;

import com.iot.common.constant.HttpHeader;
import com.iot.common.result.Result;
import com.iot.kiwicontent.model.dto.PublishArticleDTO;
import com.iot.kiwicontent.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章控制器
 * @author wan
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 发表文章
     * @param publishArticleDTO 发表文章参数
     * @return 响应结果
     */
    @RequestMapping
    public Result<Object> publishArticle(@RequestHeader(HttpHeader.USER_ID) String userId,
                                         @RequestBody @Validated PublishArticleDTO publishArticleDTO) {
        articleService.publishArticle(userId, publishArticleDTO);
        return Result.success();
    }
}
