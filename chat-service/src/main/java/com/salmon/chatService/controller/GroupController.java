package com.salmon.chatService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.salmon.chatService.annotation.CheckAuth;
import com.salmon.chatService.common.*;
import com.salmon.chatService.exception.ThrowUtils;
import com.salmon.chatService.model.dto.group.GroupAddRequest;
import com.salmon.chatService.model.dto.group.GroupQueryRequest;
import com.salmon.chatService.model.dto.group.GroupSaveRequest;
import com.salmon.chatService.model.dto.group.GroupUpdateRequest;
import com.salmon.chatService.model.enums.group.GroupStatusEnum;
import com.salmon.chatService.model.po.Group;
import com.salmon.chatService.model.vo.account.TokenUserVo;
import com.salmon.chatService.model.vo.group.GroupChatVO;
import com.salmon.chatService.model.vo.group.GroupSimpleVO;
import com.salmon.chatService.model.vo.group.GroupVO;
import com.salmon.chatService.service.GroupService;
import com.salmon.chatService.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.salmon.chatService.common.BaseController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 群聊 前端控制器
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

    @Operation(summary = "退出群聊")
    @PostMapping("/leaveGroup")
    @CheckAuth
    public BaseResponse<?> leaveGroup(@RequestBody @Valid IdRequest request) {
        TokenUserVo tokenUser = UserHolder.getUser();
        // todo 退出群聊
        return ResultUtils.success();
    }

    @Operation(summary = "解散群聊")
    @PostMapping("/dissolutionGroup")
    @CheckAuth
    public BaseResponse<?> dissolutionGroup(@RequestBody @Valid IdRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        TokenUserVo tokenUser = UserHolder.getUser();
        // todo 解散群聊
        return ResultUtils.success();
    }

    @Operation(summary = "用户创建或修改群组")
    @PostMapping("/saveOrUpdateGroup")
    @CheckAuth
    public BaseResponse<Object> saveOrUpdateGroup(@RequestBody @Valid GroupSaveRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        TokenUserVo tokenUser = UserHolder.getUser();
        Group group = new Group();
        BeanUtils.copyProperties(request, group);
        group.setGroupOwnerId(tokenUser.getId());
        group.setGroupOwnerAccount(tokenUser.getAccount());
        group.setStatus(GroupStatusEnum.NORMAL.getValue());
        // todo 需要加入oss 上传前端群封面照片，这里先不处理
        groupService.saveOrUpdateGroup(group);
        return ResultUtils.success(group.getId());
    }

    @Operation(summary = "获取我创建的群组")
    @PostMapping("/loadMyGroup")
    @CheckAuth
    public BaseResponse<List<GroupSimpleVO>> loadMyGroup() {
        TokenUserVo tokenUser = UserHolder.getUser();
        Group group = new Group();
        group.setGroupOwnerId(tokenUser.getId());
        group.setStatus(GroupStatusEnum.NORMAL.getValue());
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>(group);
        queryWrapper.orderByDesc("id");
        List<Group> groupList = groupService.list(queryWrapper);
        if (CollectionUtils.isEmpty(groupList)) {
            return ResultUtils.success(new ArrayList<>());
        }
        List<GroupSimpleVO> groupSimpleVOS = groupList.stream().map(GroupSimpleVO::objToVo).collect(Collectors.toList());
        return ResultUtils.success(groupSimpleVOS);
    }


    @Operation(summary = "获取群聊信息")
    @PostMapping("/getGroupInfo")
    @CheckAuth
    public BaseResponse<GroupVO> getGroupInfo(@RequestBody @Valid IdRequest request) {
        GroupVO groupVO = groupService.getGroupInfo(request);
        return ResultUtils.success(groupVO);
    }

    @Operation(summary = "获取群聊详细信息（包括成员）")
    @PostMapping("/getGroupInfo4Chat")
    @CheckAuth
    public BaseResponse<GroupChatVO> getGroupInfo4Chat(@RequestBody @Valid IdRequest request) {
        GroupChatVO groupChatVO = groupService.getGroupInfo4Chat(request);
        return ResultUtils.success(groupChatVO);
    }


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
        UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setEntity(group);
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
    public BaseResponse<GroupVO> getGroupById(@RequestBody @Valid IdRequest request) {
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
