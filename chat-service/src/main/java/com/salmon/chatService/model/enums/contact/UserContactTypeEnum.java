package com.salmon.chatService.model.enums.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户聊天类型枚举
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Getter
@AllArgsConstructor
public enum UserContactTypeEnum {

    USER(0, "SU", "好友"),
    GROUP(1, "SG", "群");

    private final int type;
    private final String prefix;
    private final String desc;

    /**
     * 获取类型列表
     */
    public static List<Integer> getTypes() {
        return Arrays.stream(values()).map(item -> item.type).collect(Collectors.toList());
    }

    /**
     * 根据值获取枚举
     *
     * @param type 类型
     */
    public static UserContactTypeEnum getEnumByValue(int type) {
        if (getTypes().contains(type)) {
            for (UserContactTypeEnum userContactTypeEnum : UserContactTypeEnum.values()) {
                if (userContactTypeEnum.type == type) {
                    return userContactTypeEnum;
                }
            }
        }
        return null;
    }
}
