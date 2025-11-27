package com.iot.kiwicontent.consume.listener;

import com.iot.kiwicontent.model.constant.ParameterConstant;
import com.iot.kiwicontent.model.constant.RabbitConstant;
import com.iot.kiwicontent.model.constant.RedisConstant;
import com.iot.kiwicontent.model.pojo.Article;
import com.iot.kiwicontent.model.pojo.ArticleLike;
import com.iot.kiwicontent.repository.ArticleLikeRepository;
import com.mongodb.MongoWriteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 点赞消息监听器
 * @author wan
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ArticleLikeListener {

    private final MongoTemplate mongoTemplate;
    private final ArticleLikeRepository articleLikeRepository;
    private final StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = RabbitConstant.ARTICLE_LIKE_QUEUE)
    public void handleLikeEvent(Map<String, String> msg) {
        String userId = msg.get(ParameterConstant.CURRENT_USER_ID);
        String articleId = msg.get(ParameterConstant.ARTICLE_ID);
        String authorId = msg.get(ParameterConstant.AUTHOR_ID);

        // 回查 Redis，以 Redis 的当前状态为准
        String userLikeKey = RedisConstant.USER_LIKE_KEY + articleId;
        Boolean isLikedInRedis = stringRedisTemplate.opsForSet().isMember(userLikeKey, userId);

        // 根据 Redis 的真理，强行对齐 MongoDB 的状态
        if (Boolean.TRUE.equals(isLikedInRedis)) {
            // Redis 显示“已点赞”
            // 动作：确保 DB 里有记录，且计数正确
            handleLikePersistence(userId, articleId, authorId);
        } else {
            // Redis 显示“未点赞”
            // 动作：确保 DB 里没有记录，且计数正确
            handleUnlikePersistence(userId, articleId);
        }
    }

    /**
     * 处理点赞落库 (幂等操作)
     * 只有当真正插入了数据时，才增加计数
     */
    private void handleLikePersistence(String userId, String articleId, String authorId) {
        try {
            // 尝试插入关系记录
            ArticleLike like = new ArticleLike(userId, articleId, authorId);
            articleLikeRepository.save(like);

            // 只有上面 save 成功（没有抛出唯一索引异常），说明是新的点赞
            // 此时才去增加文章计数，防止重复计算
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("_id").is(articleId)),
                    new Update().inc("stats.like_count", 1),
                    Article.class
            );
            log.info("点赞数据同步完成: u={} -> a={}", userId, articleId);

        } catch (Exception e) {
            // 捕获唯一索引冲突的异常
            if (e.getCause() instanceof MongoWriteException mongoWriteException) {
                if (mongoWriteException.getError().getCode() == 11000) {
                    log.warn("重复点赞消息，已忽略: user={}, article={}", userId, articleId);
                }
            }
            throw e;
        }
    }

    /**
     * 处理取消点赞 (幂等操作)
     * 只有当真正删除了数据时，才减少计数
     */
    private void handleUnlikePersistence(String userId, String articleId) {
        // 尝试删除关系记录
        long deletedCount = articleLikeRepository.deleteByUserIdAndArticleId(userId, articleId);

        // 只有真正删除了数据 (deletedCount > 0)，说明之前确实有点赞
        // 此时才去扣减计数
        if (deletedCount > 0) {
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("_id").is(articleId)
                            .and("stats.like_count").gt(0)),
                    new Update().inc("stats.like_count", -1),
                    Article.class
            );
            log.info("取消点赞同步完成: u={} -> a={}", userId, articleId);
        } else {
            log.warn("点赞记录不存在，忽略取消: u={} -> a={}", userId, articleId);
        }
    }
}
