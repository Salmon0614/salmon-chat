package com.salmon.chatService.model.vo.userBeauty;

import lombok.Data;
import com.salmon.chatService.model.po.UserBeauty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 靓号视图
 * </p>
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Data
@Schema(name = "UserBeautyVO", description = "靓号视图")
public class UserBeautyVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "使用的用户ID")
    private Integer userId;

    @Schema(description = "状态 0-未使用 1-已使用")
    private Integer status;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;


    /**
     * 封装类转对象
     *
     * @param userBeautyVO 视图对象
     * @return 数据库对象
     */
    public static UserBeauty voToObj(UserBeautyVO userBeautyVO) {
        if (userBeautyVO == null) {
            return null;
        }
        UserBeauty userBeauty = new UserBeauty();
        BeanUtils.copyProperties(userBeautyVO, userBeauty);
        return userBeauty;
    }

    /**
     * 对象转封装类
     *
     * @param userBeauty 数据库对象
     * @return 视图对象
     */
    public static UserBeautyVO objToVo(UserBeauty userBeauty) {
        if (userBeauty == null) {
            return null;
        }
        UserBeautyVO userBeautyVO = new UserBeautyVO();
        BeanUtils.copyProperties(userBeauty, userBeautyVO);
        return userBeautyVO;
    }
}
