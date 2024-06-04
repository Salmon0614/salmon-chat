package com.salmon.chatService.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用ID请求
 *
 * @author Salmon
 * @since 2024-05-19
 */
@Data
public class IdRequest implements Serializable {

    /**
     * 序列化版本
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
}
