package com.iot.kiwicontent.controller;

import com.iot.common.constant.HttpHeader;
import com.iot.common.result.PageResult;
import com.iot.common.result.Result;
import com.iot.kiwicontent.model.dto.PublishArticleDTO;
import com.iot.kiwicontent.model.pojo.Article;
import com.iot.kiwicontent.model.vo.ArticleListVO;
import com.iot.kiwicontent.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public Result<Object> publishArticle(@RequestHeader(HttpHeader.USER_ID) String userId,
                                         @RequestBody @Validated PublishArticleDTO publishArticleDTO) {
        articleService.publishArticle(userId, publishArticleDTO);
        return Result.success();
    }

    /**
     * 删除文章
     * @param articleId 文章 ID
     * @return 响应结果
     */
    @DeleteMapping
    public Result<Object> deleteArticle(@RequestHeader(HttpHeader.USER_ID) String userId,
                                        @RequestParam("articleId") String articleId){
        return articleService.deleteArticle(userId, articleId);
    }

    /**
     * 作者获取文章列表
     * @return 文章列表
     */
    @GetMapping("/s")
    public Result<PageResult<ArticleListVO>> getArticleList(@RequestHeader(HttpHeader.USER_ID) String userId,
                                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(articleService.getArticleList(userId, pageNum, pageSize));
    }

    /**
     * 获取文章详情
     * @return 文章详情
     */
    @GetMapping
    public Result<Article> getArticleDetail(@RequestHeader(HttpHeader.USER_ID) String userId,
                                            @RequestParam("articleId") String articleId) {
        return Result.success(articleService.getArticleDetail(userId, articleId));
    }
}
