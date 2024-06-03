<script setup>
import {ref, reactive, getCurrentInstance, nextTick} from 'vue'

const {proxy} = getCurrentInstance()

const formData = ref({});
const formDataRef = ref();
const rules = {
  title: [{required: true, message: "请输入内容"}],
}
</script>

<template>
  <div class="login-panel">
    <div class="title drag">SalmonChat</div>
    <div class="login-form">
      <div class="error-msg"></div>
      <el-form :model="formData" :rules="rules" ref="formDataRef"
               label-width="0px" @submit.prevent>
        <el-form-item prop="email">
          <el-input size="large" clearable placeholder="请输入邮箱" v-model.trim="formData.email">
            <template #prefix>
              <span class="iconfont icon-email"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="large" clearable show-password placeholder="请输入密码" v-model.trim="formData.password">
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="checkCode">
          <el-input size="large" clearable show-password placeholder="请输入验证码" v-model.trim="formData.checkCode">
            <template #prefix>
              <span class="iconfont icon-checkcode"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="login-btn" type="primary">登录</el-button>
        </el-form-item>
        <div class="bottom-link">
          <span class="a-link">没有账号？</span>
          <span class="a-link">忘记密码</span>
        </div>
      </el-form>
    </div>
  </div>
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
    padding: 0 15px 29px 15px;

    // 重写 element-ui 的 input
    :deep(.el-input__wrapper) {
      box-shadow: none;
      border-radius: unset;
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
  }
}

// 邮箱选择框
.email-select {
  width: 250px;
}

</style>
