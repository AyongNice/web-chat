package com.example.ayongchat.mapper;


import com.example.ayongchat.enitiy.CircleDto;
import com.example.ayongchat.enitiy.CommentCircle;
import com.example.ayongchat.enitiy.FindLikeDto;
import com.example.ayongchat.enitiy.LikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CircleMapper {


    /**
     * 发布朋友圈动态
     *
     * @param circleDto     待发布动态
     * @param authorization 用户token
     */
    void publishCircle(CircleDto circleDto);

    /**
     * 获取朋友圈
     */
    List<CircleDto> findByMeCircle(String id);

    /**
     * 朋友圈点赞
     */
    void like(LikeDto likeDto);

    /**
     * 查询点赞信息
     */
    List<FindLikeDto> findLike(FindLikeDto findLikeDto);

    /**
     * 删除点赞信息
     */
    void deleteLike(String id);

    /**
     * 朋友圈评论
     */
    void comment(CommentCircle commentCircle);


    /**
     * 查询评论信息
     * @param list 评论id集合
     * @return
     */
    List<CommentCircle> findComment(String[] list);
}
