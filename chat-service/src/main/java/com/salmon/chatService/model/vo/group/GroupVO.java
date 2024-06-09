package com.salmon.chatService.model.vo.group;

import lombok.Data;
import com.salmon.chatService.model.po.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 群聊视图
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@Data
@Schema(name = "GroupVO", description = "群聊视图")
public class GroupVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "群聊号")
    private String groupNumber;

    @Schema(description = "群名")
    private String groupName;

    @Schema(description = "群主ID")
    private Integer groupOwnerId;

    @Schema(description = "群封面")
    private String groupCover;

    @Schema(description = "群主账号")
    private String groupOwnerAccount;

    @Schema(description = "群通知")
    private String groupNotice;

    @Schema(description = "加入类型 0-直接加入 1-管理员同意加入")
    private Integer joinType;

    @Schema(description = "状态 0-正常 1-解散")
    private Integer status;

    @Schema(description = "群成员数量")
    private Integer memberCount;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;


    /**
     * 封装类转对象
     *
     * @param groupVO 视图对象
     * @return 数据库对象
     */
    public static Group voToObj(GroupVO groupVO) {
        if (groupVO == null) {
            return null;
        }
        Group group = new Group();
        BeanUtils.copyProperties(groupVO, group);
        return group;
    }

    /**
     * 对象转封装类
     *
     * @param group 数据库对象
     * @return 视图对象
     */
    public static GroupVO objToVo(Group group) {
        if (group == null) {
            return null;
        }
        GroupVO groupVO = new GroupVO();
        BeanUtils.copyProperties(group, groupVO);
        return groupVO;
    }
}
