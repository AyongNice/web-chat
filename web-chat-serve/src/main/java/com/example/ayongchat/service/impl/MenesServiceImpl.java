package com.example.ayongchat.service.impl;


import com.example.ayongchat.enitiy.Menes;
import com.example.ayongchat.mapper.MenesMapper;
import com.example.ayongchat.service.MenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenesServiceImpl implements MenesService {


    @Autowired
    private MenesMapper menesMapper;

    public List<Menes> getMenesList() {
        return menesMapper.getMenesList();
    }

}
