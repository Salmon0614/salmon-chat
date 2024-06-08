package com.salmon.chatService.model.enums.group;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 群组枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Getter
@AllArgsConstructor
public enum GroupEnum {
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
    public static GroupEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (GroupEnum groupEnum : GroupEnum.values()) {
                if (groupEnum.value == value) {
                    return groupEnum;
                }
            }
        }
        return null;
    }
}
