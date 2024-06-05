package com.salmon.chatService.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色枚举
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum {

    USER("用户", 0),
    ADMIN("管理员", 1);

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
    public static UserRoleEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (UserRoleEnum userRoleEnum : UserRoleEnum.values()) {
                if (userRoleEnum.value == value) {
                    return userRoleEnum;
                }
            }
        }
        return null;
    }
}
