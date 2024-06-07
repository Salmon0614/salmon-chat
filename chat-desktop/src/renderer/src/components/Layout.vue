<script setup></script>

<template>
  <div class="layout-container">
    <div class="left-sidebar-inner">
      <slot name="left-content"></slot>
    </div>
    <div class="right-content">
      <slot name="right-content"></slot>
    </div>
  </div>
</template>

<style scoped lang="scss">
.layout-container {
  display: flex;
  // 左侧边栏
  .left-sidebar-inner {
    width: 250px;
    background: #e6e5e5;
    border-color: #ddd;
    border-style: solid;
    border-width: 0 1px 0 0;
  }

  // 右侧区域
  .right-content {
    flex: 1;
    height: calc(100vh - 2px);
    background: #f5f5f5;
    width: 100%;
  }
}
</style>
