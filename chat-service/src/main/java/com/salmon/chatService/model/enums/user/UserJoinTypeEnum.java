package com.salmon.chatService.model.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户--加入好友类型枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Getter
@AllArgsConstructor
public enum UserJoinTypeEnum {

    NO_AUTH("直接加入", 0),
    AUTH("需要验证", 1);

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
    public static UserJoinTypeEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (UserJoinTypeEnum userEnum : UserJoinTypeEnum.values()) {
                if (userEnum.value == value) {
                    return userEnum;
                }
            }
        }
        return null;
    }
}
