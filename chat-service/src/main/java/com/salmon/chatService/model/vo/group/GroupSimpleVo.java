package com.salmon.chatService.model.vo.group;

import com.salmon.chatService.model.po.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 群组视图（好友列表页）
 *
 * @author Salmon
 * @since 2024-06-09
 */
@Data
@Schema(name = "GroupSimpleVo", description = "群组视图（好友列表页）")
public class GroupSimpleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "群号")
    private String groupNumber;

    @Schema(description = "群名")
    private String groupName;

    @Schema(description = "群主ID")
    private Integer groupOwnerId;

    @Schema(description = "群封面")
    private String groupCover;

    /**
     * 封装类转对象
     *
     * @param groupSimpleVo 视图对象
     * @return 数据库对象
     */
    public static Group voToObj(GroupSimpleVo groupSimpleVo) {
        if (groupSimpleVo == null) {
            return null;
        }
        Group group = new Group();
        BeanUtils.copyProperties(groupSimpleVo, group);
        return group;
    }

    /**
     * 对象转封装类
     *
     * @param group 数据库对象
     * @return 视图对象
     */
    public static GroupSimpleVo objToVo(Group group) {
        if (group == null) {
            return null;
        }
        GroupSimpleVo groupSimpleVo = new GroupSimpleVo();
        BeanUtils.copyProperties(group, groupSimpleVo);
        return groupSimpleVo;
    }
}
