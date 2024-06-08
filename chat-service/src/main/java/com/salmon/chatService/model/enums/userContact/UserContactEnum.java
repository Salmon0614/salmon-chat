package com.salmon.chatService.model.enums.userContact;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 联系人枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Getter
@AllArgsConstructor
public enum UserContactEnum {
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
    public static UserContactEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (UserContactEnum userContactEnum : UserContactEnum.values()) {
                if (userContactEnum.value == value) {
                    return userContactEnum;
                }
            }
        }
        return null;
    }
}
