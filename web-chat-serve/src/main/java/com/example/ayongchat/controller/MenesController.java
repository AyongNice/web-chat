package com.example.ayongchat.controller;


import com.example.ayongchat.enitiy.Friends;
import com.example.ayongchat.enitiy.Menes;
import com.example.ayongchat.service.MenesService;
import com.example.loginsever.utlis.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 表情包管理
 */

@RestController
@RequestMapping("/menes")
public class MenesController {

    @Autowired
    private MenesService menesService;

    /**
     * 获取表情包库列表
     */
    @RequestMapping("/getMenesList")
    public Result<List<Menes>> getMenesList(){
        return Result.success(menesService.getMenesList());
    }


}
