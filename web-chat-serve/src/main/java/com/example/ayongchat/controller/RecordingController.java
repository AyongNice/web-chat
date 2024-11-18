package com.example.ayongchat.controller;


import com.example.ayongchat.enitiy.GroupDto;
import com.example.ayongchat.enitiy.Message;
import com.example.ayongchat.enitiy.RecordDto;
import com.example.ayongchat.service.ChatService;
import com.example.loginsever.constant.Static;
import com.example.loginsever.utlis.Result;
import com.github.pagehelper.PageInfo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Ayong
 * 聊天记录
 */

@RestController
@RequestMapping("/recording")
public class RecordingController {

    @Autowired
    private ChatService chatService;

    /**
     * 本地静态路径
     */

    @Autowired
    private Static static1;

    /**
     * 获取当前好友的聊天记录
     */
    @PostMapping("/findrecording")
    public Result<PageInfo<Message>> getFriendsList(@RequestBody RecordDto recordDto, @RequestHeader("Authorization") String authorization) {


        return Result.success(chatService.getMessageList(recordDto, authorization));
    }

    /**
     * 获取群聊天记录
     */
    @PostMapping("/groupRecords")
    public Result<PageInfo<Message>> getGroupRecords(@RequestBody RecordDto recordDto) {
        return Result.success(chatService.getGroupRecords(recordDto));
    }

    // 保存文件方法
    private void saveFile(InputStream inputStream, String filePath) throws IOException {
        OutputStream outputStream = new FileOutputStream(filePath);
        StreamUtils.copy(inputStream, outputStream);
        outputStream.close();
        inputStream.close();
    }

    /**
     * 上传图片
     */

    @PostMapping("/upload")
    public Result<String> upLoad(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        //判断图片
        if (Objects.requireNonNull(file.getContentType()).contains("image")) {
            file.transferTo(new File(static1.getPath() + fileName));
            return Result.success(fileName);

        }

        if (file.getContentType().contains("audio")) {
            // 获取文件名并保存到指定位置，这里示例保存到本地
            saveFile(file.getInputStream(), static1.getPath() + fileName);
            //音频
            return Result.success(fileName);

        }
        return Result.error("文件类型错误");
    }
}
