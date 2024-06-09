package com.salmon.chatService.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 好友/群聊加入类型枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Getter
@AllArgsConstructor
public enum JoinTypeEnum {

    JOIN("直接加入", 0),
    AUTH("需审核加入", 1);

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
    public static JoinTypeEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (JoinTypeEnum joinTypeEnum : JoinTypeEnum.values()) {
                if (joinTypeEnum.value == value) {
                    return joinTypeEnum;
                }
            }
        }
        return null;
    }
}
