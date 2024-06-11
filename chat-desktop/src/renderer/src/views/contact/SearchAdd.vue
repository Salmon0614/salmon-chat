<script setup>
import { ref, getCurrentInstance, nextTick } from 'vue'
import { useUserStore } from '@/stores/userStore'

const userStore = useUserStore()

const { proxy } = getCurrentInstance()

const formData = ref({})
const formDataRef = ref({})
const rules = {}

const dialogConfig = ref({
  show: false,
  title: '提交申请',
  buttons: [
    {
      type: 'primary',
      text: '确定',
      click: () => {
        submitApply()
      }
    }
  ]
})

const emit = defineEmits(['reload'])
const show = (data) => {
  dialogConfig.value.show = true
  nextTick(() => {
    formDataRef.value.resetFields()
    formData.value = Object.assign({}, data)
    formData.value.applyInfo = '我是' + userStore.getUserInfo().nickname
  })
}

defineExpose({
  show
})

const submitApply = async () => {
  console.log(formData)
  const postData = {
    contactAccount: formData.value.account,
    applyInfo: formData.value.applyInfo,
    contactType: formData.value.contactType
  }
  let result = await proxy.$request({
    url: proxy.$api.contact.applyAdd,
    params: postData
  })
  if (!result || !result.isSuccess) {
    return
  }
  if (result.data.joinType === 0) {
    proxy.$message.success('添加成功')
  } else {
    proxy.$message.success('申请成功，等待对方同意')
  }
  dialogConfig.value.show = false
  emit('reload')
}
</script>
<template>
  <div>
    <MyDialog
      :show="dialogConfig.show"
      :title="dialogConfig.title"
      :buttons="dialogConfig.buttons"
      width="400px"
      :show-cancel="false"
      @close="dialogConfig.show = false"
    >
      <el-form ref="formDataRef" :model="formData" :rules="rules" @submit.prevent>
        <el-form-item prop="applyInfo">
          <el-input
            v-model.trim="formData.applyInfo"
            type="textarea"
            :rows="5"
            placeholder="输入申请信息，更容易被通过"
            clearable
            resize="none"
            maxlength="100"
          ></el-input>
        </el-form-item>
      </el-form>
    </MyDialog>
  </div>
</template>

<style scoped lang="scss"></style>
