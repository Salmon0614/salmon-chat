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
public enum UserContactStatusEnum {

    NOT_FRIEND("非好友", 0),
    FRIEND("好友", 1),
    DEL("已删除好友", 2),
    BE_DEL("被好友删除", 3),
    BLACK("拉黑好友", 4),
    BE_BLACK("被好友拉黑", 5),
    FIRST_BE_BLACK("首次被好友拉黑", 6);

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
    public static UserContactStatusEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (UserContactStatusEnum userContactStatusEnum : UserContactStatusEnum.values()) {
                if (userContactStatusEnum.value == value) {
                    return userContactStatusEnum;
                }
            }
        }
        return null;
    }
}
