<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from 'vue'

const { proxy } = getCurrentInstance()
const formData = ref({})
const formDataRef = ref()
const copying = ref(false)

const rules = {
  password: [{ required: true, message: '请输入新密码' }]
}

// todo 获取文件缓存路径
/**
 * 更改文件夹
 */
const changeFolder = () => {}

/**
 * 打开文件夹
 */
const openLocalFolder = () => {}
</script>

<template>
  <ContentPanel v-loading="copying" element-loading-text="正在迁移文件...">
    <el-form
      label-position="top"
      ref="formDataRef"
      :model="formData"
      label-width="80px"
      :rules="rules"
      @submit.prevent
    >
      <el-form-item label="文件管理" prop="password" class="file-manage">
        <div class="file-input" :title="formData.sysSetting">{{ formData.sysSetting }}</div>
        <div class="tips">文件的默认保存位置</div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="changeFolder">更改</el-button>
        <el-button type="primary" @click="openLocalFolder">打开文件夹</el-button>
      </el-form-item>
    </el-form>
  </ContentPanel>
</template>

<style scoped lang="scss">
.file-manage {
  :deep(.el-form-item__content) {
    display: block;
  }

  .file-input {
    background: #fff;
    padding: 0 5px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    font-size: 16px;
  }

  .tips {
    color: #888888;
    font-size: 13px;
  }
}
</style>
