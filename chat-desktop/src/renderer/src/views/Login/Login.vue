<script setup>
import {ref, reactive, getCurrentInstance, nextTick} from 'vue'

const {proxy} = getCurrentInstance()

const formData = ref({});
const formDataRef = ref();

// 邮箱校验
const validate_email = (rule, value, callback) => {
  const emailRegExp = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
  const emailRegExp1 = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
  if ((!emailRegExp.test(value) && value !== '') || (!emailRegExp1.test(value) && value !== '')) {
    callback(new Error('请输入有效邮箱格式！'));
  } else {
    callback();
  }
};

const rules = {
  // 邮箱
  email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'},
    {validator: validate_email, trigger: ['blur']}
  ],
  // 密码
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {pattern: /^\S{6,15}$/, message: '密码必须是 6-15位 的非空字符', trigger: 'blur'}
  ],
  // 再次输入密码
  rePassword: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {pattern: /^\S{6,15}$/, message: '密码必须是 6-15位 的非空字符', trigger: 'blur'},
    {
      validator: (rule, value, callback) => {
        // 判断 value 和 当前 form 中收集的 password 是否一致
        if (value !== formData.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback() // 校验成功
        }
      },
      trigger: 'blur'
    }
  ],
  // 验证码
  checkCode: [
    {required: true, message: '请输入验证码', trigger: 'blur'},
    // {len: 4, message: '验证码长度为4个字符', trigger: 'blur'}
  ]
}

// 0-登录页 1-注册页 2-忘记密码
const viewType = ref(0);
/**
 * 改变页面类型、和调整窗口大小
 * @param type
 */
const changeType = (type) => {
  // 发生渲染进程通信
  window.changeType.send(type);
  cleanVerify();
  nextTick(() => {
    formDataRef.value.resetFields();
    const oldType = viewType.value;
    // 注册页返回登录页情况
    if (type === 0 && oldType === 1) {
      formData.value = {email: null, agree: false};
    } else if (oldType !== 1) {
      // 修改密码与登录界面来回切换，不置空邮箱
      const email = formData.value.email;
      formData.value = {email: email, agree: false};
    } else {
      // 其它页面切换表单全部恢复初值
      formData.value = {email: null, agree: false};
    }
  })
  viewType.value = type;
}
/**
 * 登录
 */
const login = () => {

}
/**
 * 注册
 */
const register = () => {

}
/**
 * 修改密码
 */
const updatePassword = () => {

}

const errorMsg = ref(null);

/**
 * 提交表单
 */
const submit = () => {
  // 用element-ui表单校验
  // formDataRef.value.validate(async (valid) => {
  //   if (!valid) {
  //     return;
  //   }
  //   if (viewType.value === 0) {
  //     login();
  //   } else if (viewType.value === 1) {
  //     register();
  //   } else {
  //     updatePassword();
  //   }
  // })
  // 使用自定义校验
  cleanVerify();
  if (!proxy.verify.validateEmail(formData.value.email)) {
    errorMsg.value = "请输入有效邮箱格式！";
    return;
  }
  if (!proxy.verify.validatePassword(formData.value.password)) {
    errorMsg.value = "密码至少8个字符，包含大小写字母和数字！";
    return;
  }
  if (viewType.value !== 0 && formData.value.password !== formData.value.rePassword) {
    errorMsg.value = "两次输入密码不一致！";
    return;
  }
  if (viewType.value === 0) {
    login();
  } else if (viewType.value === 1) {
    register();
  } else {
    updatePassword();
  }
}

// 是否禁用提交按钮
const isDisabled = () => {
  if (proxy.utils.isEmpty(formData.value.email) || proxy.utils.isEmpty(formData.value.password) || proxy.utils.isEmpty(formData.value.checkCode)) {
    return true;
  }
  if (!formData.value.agree) {
    return true;
  }
  if (viewType.value === 1 && (proxy.utils.isEmpty(formData.value.rePassword) || proxy.utils.isEmpty(formData.value.nickname))) {
    return true;
  }
  return viewType.value === 2 && proxy.utils.isEmpty(formData.value.rePassword);
};

// 清空校验信息
const cleanVerify = () => {
  errorMsg.value = null;
}


</script>

<template>
  <div class="login-panel">
    <div class="title drag">SalmonChat</div>
    <div class="login-form">
      <el-form :model="formData" ref="formDataRef"
               label-width="0px" @submit.prevent>
        <el-form-item prop="email">
          <el-input size="large" clearable placeholder="请输入邮箱" v-model.trim="formData.email" @focus="cleanVerify">
            <template #prefix>
              <span class="iconfont icon-email"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="nickname" v-if="viewType===1">
          <el-input size="large" clearable placeholder="请输入昵称" maxlength="20" v-model.trim="formData.nickname"
                    @focus="cleanVerify">
            <template #prefix>
              <span class="iconfont icon-user-nick"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="large" clearable show-password placeholder="请输入密码" v-model.trim="formData.password"
                    @focus="cleanVerify">
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="rePassword" v-if="viewType===1||viewType===2">
          <el-input size="large" clearable show-password placeholder="请再次输入密码"
                    v-model.trim="formData.rePassword" @focus="cleanVerify">
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="checkCode">
          <el-input size="large" clearable placeholder="请输入验证码" v-model.trim="formData.checkCode"
                    @focus="cleanVerify">
            <template #prefix>
              <span class="iconfont icon-checkcode"></span>
            </template>
          </el-input>
        </el-form-item>
        <div class="error-msg">{{ errorMsg }}</div>
        <el-form-item>
          <el-button class="login-btn" :disabled="isDisabled()" type="primary" v-if="viewType===0" @click="submit">登录
          </el-button>
          <el-button class="login-btn" :disabled="isDisabled()" type="primary" v-if="viewType===1" @click="submit">
            注册
          </el-button>
          <el-button class="login-btn" :disabled="isDisabled()" type="primary" v-if="viewType===2" @click="submit">
            修改密码
          </el-button>
        </el-form-item>
        <el-checkbox v-if="viewType===0" size="large" :checked="formData.agree" v-model="formData.agree">
          <span class="agree-privacy">我已阅读并同意<span class="a-link">服务协议</span>和<span
            class="a-link">隐私保护协议</span></span>
        </el-checkbox>
        <el-checkbox v-if="viewType===1" size="large" :checked="formData.agree" v-model="formData.agree">
          <span class="agree-privacy">我已阅读并同意<span class="a-link">服务协议</span>和<span
            class="a-link">隐私保护协议</span></span>
        </el-checkbox>
        <div class="bottom-link" v-if="viewType===0">
          <span class="a-link" @click="changeType(1)">没有账号？</span>
          <span class="a-link" @click="changeType(2)">忘记密码</span>
        </div>
        <div class="bottom-link" v-else>
          <span class="a-link" @click="changeType(0)">已有账号？</span>
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
