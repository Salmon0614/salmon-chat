package com.salmon.chatService.model.enums.appUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * APP发布状态枚举
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@Getter
@AllArgsConstructor
public enum AppUpdateStatusEnum {

    NONE("未发布", 0),
    GRAY_POST("灰度发布", 1),
    ALL_POST("全网发布", 2);

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
    public static AppUpdateStatusEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (AppUpdateStatusEnum appUpdateFileTypeEnum : AppUpdateStatusEnum.values()) {
                if (appUpdateFileTypeEnum.value == value) {
                    return appUpdateFileTypeEnum;
                }
            }
        }
        return null;
    }
}
