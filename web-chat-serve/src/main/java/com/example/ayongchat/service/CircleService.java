package com.example.ayongchat.service;


import com.example.ayongchat.enitiy.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CircleService {


    /**
     * 发布朋友圈动态
     *
     * @param circleDto     待发布动态
     * @param authorization 用户token
     */
    void publishCircle(CircleDto circleDto, String authorization);

    /**
     * 查询动态
     *
     * @param authorization 用户token
     */

    PageInfo<CircleDto> findByMeCircle(String authorization, Page page);

    /**
     * 朋友点赞
     */

    Integer like(LikeDto likeDto);


    /**
     * 查询点赞信息
     */
    List<FindLikeDto> findLike(FindLikeDto findLikeDto);

    /**
     * 朋友圈评论
     */
    void comment(CommentCircle commentCircle);

    /**
     * 查询评论信息
     *
     * @param ids
     * @return
     */
    List<CommentCircle> findComment(String[] list);


    /**
     * 删除评论
     * @param circleId 评论id
     * @param authorization token
     */
    void deleteComment(String commentId, String authorization);
}
