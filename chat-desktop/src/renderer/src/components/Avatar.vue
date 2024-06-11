<script setup>
import { ref, getCurrentInstance } from 'vue'

import AvatarBase from '@/components/AvatarBase.vue'
import UserBaseInfo from '@/components/UserBaseInfo.vue'
import { useUserStore } from '@/stores/userStore'

const userStore = useUserStore()
const { proxy } = getCurrentInstance()

const props = defineProps({
  userId: {
    type: Number
  },
  account: {
    type: String
  },
  imageSrc: {
    type: String
  },
  width: {
    type: Number,
    default: 40
  },
  height: {
    type: Number,
    default: 40
  },
  borderRadius: {
    type: Number,
    default: 0
  },
  groupId: {
    type: String
  },
  isGroup: {
    type: Boolean,
    default: false
  },
  isRobot: {
    type: Boolean,
    default: false
  }
})

const userInfo = ref({})

/**
 * 是否自己，判断用户ID以及账号是否与当前登录的信息一致
 * @returns {boolean}
 */
const isSelf = () => {
  return (
    userStore.getUserInfo().id === props.userId && userStore.getUserInfo().account === props.account
  )
}
const getContactInfo = async () => {
  userInfo.value = {}
  userInfo.value.id = props.userId
  userInfo.value.account = props.account
  // 如果是当前登录的用户，直接本地取值
  if (isSelf()) {
    userInfo.value = userStore.getUserInfo()
  } else {
    // 获取线上数据
    let result = await proxy.$request({
      url: proxy.$api.contact.getContactInfo,
      params: {
        id: props.userId
      },
      showLoading: false
    })
    if (!result || !result.isSuccess) {
      return
    }
    userInfo.value = Object.assign({}, result.data)
  }
}

// todo 发现消息
const sendMessage = () => {}
// todo 添加好友
const addContact = () => {}
</script>

<template>
  <div>
    <AvatarBase
      v-if="isRobot || isGroup"
      :user-id="userId"
      :account="account"
      :width="width"
      :height="height"
      :border-radius="borderRadius"
      :show-detail="false"
      :image-src="imageSrc"
    ></AvatarBase>
    <el-popover
      v-else
      ref="popoverRef"
      :width="280"
      placement="right-start"
      :show-arrow="false"
      trigger="click"
      transition="none"
      :hide-after="0"
      @show="getContactInfo"
    >
      <template #reference>
        <AvatarBase
          :user-id="userId"
          :account="account"
          :width="width"
          :height="height"
          :border-radius="borderRadius"
          :show-detail="false"
          :image-src="imageSrc"
        ></AvatarBase>
      </template>
      <template #default>
        <div class="popover-user-panel">
          <UserBaseInfo :user-info="userInfo"></UserBaseInfo>
          <div v-if="!isSelf()" class="op-btn">
            <el-button v-if="userInfo.contactStatus === 1" type="primary" @click="sendMessage"
              >发送信息
            </el-button>
            <el-button v-else type="primary" @click="addContact">添加好友</el-button>
          </div>
        </div>
      </template>
    </el-popover>
  </div>
</template>

<style scoped lang="scss">
.op-btn {
  text-align: center;
  border-top: 1px solid #eaeaea;
  padding-top: 10px;
}
</style>
