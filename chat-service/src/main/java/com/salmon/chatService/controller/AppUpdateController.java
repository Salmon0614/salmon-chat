package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.BusinessException;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.appUpdate.AppUpdateAddRequest;
import com.salmon.chatService.model.dto.appUpdate.AppUpdatePostRequest;
import com.salmon.chatService.model.dto.appUpdate.AppUpdateQueryRequest;
import com.salmon.chatService.model.dto.appUpdate.AppUpdateUpdateRequest;
import com.salmon.chatService.model.enums.appUpdate.AppUpdateFileTypeEnum;
import com.salmon.chatService.model.enums.appUpdate.AppUpdateStatusEnum;
import com.salmon.chatService.model.po.AppUpdate;
import com.salmon.chatService.model.vo.appUpdate.AppUpdateVO;
import com.salmon.chatService.service.AppUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.salmon.chatService.common.BaseController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * APP发布 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-07-10
 */
@RestController
@RequestMapping("/admin/appUpdate")
@Slf4j
@Tag(name = "AppUpdateController", description = "APP发布前端控制器")
public class AppUpdateController extends BaseController {

    @Resource
    private AppUpdateService appUpdateService;

    @Operation(summary = "发布更新")
    @PostMapping("/post")
    @CheckAuth(needAdmin = true)
    public BaseResponse<?> postAppUpdate(@RequestBody @Valid AppUpdatePostRequest request) {
        AppUpdate appUpdate = appUpdateService.getById(request.getId());
        ThrowUtils.throwIf(appUpdate == null, ErrorCode.NOT_FOUND_ERROR);
        ThrowUtils.throwIf(appUpdate.getStatus() != AppUpdateStatusEnum.NONE.getValue(), "该版本已发布，禁止修改！");
        BeanUtils.copyProperties(request, appUpdate);
        if (request.getStatus() == AppUpdateStatusEnum.ALL_POST.getValue()) {
            appUpdate.setGrayscaleUid("");
        }
        ThrowUtils.throwIf(!appUpdateService.updateById(appUpdate), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success();
    }

    @Operation(summary = "添加APP发布")
    @PostMapping("/add")
    @CheckAuth(needAdmin = true)
    public BaseResponse<?> addAppUpdate(@RequestBody @Valid AppUpdateAddRequest request) {
        checkAppUpdate(null, request.getAppVersion(), request.getFileType(), request.getOuterLink());
        AppUpdate appUpdate = new AppUpdate();
        BeanUtils.copyProperties(request, appUpdate);
        appUpdate.setStatus(AppUpdateStatusEnum.NONE.getValue());
        ThrowUtils.throwIf(!appUpdateService.save(appUpdate), ErrorCode.OPERATION_ERROR);
        // 上传文件
        return ResultUtils.success();
    }

    @Operation(summary = "修改APP发布")
    @PostMapping("/update")
    @CheckAuth(needAdmin = true)
    public BaseResponse<?> updateAppUpdate(@RequestBody @Valid AppUpdateUpdateRequest request) {
        checkAppUpdate(request.getId(), request.getAppVersion(), request.getFileType(), request.getOuterLink());
        AppUpdate appUpdate = appUpdateService.getById(request.getId());
        ThrowUtils.throwIf(appUpdate == null, ErrorCode.NOT_FOUND_ERROR);
        ThrowUtils.throwIf(appUpdate.getStatus() != AppUpdateStatusEnum.NONE.getValue(), "该版本已发布，禁止删除！");
        BeanUtils.copyProperties(request, appUpdate);
        ThrowUtils.throwIf(!appUpdateService.updateById(appUpdate), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success();
    }

    /**
     * 校验更新公共逻辑
     *
     * @param version   版本号，如：1.0.0
     * @param fileType  文件类型
     * @param outerLink 外链
     */
    private void checkAppUpdate(Integer id, String version, Integer fileType, String outerLink) {
        ThrowUtils.throwIf(fileType == AppUpdateFileTypeEnum.OUTER.getValue() && !StringUtils.hasText(outerLink), "外链地址不能为空");
        LambdaQueryWrapper<AppUpdate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppUpdate::getAppVersion, version);
        if (Objects.nonNull(id)) {
            queryWrapper.eq(AppUpdate::getId, id);
        }
        ThrowUtils.throwIf(appUpdateService.count(queryWrapper) > 0, "已存在该版本的APP！");
        List<AppUpdate> appUpdates = appUpdateService.list(new QueryWrapper<AppUpdate>().orderByDesc("id").last("limit 0,1"));
        if (!CollectionUtils.isEmpty(appUpdates)) {
            AppUpdate lastAppUpdate = appUpdates.get(0);
            long lastVersion = Long.parseLong(lastAppUpdate.getAppVersion().replace(".", ""));
            long curVersion = Long.parseLong(version.replace(".", ""));
            if (Objects.isNull(id) && curVersion <= lastVersion) {
                throw new BusinessException("当前版本必须大于历史版本！");
            }
            if (Objects.nonNull(id) && !lastAppUpdate.getId().equals(id) && curVersion <= lastVersion) {
                throw new BusinessException("当前版本必须大于历史版本！");
            }
        }
    }

    @Operation(summary = "删除APP发布")
    @PostMapping("/delete")
    @CheckAuth(needAdmin = true)
    public BaseResponse<?> deleteAppUpdate(@RequestBody @Valid DeleteRequest request) {
        AppUpdate appUpdate = appUpdateService.getById(request.getId());
        ThrowUtils.throwIf(appUpdate.getStatus() != AppUpdateStatusEnum.NONE.getValue(), "该版本已发布，禁止删除！");
        ThrowUtils.throwIf(!appUpdateService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success();
    }


    @Operation(summary = "分页查询APP发布")
    @PostMapping("/queryPage")
    @CheckAuth(needAdmin = true)
    public BaseResponse<Page<AppUpdateVO>> queryAppUpdatePage(@RequestBody @Valid AppUpdateQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        LambdaQueryWrapper<AppUpdate> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(request.getCreateTimeBegin())) {
            queryWrapper.ge(AppUpdate::getCreateTime, request.getCreateTimeBegin());
        }
        if (Objects.nonNull(request.getCreateTimeEnd())) {
            queryWrapper.le(AppUpdate::getCreateTime, request.getCreateTimeEnd());
        }
        queryWrapper.orderByDesc(AppUpdate::getId);
        Page<AppUpdate> page = appUpdateService.page(new Page<>(current, size), queryWrapper);
        Page<AppUpdateVO> appUpdateVOPage = new Page<>(current, size);
        List<AppUpdateVO> appUpdateVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(page.getRecords())) {
            appUpdateVOS = page.getRecords().stream().map(AppUpdateVO::objToVo).collect(Collectors.toList());
        }
        appUpdateVOPage.setRecords(appUpdateVOS);
        return ResultUtils.success(appUpdateVOPage);
    }
}
