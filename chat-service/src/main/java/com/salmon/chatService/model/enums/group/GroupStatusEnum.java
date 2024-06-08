package com.salmon.chatService.model.enums.group;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 群组状态枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Getter
@AllArgsConstructor
public enum GroupStatusEnum {

    NORMAL("正常", 0),
    DISSOLUTION("解散", 1);

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
    public static GroupStatusEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (GroupStatusEnum groupStatusEnum : GroupStatusEnum.values()) {
                if (groupStatusEnum.value == value) {
                    return groupStatusEnum;
                }
            }
        }
        return null;
    }
}
