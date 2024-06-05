package com.salmon.chatService.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 验证码类型枚举
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Getter
@AllArgsConstructor
public enum CheckCodeTypeEnum {

    IMAGE("图像验证码", 0),
    EMAIL("邮箱验证码", 1),
    MOBILE("手机验证码", 2);

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
    public static CheckCodeTypeEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (CheckCodeTypeEnum codeTypeEnum : CheckCodeTypeEnum.values()) {
                if (codeTypeEnum.value == value) {
                    return codeTypeEnum;
                }
            }
        }
        return null;
    }

}
