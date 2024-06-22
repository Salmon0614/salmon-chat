<script setup>
import { ref, reactive, getCurrentInstance, nextTick, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { useContactStateStore } from '@/stores/contactStateStore'
import GroupEditDialog from './GroupEditDialog.vue'

const contactStateStore = useContactStateStore()
const userStore = useUserStore()
const { proxy } = getCurrentInstance()
const route = useRoute()
const router = useRouter()

const groupInfo = ref({})
const groupId = ref()
const groupEditDialogRef = ref()
const getGroupInfo = async () => {
  let result = await proxy.$request({
    url: proxy.$api.group.getGroupInfo,
    params: {
      id: groupId.value
    }
  })
  if (!result || !result.isSuccess) {
    return
  }
  groupInfo.value = result.data
}

/**
 * 监听状态
 */
watch(
  () => route.query.contactId,
  (newVal, oldVal) => {
    console.debug('groupDetail watch...', newVal, oldVal)
    if (newVal == null) {
      return
    }
    groupId.value = newVal
    getGroupInfo()
  },
  { immediate: true, deep: true }
)
/**
 * 编辑群信息
 */
const editGroupInfo = () => {
  groupEditDialogRef.value.show(groupInfo.value)
}
/**
 * 解散群聊
 */
const dissolutionGroup = () => {
  proxy.$confirm({
    message: '您确定要解散群聊吗？解散后无法恢复',
    okfun: async () => {
      contactStateStore.setContactReload(null)
      let result = await proxy.$request({
        url: proxy.$api.group.dissolutionGroup,
        params: {
          id: groupInfo.value.id
        }
      })
      if (!result || !result.isSuccess) {
        return
      }
      proxy.$message.success('解散成功')
      contactStateStore.setContactReload(4)
    }
  })
}
/**
 * 退出群聊
 */
const leaveGroup = () => {
  proxy.$confirm({
    message: '您确定要退出群聊？',
    okfun: async () => {
      contactStateStore.setContactReload(null)
      let result = await proxy.$request({
        url: proxy.$api.group.leaveGroup,
        params: {
          id: groupInfo.value.id
        }
      })
      if (!result || !result.isSuccess) {
        return
      }
      proxy.$message.success('退出成功')
      contactStateStore.setContactReload(5)
    }
  })
}
/**
 * 发送群消息
 */
const sendMessage = () => {
  router.push({
    path: '/chat',
    query: { chatAccount: groupInfo.value.groupNumber, timestamp: new Date().getTime() }
  })
}
</script>

<template>
  <ContentPanel>
    <div class="group-info-item">
      <div class="group-title">群封面：</div>
      <div class="group-value">
        <Avatar
          :is-group="true"
          :user-id="groupInfo.groupId"
          :image-src="groupInfo.groupCover"
        ></Avatar>
      </div>
      <el-dropdown placement="bottom-end" trigger="click">
        <span class="el-dropdown-link">
          <div class="iconfont icon-more"></div>
        </span>
        <template #dropdown>
          <el-dropdown-menu v-if="groupInfo.groupOwnerId === userStore.getUserInfo().id">
            <el-dropdown-item @click="editGroupInfo">修改群信息</el-dropdown-item>
            <el-dropdown-item @click="dissolutionGroup">解散该群聊</el-dropdown-item>
          </el-dropdown-menu>
          <el-dropdown-menu v-else>
            <el-dropdown-item @click="leaveGroup">退出群聊</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <div class="group-info-item">
      <div class="group-title">群账号：</div>
      <div class="group-value">{{ groupInfo.groupNumber }}</div>
    </div>
    <div class="group-info-item">
      <div class="group-title">群名称：</div>
      <div class="group-value">{{ groupInfo.groupName }}</div>
    </div>
    <div class="group-info-item">
      <div class="group-title">群成员：</div>
      <div class="group-value">{{ groupInfo.memberCount }}</div>
    </div>
    <div class="group-info-item">
      <div class="group-title">加入权限：</div>
      <div class="group-value">
        {{ groupInfo.joinType === 0 ? '直接加入' : '管理员同意后加入' }}
      </div>
    </div>
    <div class="group-info-item notice">
      <div class="group-title">公告：</div>
      <div class="group-value">{{ groupInfo.groupNotice || '-' }}</div>
    </div>
    <div class="group-info-item">
      <div class="group-title"></div>
      <div class="group-value">
        <el-button type="primary" @click="sendMessage">发送群消息</el-button>
      </div>
    </div>
  </ContentPanel>
  <GroupEditDialog ref="groupEditDialogRef" @reload-group-info="getGroupInfo"></GroupEditDialog>
</template>

<style scoped lang="scss">
.group-info-item {
  display: flex;
  margin: 15px 0;
  align-items: center;

  .group-title {
    width: 100px;
    text-align: right;
  }

  .group-value {
    flex: 1;
  }
}

.notice {
  align-items: flex-start;
}
</style>
