package com.iot.kiwicontent.consume.listener;

import com.iot.kiwicontent.model.constant.ParameterConstant;
import com.iot.kiwicontent.model.constant.RabbitConstant;
import com.iot.kiwicontent.model.pojo.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wan
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleViewListener {
    private static final String VIEW_COUNT = "stats.view_count";
    private static final String LIKE_COUNT = "stats.like_count";
    private static final String COMMENT_COUNT = "stats.comment_count";

    private static final String AUTHOR_ID_KEY = "author_id";

    private final MongoTemplate mongoTemplate;

    /**
     * 监听文章浏览事件
     */
    @RabbitListener(queues = RabbitConstant.ARTICLE_VIEW_QUEUE)
    public void handleArticleViewEvent(Map<String, String> message) {
        // 解析消息，调用通用方法更新浏览数
        updateArticleCount(
                message.get(ParameterConstant.ARTICLE_ID),
                message.get(ParameterConstant.CURRENT_USER_ID),
                VIEW_COUNT,
                ParameterConstant.INCREASE.equals(message.get(ParameterConstant.ACTION)) ? 1 : -1
        );
    }

    /**
     * 监听文章点赞事件
     */
    @RabbitListener(queues = RabbitConstant.ARTICLE_LIKE_QUEUE)
    public void handleArticleLikeEvent(Map<String, String> message) {
        // 解析消息，调用通用方法更新点赞数
        updateArticleCount(
                message.get(ParameterConstant.ARTICLE_ID),
                message.get(ParameterConstant.CURRENT_USER_ID),
                LIKE_COUNT,
                ParameterConstant.INCREASE.equals(message.get(ParameterConstant.ACTION)) ? 1 : -1
        );
    }

    /**
     * 监听文章评论事件
     */
    @RabbitListener(queues = RabbitConstant.ARTICLE_COMMENT_QUEUE)
    public void handleArticleCommentEvent(Map<String, String> message) {
        // 解析消息，调用通用方法更新评论数
        updateArticleCount(
                message.get(ParameterConstant.ARTICLE_ID),
                message.get(ParameterConstant.CURRENT_USER_ID),
                COMMENT_COUNT,
                ParameterConstant.INCREASE.equals(message.get(ParameterConstant.ACTION)) ? 1 : -1
        );
    }

    /**
     * 通用文章计数更新方法（核心提取方法）
     * @param articleId 文章ID
     * @param userId 当前用户ID
     * @param countField 要更新的计数字段（如 viewCount、likeCount）
     * @param incCount 增减数量（1=增加，-1=减少）
     */
    private void updateArticleCount(String articleId, String userId, String countField, int incCount) {
        // 校验必填参数（避免空指针导致更新失败）
        if (articleId == null || userId == null || countField == null) {
            log.error("更新文章计数失败：必填参数为空！articleId={}, authorId={}, countField={}",
                    articleId, userId, countField);
            return;
        }

        // 构建查询条件：文章ID + 作者ID（不能是当前用户）, 增加时大于等于0,减少时大于0
        Query query = Query.query(
                Criteria.where("_id").is(articleId)
                        .and(AUTHOR_ID_KEY).ne(userId)
        );
        if (incCount < 0) {
            query.addCriteria(Criteria.where(countField).gt(0));
        }

        // 构建更新操作：增减计数 + 强制计数不小于0
        Update update = new Update()
                .inc(countField, incCount);

        mongoTemplate.updateFirst(query, update, Article.class);
        log.debug("更新文章计数成功：articleId={}, authorId={}, countField={}, incCount={}",
                articleId, userId, countField, incCount);

    }
}
