package com.salmon.chatService.model.vo.user;

import com.salmon.chatService.common.UserRoleEnum;
import lombok.Data;
import com.salmon.chatService.model.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 用户视图
 * </p>
 *
 * @author Salmon
 * @since 2024-06-04
 */
@Data
@Schema(name = "UserVO", description = "用户视图")
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "性别 0-女 1-男")
    private Integer gender;

    @Schema(description = "地区")
    private String area;

    @Schema(description = "地区编号")
    private String areaCode;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态 0-禁用 1-启用")
    private Integer status;

    @Schema(description = "登录凭证")
    private String token;

    @Schema(description = "角色 0-普通用户 1-管理员")
    private Integer role;

    @Schema(description = "是否是管理员")
    private Boolean isAdmin;

    @Schema(description = "加入好友类型 0-直接加入 1-需要验证")
    private Integer joinType;

    public Boolean getIsAdmin() {
        if(Objects.isNull(role)) {
            return false;
        }
        return role == UserRoleEnum.ADMIN.getValue();
    }

    /**
     * 封装类转对象
     *
     * @param userVO 视图对象
     * @return 数据库对象
     */
    public static User voToObj(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        return user;
    }

    /**
     * 对象转封装类
     *
     * @param user 数据库对象
     * @return 视图对象
     */
    public static UserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
