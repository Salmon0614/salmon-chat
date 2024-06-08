package com.salmon.chatService.model.enums.userContactApply;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 联系人申请枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Getter
@AllArgsConstructor
public enum UserContactApplyEnum {
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
    public static UserContactApplyEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (UserContactApplyEnum userContactApplyEnum : UserContactApplyEnum.values()) {
                if (userContactApplyEnum.value == value) {
                    return userContactApplyEnum;
                }
            }
        }
        return null;
    }
}
