package com.salmon.chatService.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取验证码方式枚举
 *
 * @author Salmon
 * @since 2024-06-06
 */
@Getter
@AllArgsConstructor
public enum GetCodeMethodEnum {
    CLICK("用户点击", "click"),
    SYSTEM("系统获取", "sys");

    private final String desc;
    private final String value;

    /**
     * 获取值列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据值获取枚举
     *
     * @param value 键
     */
    public static GetCodeMethodEnum getEnumByValue(String value) {
        if (getValues().contains(value)) {
            for (GetCodeMethodEnum codeMethodEnum : GetCodeMethodEnum.values()) {
                if (codeMethodEnum.value.equals(value)) {
                    return codeMethodEnum;
                }
            }
        }
        return null;
    }
}
