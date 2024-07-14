<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted, computed } from 'vue'
import AvatarUpload from '@/components/AvatarUpload.vue'
import AreaSelect from '../../components/AreaSelect.vue'
import { useUserStore } from '@/stores/userStore'

const { proxy } = getCurrentInstance()
const userStore = useUserStore()
const props = defineProps({
  data: {
    type: Object
  }
})

const formData = computed(() => {
  // const userInfo = Object.assign({}, props.data)
  const userInfo = props.data

  // todo 头像处理

  userInfo.areaObj = {
    area: userInfo.area ? userInfo.area.split(',') : [],
    areaCode: userInfo.areaCode ? userInfo.areaCode.split(',') : []
  }
  console.log(userInfo)
  return userInfo
})

const formDataRef = ref()

const rules = {
  avatar: [{ required: true, message: '请选择头像' }],
  nickname: [{ required: true, message: '请输入昵称' }]
}
const emit = defineEmits(['editBack'])

/**
 * 保存个人信息
 */
const saveCover = ({ avatarFile, coverFile }) => {
  formData.value.avatarFile = avatarFile
  formData.value.coverFile = coverFile
}

/**
 * 保存个人信息
 */
const saveUserInfo = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return
    }
    let params = {}
    Object.assign(params, formData.value)
    // 有选择地区过，则进行修改
    params.area = null
    params.areaCode = null
    if (params.areaObj) {
      params.area = params.areaObj.area.join(',')
      params.areaCode = params.areaObj.areaCode.join(',')
      delete params.areaObj
    }
    // todo 强制刷新头像

    let result = await proxy.$request({
      url: proxy.$api.userInfo.saveUserInfo,
      params: params
    })
    if (!result || !result.isSuccess) {
      return
    }
    proxy.$message.success('保存成功')
    userStore.setUserInfo(result.data)
    // todo 强制刷新头像
    emit('editBack')
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
      <el-form-item label="头像" prop="avatar">
        <AvatarUpload v-model="formData.avatar" @uploadImage="saveCover"></AvatarUpload>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input
          maxlength="20"
          clearable
          placeholder="请输入昵称"
          v-model.trim="formData.nickname"
        ></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="formData.gender">
          <el-radio :value="1">男</el-radio>
          <el-radio :value="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="朋友权限" prop="joinType">
        <el-switch v-model="formData.joinType" :active-value="1" :inactive-value="0" />
        <div class="info">加我为好友时需要验证</div>
      </el-form-item>
      <el-form-item label="地区" prop="areaObj">
        <area-select v-model="formData.areaObj"></area-select>
      </el-form-item>
      <el-form-item label="个性签名" prop="description">
        <el-input
          v-model="formData.description"
          maxlength="200"
          clearable
          placeholder="请输入个性签名"
          type="textarea"
          rows="5"
          :show-word-limit="true"
          resize="none"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveUserInfo">保存个人信息</el-button>
        <el-button link @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss">
.info {
  margin-left: 5px;
  color: #949494;
  font-size: 12px;
}
</style>
