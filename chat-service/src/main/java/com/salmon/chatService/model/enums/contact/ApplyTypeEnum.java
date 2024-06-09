package com.salmon.chatService.model.enums.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 申请方式
 *
 * @author Salmon
 * @since 2024-06-10
 */
@Getter
@AllArgsConstructor
public enum ApplyTypeEnum {

    SEARCH("搜索方式", 0),
    GROUP("群聊方式", 1);

    private final String desc;
    private final int value;

    /**
     * 获取类型列表
     */
    public static List<Integer> getTypes() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据值获取枚举
     *
     * @param value 类型
     */
    public static ApplyTypeEnum getEnumByValue(int value) {
        if (getTypes().contains(value)) {
            for (ApplyTypeEnum applyTypeEnum : ApplyTypeEnum.values()) {
                if (applyTypeEnum.value == value) {
                    return applyTypeEnum;
                }
            }
        }
        return null;
    }
}
