<script setup>
const props = defineProps({
  showTopBorder: {
    type: Boolean,
    default: false
  }
})
</script>

<template>
  <div class="content-panel" :style="{ 'border-top': showTopBorder ? '1px solid #ddd' : 'none' }">
    <div class="content-inner">
      <slot />
    </div>
  </div>
</template>

<style scoped lang="scss">
.content-panel {
  overflow: auto;
  height: calc(100vh - 62px);

  .content-inner {
    margin: 0 auto;
    width: 400px;
  }
}
</style>
