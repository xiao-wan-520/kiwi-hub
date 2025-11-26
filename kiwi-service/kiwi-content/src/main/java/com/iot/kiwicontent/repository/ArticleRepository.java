package com.iot.kiwicontent.repository;

import com.iot.kiwicontent.model.pojo.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 文章仓库接口
 * @author wan
 */
public interface ArticleRepository extends MongoRepository<Article, String> {
}
