<script setup>
import { ref, getCurrentInstance } from 'vue'

const { proxy } = getCurrentInstance()

const props = defineProps({
  userId: {
    type: Number
  },
  account: {
    type: String
  },
  imageSrc: {
    type: String
  },
  width: {
    type: Number,
    default: 40
  },
  height: {
    type: Number,
    default: 40
  },
  borderRadius: {
    type: Number,
    default: 0
  },
  showDetail: {
    type: Boolean,
    default: false
  }
})

const showDetailHandler = () => {
  console.log(props.imageSrc)
  if (!props.showDetail) {
    return
  }
  // todo 查看图片详情（原图）
  console.log(props.imageSrc)
}
</script>

<template>
  <div
    class="user-avatar"
    :style="{
      width: width + 'px',
      height: height + 'px',
      'border-radius': borderRadius + 'px'
    }"
    @click="showDetailHandler"
  >
    <ShowLocalImage
      :width="width"
      :height="height"
      :file-id="userId"
      part-type="avatar"
      :force-get="true"
      :image-src="imageSrc"
    ></ShowLocalImage>
  </div>
</template>

<style scoped lang="scss">
.user-avatar {
  background: #d3d3d3;
  display: flex;
  overflow: hidden;
  cursor: pointer;
  align-items: center;
  justify-content: center;
}
</style>
