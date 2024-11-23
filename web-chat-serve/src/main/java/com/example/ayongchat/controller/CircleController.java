package com.example.ayongchat.controller;


import com.example.ayongchat.enitiy.*;
import com.example.ayongchat.service.CircleService;
import com.example.loginsever.utlis.Result;
import com.github.pagehelper.PageInfo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 朋友圈
 *
 * @author ayong
 * @date 2023/10/21
 */
@RestController
@RequestMapping("/circle")
public class CircleController {

    @Autowired
    private CircleService circleService;


    /**
     * 发布朋友圈
     *
     * @param circleDto
     * @param authorization
     * @return
     */
    @PostMapping("/addCircle")
    public Result<String> circle(@RequestBody CircleDto circleDto, @RequestHeader("Authorization") String authorization) {
        try {
            circleService.publishCircle(circleDto, authorization);
            return Result.success();
        } catch (Exception e) {
            return Result.error("发布失败");
        }
    }

    /**
     * 获取朋友圈
     *
     * @return
     */

    @GetMapping("/findByMeCircle")
    public Result<PageInfo<CircleDto>> getCircle(@RequestHeader("Authorization") String authorization, @ModelAttribute Page page) {
        return Result.success(circleService.findByMeCircle(authorization, page));
    }

    /**
     * 朋友圈点赞
     */
    @PostMapping("/like")
    public Result<Integer> like(@RequestBody LikeDto likeDto) {
        return Result.success(circleService.like(likeDto));
    }

    /**
     * 查询点赞信息
     */
    @GetMapping("/findLike")
    public Result<List<FindLikeDto>> findLike(@Param("ids") String ids) {
        FindLikeDto findLikeDto = new FindLikeDto();
        findLikeDto.setIds(ids.split(","));
        return Result.success(circleService.findLike(findLikeDto));
    }

    /**
     * 朋友圈评论
     */
    @PostMapping("/comment")
    public Result<String> comment(@RequestBody CommentCircle commentCircle) {
        circleService.comment(commentCircle);
        return Result.success();
    }

    /**
     * 查询朋友圈评论信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/findComment")
    public Result<List<CommentCircle>> findComment(@Param("ids") String ids) {
        return Result.success(circleService.findComment(ids.split(",")));
    }

    /**
     * 删除朋友圈 评论
     */
    @DeleteMapping("/deleteComment/{commentId}")
    public Result<String> deleteComment(@PathVariable String commentId, @RequestHeader("Authorization") String authorization) {
        circleService.deleteComment(commentId, authorization);
        return Result.success();
    }
}
