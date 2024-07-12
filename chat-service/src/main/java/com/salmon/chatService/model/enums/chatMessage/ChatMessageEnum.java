package com.salmon.chatService.model.enums.chatMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 聊天消息枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Getter
@AllArgsConstructor
public enum ChatMessageEnum {
    USER("", 0);
    private final String desc;
    private final int value;

    /**
     * 获取值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据值获取枚举
     *
     * @param value 键
     */
    public static ChatMessageEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (ChatMessageEnum chatMessageEnum : ChatMessageEnum.values()) {
                if (chatMessageEnum.value == value) {
                    return chatMessageEnum;
                }
            }
        }
        return null;
    }
}
