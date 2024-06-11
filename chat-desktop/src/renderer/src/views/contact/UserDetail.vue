<script setup>
import { ref, reactive, getCurrentInstance, nextTick, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useContactStateStore } from '@/stores/contactStateStore'

const contactStateStore = useContactStateStore()
const { proxy } = getCurrentInstance()
const route = useRoute()
const router = useRouter()
const userInfo = ref({})
const loadUserDetail = async (contactId) => {
  let result = await proxy.$request({
    url: proxy.$api.contact.getContactUserInfo,
    params: {
      id: contactId
    }
  })
  if (!result || !result.isSuccess) {
    return
  }
  userInfo.value = result.data
}
/**
 * 加入黑名单
 * @returns {Promise<void>}
 */
const addContactToBlackList = async () => {
  proxy.$confirm({
    message: '您确定要将该联系人加入黑名单吗？',
    okfun: async () => {
      let result = await proxy.$request({
        url: proxy.$api.contact.addContactToBlackList,
        params: {
          id: userInfo.value.id
        }
      })
      if (!result || !result.isSuccess) {
        return
      }
      delContactData()
    }
  })
}
/**
 * 删除联系人
 * @returns {Promise<void>}
 */
const delContact = async () => {
  proxy.$confirm({
    message: '您确定要删除该联系人吗？',
    okfun: async () => {
      let result = await proxy.$request({
        url: proxy.$api.contact.delContact,
        params: {
          id: userInfo.value.id
        }
      })
      if (!result || !result.isSuccess) {
        return
      }
      delContactData()
    }
  })
}

/**
 * 删除/拉黑联系人后续操作
 */
const delContactData = () => {
  contactStateStore.setContactReload(3)
}

/**
 * 发消息
 * @returns {Promise<void>}
 */
const sendMessage = async () => {}

/**
 * 监听状态
 */
watch(
  () => route.query.contactId,
  (newVal, oldVal) => {
    console.debug('userDetail watch...', newVal, oldVal)
    if (newVal == null) {
      return
    }
    loadUserDetail(newVal)
  },
  { immediate: true, deep: true }
)
</script>

<template>
  <ContentPanel>
    <div class="user-info">
      <UserBaseInfo :user-info="userInfo" :show-area="true"></UserBaseInfo>
      <div class="more-op">
        <el-dropdown placement="bottom-end" trigger="click">
          <span class="el-dropdown-link">
            <span class="iconfont icon-more"></span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="addContactToBlackList">加入黑名单</el-dropdown-item>
              <el-dropdown-item @click="delContact">删除联系人</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <div class="part-item">
      <div class="part-title">个性签名</div>
      <div class="part-content">{{ userInfo.description || '-' }}</div>
    </div>
    <div class="send-message" @click="sendMessage">
      <div class="iconfont icon-chat2"></div>
      <div class="text">发消息</div>
    </div>
  </ContentPanel>
</template>

<style scoped lang="scss">
.user-info {
  position: relative;

  .more-op {
    position: absolute;
    right: 0;
    top: 20px;

    .icon-more {
      color: #9e9e9e;

      &:hover {
        background: #dddddd;
      }
    }
  }
}

.part-item {
  display: flex;
  border-bottom: 1px solid #eaeaea;
  padding: 20px 0;

  .part-title {
    width: 60px;
    color: #9e9e9e;
  }

  .part-content {
    flex: 1;
    margin-left: 15px;
    color: #161616;
  }
}

.send-message {
  width: 80px;
  margin: 0 auto;
  text-align: center;
  margin-top: 20px;
  color: #7d8cac;
  padding: 5px;

  .icon-chat2 {
    font-size: 23px;
  }

  .text {
    font-size: 12px;
    margin-top: 5px;
  }

  &:hover {
    background: #e9e9e9;
    cursor: pointer;
  }
}
</style>
