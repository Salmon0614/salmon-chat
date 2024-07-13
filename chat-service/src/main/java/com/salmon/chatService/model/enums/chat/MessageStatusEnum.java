package com.salmon.chatService.model.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息状态枚举
 *
 * @author Salmon
 * @since 2024-07-13
 */
@Getter
@AllArgsConstructor
public enum MessageStatusEnum {

    SENDING(0, "发送中"),
    SENT(1, "已发送");

    private final Integer value;
    private final String desc;

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
    public static MessageStatusEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (MessageStatusEnum messageStatusEnum : MessageStatusEnum.values()) {
                if (messageStatusEnum.value == value) {
                    return messageStatusEnum;
                }
            }
        }
        return null;
    }
}
