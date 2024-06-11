<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted, computed } from 'vue'

const { proxy } = getCurrentInstance()

const props = defineProps({
  imageSrc: {
    type: String
  },
  width: {
    type: Number,
    default: 170
  },
  height: {
    type: Number,
    default: 170
  },
  showPlay: {
    type: Boolean,
    default: false
  },
  fileId: {
    type: [String, Number]
  },
  partType: {
    type: String,
    default: 'avatar'
  },
  fileType: {
    type: Number,
    default: 0
  },
  forceGet: {
    type: Boolean,
    default: false
  }
})

const serverUrl = computed(() => {
  if (!props.fileId) {
    return
  }
  // todo 获取本地服务的图片URL
  return ''
})

const showImageHandler = () => {}
</script>

<template>
  <div class="image-panel" @click="showImageHandler">
    <el-image :src="imageSrc || serverUrl" fit="scale-down" :width="width"></el-image>
  </div>
</template>

<style scoped lang="scss">
.image-panel {
  position: relative;
  display: flex;
  overflow: hidden;
  cursor: pointer;
  max-width: 170px;
  max-height: 170px;
  background: #ddd;

  .icon-image-error {
    margin: 0 auto;
    font-size: 30px;
    color: #838383;
  }

  .play-panel {
    z-index: 2;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;

    .icon-video-play {
      font-size: 35px;
      color: #fff;
    }

    &:hover {
      opacity: 0.8;
    }
  }
}
</style>
