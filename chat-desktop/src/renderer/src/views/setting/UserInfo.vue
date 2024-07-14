<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from 'vue'
import UserInfoEdit from './UserInfoEdit.vue'
import UserUpdatePassword from './UserUpdatePassword.vue'

const { proxy } = getCurrentInstance()

const userInfo = ref({})

/**
 * 获取用户信息
 * @returns {Promise<void>}
 */
const getUserInfo = async () => {
  let result = await proxy.$request({
    url: proxy.$api.userInfo.getUserInfo,
    params: {}
  })
  if (!result || !result.isSuccess) {
    return
  }
  userInfo.value = result.data
}

getUserInfo()

const showType = ref(0)

/**
 * 选择操作类型
 * @param type
 */
const changePart = (type) => {
  showType.value = type
}

/**
 * 编辑回调
 */
const editBack = () => {
  showType.value = 0
  getUserInfo()
}

/**
 * todo 退出登录
 */
const logout = () => {}
</script>

<template>
  <ContentPanel>
    <div class="show-info" v-if="showType === 0">
      <div class="user-info">
        <UserBaseInfo :user-info="userInfo" :show-area="true"></UserBaseInfo>
        <div class="more-op">
          <el-dropdown placement="bottom-end" trigger="click">
            <span class="el-dropdown-link">
              <div class="iconfont icon-more"></div>
            </span>
            <template #dropdown>
              <el-dropdown-item @click="changePart(1)"> 修改个人信息</el-dropdown-item>
              <el-dropdown-item @click="changePart(2)"> 修改密码</el-dropdown-item>
            </template>
          </el-dropdown>
        </div>
      </div>
      <div class="part-item">
        <div class="part-title">朋友权限</div>
        <div class="part-content">
          {{ userInfo.joinType === 0 ? '直接加入' : '加我为好友时需要验证' }}
        </div>
      </div>
      <div class="part-item">
        <div class="part-title">个性签名</div>
        <div class="part-content">
          {{ userInfo.description || '-' }}
        </div>
      </div>
      <div class="logout">
        <el-button @click="logout">退出登录</el-button>
      </div>
    </div>
    <div v-if="showType === 1">
      <UserInfoEdit :data="userInfo" @editBack="editBack"></UserInfoEdit>
    </div>
    <div v-if="showType === 2">
      <UserUpdatePassword @editBack="editBack"></UserUpdatePassword>
    </div>
  </ContentPanel>
</template>

<style scoped lang="scss">
.show-info {
  .user-info {
    position: relative;
  }

  .more-op {
    position: absolute;
    right: 0;
    top: 20px;

    .sicon-more {
      color: #9e9e9e;

      &:hover {
        background: #dddddd;
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

  .logout {
    text-align: center;
    margin-top: 20px;
  }
}
</style>
