package com.salmon.chatService.model.vo.userContactApply;

import lombok.Data;
import com.salmon.chatService.model.po.UserContactApply;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 联系人申请视图
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Schema(name = "UserContactApplyVO", description = "联系人申请视图")
public class UserContactApplyVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "申请人ID")
    private Integer applyUserId;

    @Schema(description = "接收人ID")
    private Integer receiveUserId;

    @Schema(description = "联系人类型 0-好友 1-群聊")
    private Integer contactType;

    @Schema(description = "联系人/群组ID")
    private Integer contactId;

    @Schema(description = "最近申请时间")
    private LocalDateTime lastApplyTime;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "状态 0-待处理 1-已同意 2-已拒绝 3-已拉黑")
    private Integer status;

    @Schema(description = "申请信息")
    private String applyInfo;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;


    /**
     * 封装类转对象
     *
     * @param userContactApplyVO 视图对象
     * @return 数据库对象
     */
    public static UserContactApply voToObj(UserContactApplyVO userContactApplyVO) {
        if (userContactApplyVO == null) {
            return null;
        }
        UserContactApply userContactApply = new UserContactApply();
        BeanUtils.copyProperties(userContactApplyVO, userContactApply);
        return userContactApply;
    }

    /**
     * 对象转封装类
     *
     * @param userContactApply 数据库对象
     * @return 视图对象
     */
    public static UserContactApplyVO objToVo(UserContactApply userContactApply) {
        if (userContactApply == null) {
            return null;
        }
        UserContactApplyVO userContactApplyVO = new UserContactApplyVO();
        BeanUtils.copyProperties(userContactApply, userContactApplyVO);
        return userContactApplyVO;
    }
}
