package com.salmon.chatService.model.enums.chatSession;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 聊天会话信息枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-07-12
 */
@Getter
@AllArgsConstructor
public enum ChatSessionEnum {
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
    public static ChatSessionEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (ChatSessionEnum chatSessionEnum : ChatSessionEnum.values()) {
                if (chatSessionEnum.value == value) {
                    return chatSessionEnum;
                }
            }
        }
        return null;
    }
}
