<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted, computed } from 'vue'

const { proxy } = getCurrentInstance()
const formData = ref({})
const formDataRef = ref()
const emit = defineEmits(['editBack'])
const validateRePassword = (rule, value, callback) => {
  if (value !== formData.value.password) {
    callback(new Error(rule.message))
  } else {
    callback()
  }
}
const rules = {
  password: [
    { required: true, message: '请输入新密码' },
    {
      validator: proxy.$verify.password,
      message: proxy.$verify.regErrMsg.passwordError
    }
  ],
  rePassword: [
    { required: true, message: '请再次输入新密码' },
    {
      validator: validateRePassword,
      message: proxy.$verify.regErrMsg.rePasswordError
    }
  ]
}

/**
 * 更新密码
 */
const updatePassword = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return
    }
    proxy.$confirm({
      message: '修改密码后需重新登录，确认修改吗？',
      okfun: async () => {
        let params = {}
        Object.assign(params, formData.value)
        let result = await proxy.$request({
          url: proxy.$api.userInfo.updatePassword,
          params: params
        })
        if (!result || !result.isSuccess) {
          return
        }
        proxy.$message.success('修改成功，请重新登录', () => {
          // todo 重新登录
        })
      }
    })
  })
}

/**
 * 取消
 */
const cancel = () => {
  emit('editBack')
}
</script>

<template>
  <div>
    <el-form ref="formDataRef" :model="formData" label-width="80px" :rules="rules" @submit.prevent>
      <el-form-item label="密码" prop="password">
        <el-input
          type="password"
          maxlength="20"
          clearable
          placeholder="请输入新密码"
          v-model.trim="formData.password"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="rePassword">
        <el-input
          type="password"
          maxlength="20"
          clearable
          placeholder="请再次输入新密码"
          v-model.trim="formData.rePassword"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updatePassword">修改密码</el-button>
        <el-button link @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss"></style>
