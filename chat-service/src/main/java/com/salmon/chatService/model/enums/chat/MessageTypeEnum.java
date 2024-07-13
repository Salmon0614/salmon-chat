package com.salmon.chatService.model.enums.chat;

import com.salmon.chatService.model.enums.chatMessage.ChatMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息类型枚举
 *
 * @author Salmon
 * @since 2024-07-13
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

    INIT(0, "", "连接ws信息"),
    ADD_FRIEND(1, "", "添加好友打招呼消息"),
    CHAT(2, "", "普通聊天消息"),
    GROUP_CREATE(3, "群聊已经创建好，可以和好友一起畅聊啦", "群创建成功"),
    CONTACT_APPLY(4, "", "好友申请"),
    MEDIA_CHAT(5, "", "媒体文件"),
    FILE_UPLOAD(6, "", "文件上传完成"),
    FORCE_OFF_LINE(7, "", "强制下线"),
    DISSOLUTION_GROUP(8, "群聊已解散", "解散群聊"),
    ADD_GROUP(9, "%s加入了群聊", "加入群聊"),
    GROUP_NAME_UPDATE(10, "", "更新群昵称"),
    LEAVE_GROUP(11, "%s退出了群聊", "退出群聊"),
    REMOVE_GROUP(12, "%s被管理员移出了群聊", "被管理员移出了群聊");

    private final Integer type;
    private final String initMessage;
    private final String desc;

    /**
     * 获取值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.type).collect(Collectors.toList());
    }

    /**
     * 根据值获取枚举
     *
     * @param value 键
     */
    public static MessageTypeEnum getEnumByValue(int value) {
        if (getValues().contains(value)) {
            for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
                if (messageTypeEnum.type == value) {
                    return messageTypeEnum;
                }
            }
        }
        return null;
    }
}
