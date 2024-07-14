<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from 'vue'
import config from '../../../../../package.json'
const { proxy } = getCurrentInstance()
const formData = ref({})
const formDataRef = ref()

const rules = {
  password: [{ required: true, message: '请输入新密码' }]
}

// console.log(config.version)

/**
 * todo 检查更新
 */
const checkUpdate = () => {}
</script>

<template>
  <ContentPanel>
    <el-form ref="formDataRef" :model="formData" :rules="rules" @submit.prevent>
      <el-form-item label="版本信息">
        <div class="version-info">
          <div>SalmonChat {{ config.version }}</div>
          <div>
            <el-button type="primary" @click="checkUpdate">检查更新</el-button>
          </div>
        </div>
      </el-form-item>
    </el-form>
  </ContentPanel>
</template>

<style scoped lang="scss"></style>
