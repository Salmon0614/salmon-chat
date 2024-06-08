<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from 'vue'

const props = defineProps({
  showSetTop: {
    type: Boolean,
    default: true
  },
  showMin: {
    type: Boolean,
    default: true
  },
  showMax: {
    type: Boolean,
    default: true
  },
  showClose: {
    type: Boolean,
    default: true
  },
  // 关闭类型：0-关闭 1-隐藏
  closeType: {
    type: Number,
    default: 1
  }
})
const emit = defineEmits(['closeCallback'])
const isTop = ref(false)
const isMax = ref(false)
onMounted(() => {
  isMax.value = false
  isTop.value = false
})
/**
 * 窗口操作
 * @param action
 * @param data
 */
const winOperate = (action, data) => {
  window.operateWindow.operateWindowTitle({ action, data })
}
/**
 * 窗口置顶
 */
const top = () => {
  isTop.value = !isTop.value
  winOperate('top', { top: isTop.value })
}
/**
 * 最小化
 */
const minimize = () => {
  winOperate('minimize')
}
/**
 * 最大化
 */
const maximize = () => {
  if (isMax.value) {
    winOperate('unMaximize')
    isMax.value = false
  } else {
    winOperate('maximize')
    isMax.value = true
  }
}
/**
 * 点击关闭窗口
 */
const close = () => {
  winOperate('close', { closeType: props.closeType })
  emit('closeCallback')
}
</script>

<template>
  <div class="win-op no-drag">
    <div
      v-if="showSetTop"
      :class="['iconfont icon-top', isTop ? 'win-top' : '']"
      @click="top"
      :title="isTop ? '取消置顶' : '置顶'"
    ></div>
    <div v-if="showMin" class="iconfont icon-min" @click="minimize" title="最小化"></div>
    <div
      v-if="showMax"
      :class="['iconfont', isMax ? 'icon-maximize' : 'icon-max']"
      @click="maximize"
      :title="isMax ? '向下还原' : '最大化'"
    ></div>
    <div v-if="showClose" class="iconfont icon-close" @click="close" title="关闭窗口"></div>
  </div>
</template>

<style scoped lang="scss">
.win-op {
  position: absolute;
  top: 0;
  right: 0;
  z-index: 1;
  overflow: hidden;
  border-radius: 0 3px 0 0;

  .iconfont {
    float: left;
    font-size: 12px;
    color: #101010;
    text-align: center;
    display: flex;
    justify-content: center;
    cursor: pointer;
    height: 25px;
    align-items: center;
    padding: 0 10px;

    &:hover {
      background: #ddd;
    }
  }

  .icon-close {
    background: #fb7373;
    color: #fff;
  }

  .win-top {
    background: #ddd;
    color: #07c160;
  }
}
</style>
