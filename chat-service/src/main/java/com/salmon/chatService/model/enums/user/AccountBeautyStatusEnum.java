package com.salmon.chatService.model.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 靓号使用状态枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Getter
@AllArgsConstructor
public enum AccountBeautyStatusEnum {

    UNUSED("未使用", 0),
    USED("已使用", 1);

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
    public static AccountBeautyStatusEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (AccountBeautyStatusEnum statusEnum : AccountBeautyStatusEnum.values()) {
                if (statusEnum.value == value) {
                    return statusEnum;
                }
            }
        }
        return null;
    }
}
