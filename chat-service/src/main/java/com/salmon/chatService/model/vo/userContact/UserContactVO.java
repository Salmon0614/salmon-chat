package com.salmon.chatService.model.vo.userContact;

import lombok.Data;
import com.salmon.chatService.model.po.UserContact;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 联系人视图
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Schema(name = "UserContactVO", description = "联系人视图")
public class UserContactVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "联系人ID或者群组ID")
    private Integer contactId;

    @Schema(description = "联系人类型 0-好友 1-群组")
    private Integer contactType;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "状态 0-非好友 1-好友 2-已删除好友 3-被好友删除 4-拉黑好友 5-被好友拉黑")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;


    /**
     * 封装类转对象
     *
     * @param userContactVO 视图对象
     * @return 数据库对象
     */
    public static UserContact voToObj(UserContactVO userContactVO) {
        if (userContactVO == null) {
            return null;
        }
        UserContact userContact = new UserContact();
        BeanUtils.copyProperties(userContactVO, userContact);
        return userContact;
    }

    /**
     * 对象转封装类
     *
     * @param userContact 数据库对象
     * @return 视图对象
     */
    public static UserContactVO objToVo(UserContact userContact) {
        if (userContact == null) {
            return null;
        }
        UserContactVO userContactVO = new UserContactVO();
        BeanUtils.copyProperties(userContact, userContactVO);
        return userContactVO;
    }
}
