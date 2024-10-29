package com.example.ayongchat.aop;

import com.example.ayongchat.enitiy.GroupDto;
import com.example.ayongchat.enitiy.RemoveGroupUserDto;
import com.example.ayongchat.exception.UnauthorizedException;
import com.example.ayongchat.mapper.FriendsMapper;
import com.example.loginsever.utlis.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

@Aspect
@Component
public class GroupUserAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FriendsMapper friendsMapper;

    @Before("@annotation(com.example.ayongchat.aop.CheckGroupPermission)")
    public void checkPermission(JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        RemoveGroupUserDto removeGroupUserDto = (RemoveGroupUserDto) args[0];
        String authorization = (String) args[1];

        GroupDto groupInfo = friendsMapper.getGroupInfo(removeGroupUserDto.getGroupId());
        if (!groupInfo.getLeader().equals(jwtUtil.getID(authorization))) {
            throw new UnauthorizedException("无权操作该群组");
        }

        // 调用目标方法
        method.invoke(joinPoint.getTarget(), args[0], args[1], groupInfo, new ArrayList<>(Arrays.asList(groupInfo.getList().split(","))));
    }
}
