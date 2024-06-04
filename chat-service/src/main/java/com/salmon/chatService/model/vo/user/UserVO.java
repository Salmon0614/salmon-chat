package com.salmon.chatService.model.vo.user;

import lombok.Data;
import com.salmon.chatService.model.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别 0-女 1-男")
    private Boolean gender;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "盐")
    private String secret;

    @Schema(description = "地区")
    private String area;

    @Schema(description = "地区编号")
    private String areaCode;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "状态 0-禁用 1-启用")
    private Boolean status;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "加入好友类型 0-直接加入 1-需要验证")
    private Boolean joinType;

    @Schema(description = "最近登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "离线时间")
    private LocalDateTime lastOffTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否删除 0-未删除 1-已删除")
    private Boolean isDelete;


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
