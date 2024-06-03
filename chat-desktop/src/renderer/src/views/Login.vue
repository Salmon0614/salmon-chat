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
  <div>
    <el-form :model="formData" :rules="rules" ref="formDataRef"
             label-width="80px" @submit.prevent>
      <el-form-item label="邮箱" prop="email">
        <el-input clearable placeholder="提示信息" v-model.trim="formData.email">

        </el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="less">

</style>
