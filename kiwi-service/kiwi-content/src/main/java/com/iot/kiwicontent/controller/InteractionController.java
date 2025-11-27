package com.iot.kiwicontent.controller;

import com.iot.common.constant.HttpHeader;
import com.iot.common.result.Result;
import com.iot.kiwicontent.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 互动控制器
 * @author wan
 */
@RestController
@RequestMapping("/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    /**
     * 点赞/取消点赞
     * @param userId 用户ID
     * @param articleId 文章ID
     * @param authorId 作者ID
     * @return 结果
     */
    @PostMapping("/like")
    public Result<Object> toggleLike(@RequestHeader(HttpHeader.USER_ID) String userId,
                               @RequestParam("articleId") String articleId,
                               @RequestParam("authorId") String authorId) {
        boolean isLiked = interactionService.toggleLike(userId, articleId, authorId);
        // 返回给前端，前端根据 isLiked 变红或变灰，同时更新数字
        Map<String, Object> data = new HashMap<>();
        data.put("isLiked", isLiked);
        return Result.success(data);
    }
}
