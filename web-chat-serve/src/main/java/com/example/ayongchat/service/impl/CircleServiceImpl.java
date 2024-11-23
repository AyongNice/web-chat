package com.example.ayongchat.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.ayongchat.enitiy.*;
import com.example.ayongchat.mapper.CircleMapper;
import com.example.ayongchat.service.CircleService;
import com.example.loginsever.utlis.JwtUtil;
import com.example.loginsever.utlis.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDate;
import java.util.List;


@Service
public class CircleServiceImpl implements CircleService {

    @Autowired
    private CircleMapper circleMapper;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 发布朋友圈动态
     *
     * @param circleDto     待发布动态
     * @param authorization 用户token
     */
    @Override
    public void publishCircle(CircleDto circleDto, String authorization) {
        circleDto.setId(IdUtil.getSnowflakeNextIdStr());
        circleDto.setUserId(jwtUtil.getID(authorization));
        circleDto.setCreatedAt(LocalDate.now());
        circleMapper.publishCircle(circleDto);
    }

    /**
     * 获取朋友圈
     */
    @Override
    public PageInfo<CircleDto> findByMeCircle(String authorization, Page page) {

        PageHelper.startPage(page.getPage(), page.getSize());

        return new PageInfo<>(circleMapper.findByMeCircle(jwtUtil.getID(authorization)));
    }

    /**
     * 朋友圈点赞
     */
    public Integer like(LikeDto likeDto) {
        FindLikeDto findLikeDto = new FindLikeDto();
        //声明数组
        findLikeDto.setIds(new String[]{likeDto.getCircleId()});

        List<FindLikeDto> list = circleMapper.findLike(findLikeDto);
        if (list.size() > 0) {
            circleMapper.deleteLike(list.get(0).getId());
            return 0;
        }

        likeDto.setId(IdUtil.getSnowflakeNextIdStr());
        likeDto.setCreatedAt(LocalDate.now());

        circleMapper.like(likeDto);
        return 1;
    }


    /**
     * 查询点赞信息
     */
    public List<FindLikeDto> findLike(FindLikeDto findLikeDto) {
        return circleMapper.findLike(findLikeDto);
    }

    /**
     * 朋友圈评论
     */
    public void comment(CommentCircle commentCircle) {
        commentCircle.setId(IdUtil.getSnowflakeNextIdStr());
        commentCircle.setCreatedAt(LocalDate.now());
        circleMapper.comment(commentCircle);

    }


    /**
     * 查询评论信息
     *
     * @param list 评论id集合
     * @return
     */
    public List<CommentCircle> findComment(String[] list) {
        return circleMapper.findComment(list);
    }

    /**
     * 删除评论
     *
     * @param commentId      评论id
     * @param authorization token
     */
    @Override
    public void deleteComment(String commentId, String authorization) {
        String id = jwtUtil.getID(authorization);
        CommentCircle commentCircle = circleMapper.findById(commentId);
        if (commentCircle.getUserId().equals(id)) {
            circleMapper.deleteComment(commentId);
        } else {
            throw new RuntimeException("你没有权限删除");
        }


    }
}
