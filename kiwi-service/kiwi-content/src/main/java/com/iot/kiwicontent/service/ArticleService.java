package com.iot.kiwicontent.service;

import com.iot.common.result.PageResult;
import com.iot.common.result.Result;
import com.iot.kiwicontent.model.dto.PublishArticleDTO;
import com.iot.kiwicontent.model.pojo.Article;
import com.iot.kiwicontent.model.vo.ArticleListVO;

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

    /**
     * 删除文章
     *
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 响应结果
     */
    Result<Object> deleteArticle(String userId, String articleId);

    /**
     * 获取文章列表
     *
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 文章列表
     */
    PageResult<ArticleListVO> getArticleList(String userId, Integer pageNum, Integer pageSize);

    /**
     * 获取文章详情
     *
     * @param userId 用户ID
     * @param articleId 文章ID
     * @return 文章详情
     */
    Article getArticleDetail(String userId, String articleId);
}
