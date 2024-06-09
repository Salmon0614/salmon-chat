package com.salmon.chatService.model.enums.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 申请状态枚举
 *
 * @author Salmon
 * @since 2024-06-10
 */
@Getter
@AllArgsConstructor
public enum ContactApplyStatusEnum {

    WAIT("待处理", 0),
    PASS("已同意", 1),
    REJECT("已拒绝", 2),
    BLACK("已拉黑", 3);

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
    public static ContactApplyStatusEnum getEnumByValue(int value) {
        if (getTypes().contains(value)) {
            for (ContactApplyStatusEnum applyStatusEnum : ContactApplyStatusEnum.values()) {
                if (applyStatusEnum.value == value) {
                    return applyStatusEnum;
                }
            }
        }
        return null;
    }
}
