<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from 'vue'
import AreaData from '@/utils/areaData.js'

const props = defineProps({
  modelValue: {
    type: Object,
    default: {}
  }
})

const emit = defineEmits(['update:modelValue'])
const areaSelectRef = ref({})

/**
 * 改变地区
 */
const changeArea = (e) => {
  const areaData = {
    area: [],
    areaCode: []
  }
  const checkedNodes = areaSelectRef.value.getCheckedNodes()[0]
  if (checkedNodes) {
    areaData.area = checkedNodes.pathLabels
    areaData.areaCode = checkedNodes.pathValues
  }
  emit('update:modelValue', areaData)
}
</script>

<template>
  <div>
    <el-cascader
      :options="AreaData"
      v-model="modelValue.areaCode"
      @change="changeArea"
      ref="areaSelectRef"
      clearable
    ></el-cascader>
  </div>
</template>

<style scoped lang="scss"></style>
