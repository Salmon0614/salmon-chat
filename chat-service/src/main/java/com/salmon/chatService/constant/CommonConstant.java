package com.salmon.chatService.constant;

/**
 * 通用常量
 *
 * @author Salmon
 * @since 2024-05-19
 */
public interface CommonConstant {

    /**
     * 升序
     */
    String SORT_ORDER_ASC = "ascend";

    /**
     * 降序
     */
    String SORT_ORDER_DESC = " descend";

    /**
     * 响应 JSON 格式
     */
    String APPLICATION_JSON = "application/json;charset=utf-8";

    /**
     * header头token字段
     */
    String TOKEN = "token";

    /**
     * 添加用户/加入群的默认申请信息
     */
    String APPLY_INFO_FOR_USER = "我是%s";

    /**
     * 从群中添加好友的默认申请信息
     */
    String APPLY_INFO_FOR_GROUP = "我是群聊“%s”群的%s";

}
