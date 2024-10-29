package com.example.ayongchat.enitiy;


import lombok.Data;

@Data
public class Page {
    //页码
    private Integer page;
    //每页显示的条数
    private Integer size;
}
