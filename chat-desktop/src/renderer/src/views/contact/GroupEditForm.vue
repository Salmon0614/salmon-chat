<script setup>
import { ref, getCurrentInstance, nextTick } from 'vue'
import { useContactStateStore } from '@/stores/contactStateStore'

const contactStateStore = useContactStateStore()
const { proxy } = getCurrentInstance()
const formData = ref({ joinType: 1 })
const formDataRef = ref({})
const rules = {
  groupName: [{ required: true, message: '请输入群名称' }],
  groupNotice: [{ required: true, message: '请输入群公告' }],
  joinType: [{ required: true, message: '请选择加入权限' }],
  groupCover: [{ required: false, message: '请上传群封面' }]
}
const emit = defineEmits(['editBack'])
const submit = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return
    }
    let params = {}
    // todo 重新加载头像
    Object.assign(params, formData.value)
    let result = await proxy.$request({
      url: proxy.$api.group.saveOrUpdateGroup,
      params: params,
      showLoading: false
    })
    if (!result || !result.isSuccess) {
      return
    }
    if (!proxy.$utils.isEmpty(params.id)) {
      proxy.$message.success('群聊修改成功')
      emit('editBack')
    } else {
      proxy.$message.success('创建群聊成功')
    }
    formDataRef.value.resetFields()
    // 重新加载我创建的群聊
    contactStateStore.setContactReload(2)
    // todo 重新加载头像
  })
}
/**
 * 保存封面
 */
const saveCover = () => {
  // todo 保存封面
}

const show = (data) => {
  formDataRef.value.resetFields()
  formData.value = Object.assign({}, data)
  console.log(data)
  // todo 群头像处理
  formData.value.avaterFile = data.groupId
}

defineExpose({
  show
})
</script>

<template>
  <el-form ref="formDataRef" :model="formData" :rules="rules" label-width="80px" @submit.prevent>
    <el-form-item label="群名称" prop="groupName">
      <el-input
        v-model.trim="formData.groupName"
        maxlength="20"
        clearable
        placeholder="请输入群名称"
      >
      </el-input>
    </el-form-item>
    <el-form-item label="群封面" prop="groupCover">
      <AvatarUpload
        ref="avatarUploadRef"
        v-model="formData.groupCover"
        @cover-file="saveCover"
      ></AvatarUpload>
    </el-form-item>
    <el-form-item label="加入权限" prop="joinType">
      <el-radio-group v-model="formData.joinType">
        <el-radio :value="1">管理员审核同意后加入</el-radio>
        <el-radio :value="0">直接加入</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="公告" prop="groupNotice">
      <el-input
        v-model.trim="formData.groupNotice"
        maxlength="300"
        clearable
        placeholder="请输入群公告"
        type="textarea"
        rows="5"
        :show-word-limit="true"
        resize="none"
      >
      </el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">
        {{ formData.id ? '修改群聊' : '创建群聊' }}
      </el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss"></style>
