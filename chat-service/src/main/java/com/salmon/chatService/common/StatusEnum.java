package com.salmon.chatService.common;

import com.salmon.chatService.model.enums.user.UserGenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 通用状态枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    DISABLED("禁用", 0),
    ENABLE("启用", 1);

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
    public static StatusEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (statusEnum.value == value) {
                    return statusEnum;
                }
            }
        }
        return null;
    }
}
