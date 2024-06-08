<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router'
import WinOp from '../components/WinOp.vue'

const router = useRouter()
const { proxy } = getCurrentInstance()
const userStore = useUserStore()
const isMac = window.electron.process.platform === 'darwin'

const formData = ref({})
const formDataRef = ref()

// 0-登录页 1-注册页 2-忘记密码
const viewType = ref(0)
/**
 * 改变页面类型、和调整窗口大小
 * @param type
 */
const changeType = (type) => {
  // 发生渲染进程通信
  window.changeWindowSize.changeLoginWindow(type)
  cleanVerify()
  nextTick(() => {
    formDataRef.value.resetFields()
    const oldType = viewType.value
    // 注册页返回登录页情况
    if (type === 0 && oldType === 1) {
      formData.value = { email: null, agree: false }
    } else if (oldType !== 1) {
      // 修改密码与登录界面来回切换，不置空邮箱
      const email = formData.value.email
      formData.value = { email: email, agree: false }
    } else {
      // 其它页面切换表单全部恢复初值
      formData.value = { email: null, agree: false }
    }
    changeCheckCode('sys')
  })
  viewType.value = type
}

/**
 * 显示加载
 * @type {Ref<UnwrapRef<boolean>>}
 */
const showLoading = ref(false)

/**
 * 获取验证码
 */
const checkCodeUrl = ref(null)
/**
 * 获取验证码
 *
 * @param type 0-点击 1-切换
 * @returns {Promise<void>}
 */
const changeCheckCode = async (type = 'click') => {
  let result = await proxy.$request({
    url: proxy.$api.account.getCheckCode,
    headersConfig: {
      type: type
    }
  })
  if (!result.isSuccess) {
    return
  }
  checkCodeUrl.value = result.data.base64
  localStorage.setItem('checkCodeKey', result.data.codeKey)
}

changeCheckCode('sys')

/**
 * 登录
 */
const login = async () => {
  const checkCodeKey = localStorage.getItem('checkCodeKey')
  if (!checkCodeKey) {
    proxy.$message.error('请刷新验证码')
    return
  }
  showLoading.value = true
  let result = await proxy.$request({
    url: proxy.$api.account.loginByEmail,
    showLoading: false,
    showError: false,
    params: {
      email: formData.value.email,
      password: formData.value.password,
      checkCode: formData.value.checkCode,
      checkCodeKey: checkCodeKey
    },
    errorCallback: (response) => {
      showLoading.value = false
      changeCheckCode('sys')
      errorMsg.value = response.message
    }
  })
  if (!result || !result.isSuccess) {
    return
  }
  router.push('/main')
  userStore.setUserInfo(result.data)
  localStorage.setItem('token', result.data.token)
  const screenWidth = window.screen.width
  const screenHeight = window.screen.height
  window.changeWindowSize.changeChatWindow({
    email: result.data.email,
    token: result.data.token,
    account: result.data.account,
    mobile: result.data.mobile,
    nickname: result.data.nickname,
    isAdmin: result.data.isAdmin,
    userId: result.data.id,
    screenWidth: screenWidth,
    screenHeight: screenHeight
  })
}
/**
 * 注册
 */
const register = async () => {
  const checkCodeKey = localStorage.getItem('checkCodeKey')
  if (!checkCodeKey) {
    proxy.$message.error('请刷新验证码')
    return
  }
  let result = await proxy.$request({
    url: proxy.$api.account.registerByEmail,
    showLoading: true,
    showError: false,
    params: {
      email: formData.value.email,
      password: formData.value.password,
      nickname: formData.value.nickname,
      checkCode: formData.value.checkCode,
      checkCodeKey: checkCodeKey
    },
    errorCallback: (response) => {
      showLoading.value = false
      changeCheckCode('sys')
      errorMsg.value = response.message
    }
  })
  if (!result.isSuccess) {
    return
  }
  proxy.$message.success('注册成功')
  // 切换到登录页
  changeType(0)
}
/**
 * 修改密码
 */
const updatePassword = async () => {}

const errorMsg = ref(null)

/**
 * 提交表单
 */
const submit = async () => {
  // 使用自定义校验
  cleanVerify()
  if (!proxy.$verify.validateEmail(formData.value.email)) {
    errorMsg.value = '请输入正确的邮箱'
    return
  }
  if (viewType.value === 1 && !proxy.$verify.validatePassword(formData.value.password)) {
    errorMsg.value = '密码至少8个字符，包含大小写字母和数字'
    return
  }
  if (viewType.value !== 0 && formData.value.password !== formData.value.rePassword) {
    errorMsg.value = '两次输入密码不一致'
    return
  }
  if (viewType.value === 0) {
    await login()
  } else if (viewType.value === 1) {
    await register()
  } else {
    await updatePassword()
  }
}

// 是否禁用提交按钮
const isDisabled = () => {
  if (
    proxy.$utils.isEmpty(formData.value.email) ||
    proxy.$utils.isEmpty(formData.value.password) ||
    proxy.$utils.isEmpty(formData.value.checkCode)
  ) {
    return true
  }
  if (!formData.value.agree) {
    return true
  }
  if (
    viewType.value === 1 &&
    (proxy.$utils.isEmpty(formData.value.rePassword) ||
      proxy.$utils.isEmpty(formData.value.nickname))
  ) {
    return true
  }
  return viewType.value === 2 && proxy.$utils.isEmpty(formData.value.rePassword)
}

