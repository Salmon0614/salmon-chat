package com.salmon.chatService.model.vo.appUpdate;

import lombok.Data;
import com.salmon.chatService.model.po.AppUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * APP发布视图
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@Data
@Schema(name = "AppUpdateVO", description = "APP发布视图")
public class AppUpdateVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "APP版本号")
    private String appVersion;

    @Schema(description = "更新描述")
    private String updateDesc;

    @Schema(description = "更新描述数组")
    private String[] updateDescArray;

    @Schema(description = "状态 0-未发布 1-灰度发布 2-全网发布")
    private Integer status;

    @Schema(description = "文件类型 0-本地文件 1-外链")
    private Integer fileType;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "外链地址")
    private String outerLink;

    @Schema(description = "参与灰度发布的用户ID")
    private String grayscaleUid;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "发布时间")
    private LocalDateTime postTime;

    public String[] getUpdateDescArray() {
        if (!StringUtils.hasText(updateDesc)) {
            return new String[]{};
        }
        return updateDesc.split("\\|");
    }

    /**
     * 封装类转对象
     *
     * @param appUpdateVO 视图对象
     * @return 数据库对象
     */
    public static AppUpdate voToObj(AppUpdateVO appUpdateVO) {
        if (appUpdateVO == null) {
            return null;
        }
        AppUpdate appUpdate = new AppUpdate();
        BeanUtils.copyProperties(appUpdateVO, appUpdate);
        return appUpdate;
    }

    /**
     * 对象转封装类
     *
     * @param appUpdate 数据库对象
     * @return 视图对象
     */
    public static AppUpdateVO objToVo(AppUpdate appUpdate) {
        if (appUpdate == null) {
            return null;
        }
        AppUpdateVO appUpdateVO = new AppUpdateVO();
        BeanUtils.copyProperties(appUpdate, appUpdateVO);
        return appUpdateVO;
    }
}
