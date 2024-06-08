package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.group.GroupAddRequest;
import com.salmon.chatService.model.dto.group.GroupQueryRequest;
import com.salmon.chatService.model.dto.group.GroupUpdateRequest;
import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.model.vo.group.GroupVO;
import com.salmon.chatService.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.salmon.chatService.common.BaseController;
import javax.annotation.Resource;

/**
 * <p>
 * 群组 前端控制器
 * </p>
 *
 * @author Salmon
 * @since 2024-06-08
 */
@RestController
@RequestMapping("/group")
@Slf4j
@Tag(name = "GroupController", description = "群组前端控制器")
public class GroupController extends BaseController {

    @Resource
    private GroupService groupService;

    @Operation(summary = "添加群组")
    @PostMapping("/add")
    public BaseResponse<Object> addGroup(@RequestBody GroupAddRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        Group group = new Group();
        BeanUtils.copyProperties(request, group);
        ThrowUtils.throwIf(!groupService.save(group), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(group.getId());
    }

    @Operation(summary = "修改群组")
    @PostMapping("/update")
    public BaseResponse<Object> updateGroup(@RequestBody GroupUpdateRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        Group group = new Group();
        BeanUtils.copyProperties(request, group);
        UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>(group);
        ThrowUtils.throwIf(!groupService.update(updateWrapper), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(group.getId());
    }

    @Operation(summary = "删除群组")
    @PostMapping("/delete")
    public BaseResponse<Object> deleteGroup(@RequestBody DeleteRequest request) {
        ThrowUtils.throwIf(request == null || request.getId() <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(!groupService.removeById(request.getId()), ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(); 
    }

    @Operation(summary = "根据ID查询群组")
    @PostMapping("/getById")
    public BaseResponse<GroupVO> getGroupById(@RequestBody IdRequest request) {
        Group group = groupService.getById(request.getId());
        GroupVO groupVO = new GroupVO();
        BeanUtils.copyProperties(group, groupVO);
        return ResultUtils.success(groupVO);
    }

    @Operation(summary = "分页查询群组")
    @PostMapping("/queryPage")
    public BaseResponse<Page<Group>> queryGroupPage(@RequestBody GroupQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        // todo 查询条件
        Page<Group> page = groupService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(page);
    }
}
