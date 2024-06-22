<script setup>
import GroupEditForm from '@/views/contact/GroupEditForm.vue'
import { ref, reactive, getCurrentInstance, nextTick, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const { proxy } = getCurrentInstance()
const route = useRoute()
const router = useRouter()

const dialogConfig = ref({
  show: false,
  title: '修改群聊',
  buttons: []
})

const groupEditRef = ref()
const show = (data) => {
  dialogConfig.value.show = true
  nextTick(() => {
    groupEditRef.value.show(data)
  })
}
defineExpose({
  show
})
const emit = defineEmits(['reloadGroupInfo'])
/**
 * 编辑回调
 */
const editBack = () => {
  dialogConfig.value.show = false
  emit('reloadGroupInfo')
}
</script>

<template>
  <my-dialog
    :show="dialogConfig.show"
    :buttons="dialogConfig.buttons"
    width="400px"
    :show-cancel="false"
    :title="dialogConfig.title"
    @close="dialogConfig.show = false"
  >
    <GroupEditForm ref="groupEditRef" @edit-back="editBack"></GroupEditForm>
  </my-dialog>
</template>

<style scoped lang="scss"></style>
