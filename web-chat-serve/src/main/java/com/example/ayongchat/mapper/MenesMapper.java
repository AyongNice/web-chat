package com.example.ayongchat.mapper;


import com.example.ayongchat.enitiy.Menes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenesMapper {

      List<Menes> getMenesList();
}
