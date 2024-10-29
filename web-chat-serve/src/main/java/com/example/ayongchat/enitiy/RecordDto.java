package com.example.ayongchat.enitiy;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Ayong
 * @date 2024/9/22 - 00:22
 * @Description: 聊天记录dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordDto extends Page  implements Serializable {
    /**
     * 好友id
     */
    private String friendId;
}
