package com.salmon.chatService.model.vo.account;

import com.salmon.chatService.model.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录 token
 *
 * @author Salmon
 * @since 2024-06-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "TokenUser", description = "用户登录 token")
public class TokenUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "登录凭证")
    private String token;

    @Schema(description = "角色 0-普通用户 1-管理员")
    private Integer role;

    /**
     * 对象转封装类
     *
     * @param user 数据库对象
     * @return 视图对象
     */
    public static TokenUserVo objToVo(User user) {
        if (user == null) {
            return null;
        }
        TokenUserVo tokenUserVo = new TokenUserVo();
        BeanUtils.copyProperties(user, tokenUserVo);
        return tokenUserVo;
    }
}
