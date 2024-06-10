package com.salmon.chatService.model.enums.contact;

import com.salmon.chatService.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 申请来源类型
 *
 * @author Salmon
 * @since 2024-06-10
 */
@Getter
@AllArgsConstructor
public enum ApplyOriginTypeEnum {

    ACCOUNT("搜索账号添加", 0),
    GROUP("群聊方式添加", 1),
    EMAIL("搜索邮箱添加", 2),
    MOBILE("搜索手机号添加", 3);

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
    public static ApplyOriginTypeEnum getEnumByValue(int value) {
        if (getTypes().contains(value)) {
            for (ApplyOriginTypeEnum originTypeEnum : ApplyOriginTypeEnum.values()) {
                if (originTypeEnum.value == value) {
                    return originTypeEnum;
                }
            }
        }
        return null;
    }

    /**
     * 根据搜索关键字识别类型
     *
     * @param keyword 关键字
     */
    public static ApplyOriginTypeEnum validType(String keyword) {
        if (Utils.isMobile(keyword)) {
            return MOBILE;
        } else if (Utils.isEmail(keyword)) {
            return EMAIL;
        } else {
            return ACCOUNT;
        }
    }
}