// 清空校验信息
const cleanVerify = () => {
  errorMsg.value = null
}
</script>

<template>
  <div class="login-panel">
    <div class="title drag">SalmonChat</div>
    <div v-if="showLoading" class="loading-panel">
      <img src="@/assets/img/loading.gif" />
    </div>
    <div class="login-form">
      <el-form ref="formDataRef" :model="formData" label-width="0px" @submit.prevent>
        <el-form-item prop="email">
          <el-input
            v-model.trim="formData.email"
            size="large"
            clearable
            placeholder="请输入邮箱"
            @focus="cleanVerify"
          >
            <template #prefix>
              <span class="iconfont icon-email"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="viewType === 1" prop="nickname">
          <el-input
            v-model.trim="formData.nickname"
            size="large"
            clearable
            placeholder="请输入昵称"
            maxlength="20"
            @focus="cleanVerify"
          >
            <template #prefix>
              <span class="iconfont icon-user-nick"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model.trim="formData.password"
            size="large"
            clearable
            show-password
            placeholder="请输入密码"
            @focus="cleanVerify"
          >
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="viewType === 1 || viewType === 2" prop="rePassword">
          <el-input
            v-model.trim="formData.rePassword"
            size="large"
            clearable
            show-password
            placeholder="请再次输入密码"
            @focus="cleanVerify"
          >
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="checkCode">
          <div class="check-code-panel">
            <el-input
              v-model.trim="formData.checkCode"
              size="large"
              clearable
              placeholder="请输入验证码"
              @focus="cleanVerify"
            >
              <template #prefix>
                <span class="iconfont icon-checkcode"></span>
              </template>
            </el-input>
            <img :src="checkCodeUrl" class="check-code" @click="changeCheckCode()" />
          </div>
        </el-form-item>
        <div class="error-msg">{{ errorMsg }}</div>
        <el-form-item>
          <el-button
            v-if="viewType === 0"
            class="login-btn"
            :disabled="isDisabled()"
            type="primary"
            @click="submit"
            >登录
          </el-button>
          <el-button
            v-if="viewType === 1"
            class="login-btn"
            :disabled="isDisabled()"
            type="primary"
            @click="submit"
          >
            注册
          </el-button>
          <el-button
            v-if="viewType === 2"
            class="login-btn"
            :disabled="isDisabled()"
            type="primary"
            @click="submit"
          >
            修改密码
          </el-button>
        </el-form-item>
        <el-checkbox
          v-if="viewType === 0"
          v-model="formData.agree"
          size="large"
          :checked="formData.agree"
        >
          <span class="agree-privacy"
            >我已阅读并同意<span class="a-link">服务协议</span>和<span class="a-link"
              >隐私保护协议</span
            ></span
          >
        </el-checkbox>
        <el-checkbox
          v-if="viewType === 1"
          v-model="formData.agree"
          size="large"
          :checked="formData.agree"
        >
          <span class="agree-privacy"
            >我已阅读并同意<span class="a-link">服务协议</span>和<span class="a-link"
              >隐私保护协议</span
            ></span
          >
        </el-checkbox>
        <div v-if="viewType === 0" class="bottom-link">
          <span class="a-link" @click="changeType(1)">没有账号？</span>
          <span class="a-link" @click="changeType(2)">忘记密码</span>
        </div>
        <div v-else class="bottom-link">
          <span class="a-link" @click="changeType(0)">已有账号？</span>
        </div>
      </el-form>
    </div>
  </div>
  <win-op
    :show-set-top="false"
    :show-min="false"
    :close-type="0"
    :show-max="false"
    :show-close="!isMac"
  ></win-op>
</template>

<style scoped lang="scss">
// 加载相关
.loading-panel {
  height: calc(100vh - 32px);
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;

  img {
    width: 300px;
  }
}

//登录相关
.login-panel {
  background: #fff;
  border-radius: 3px;
  border: 1px solid #ddd;

  // 标题
  .title {
    height: 30px;
    padding: 5px 0 0 10px;
    text-align: center;
  }

  // 登录表单
  .login-form {
    margin-top: 30px;
    padding: 0 15px 29px 15px;

    // 重写 element-ui 的 input
    :deep(.el-input__wrapper) {
      box-shadow: none;
      border-radius: unset;
    }

    // 按钮禁用样式
    .is-disabled {
      background: rgba(7, 193, 96, 0.59) !important;
    }

    .el-form-item {
      border-bottom: 1px solid #ddd;
    }

    // 邮箱输入框
    .email-panel {
      align-items: center;
      width: 100%;
      display: flex;

      .input {
        flex: 1;
      }

      .icon-down {
        margin-left: 3px;
        width: 16px;
        cursor: pointer;
        border: none;
      }
    }

    // 错误消息
    .error-msg {
      line-height: 30px;
      height: 30px;
      color: #fb7373;
    }

    // 验证码校验框
    .check-code-panel {
      display: flex;

      .check-code {
        cursor: pointer;
        width: 120px;
        margin-left: 5px;
      }
    }

    // 登录按钮
    .login-btn {
      margin-top: 20px;
      width: 100%;
      background: #07c160;
      height: 36px;
      font-size: 16px;
    }

    // 底部忘记密码链接
    .bottom-link {
      text-align: right;
    }

    // 协议
    .agree-privacy {
      color: #a7a7a9;
    }
  }
}

// 邮箱选择框
.email-select {
  width: 250px;
}
</style>
