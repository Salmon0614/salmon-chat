<script setup>
import AvatarBase from '@/components/AvatarBase.vue'
import { ref, reactive, getCurrentInstance, nextTick, onMounted, computed } from 'vue'

const { proxy } = getCurrentInstance()

const props = defineProps({
  userInfo: {
    type: Object,
    // eslint-disable-next-line vue/require-valid-default-prop
    default: {}
  },
  showArea: {
    type: Boolean,
    default: false
  }
})
</script>

<template>
  <div class="user-panel">
    <AvatarBase
      :user-id="userInfo.id || userInfo.contactId"
      :width="60"
      :height="60"
      :border-radius="5"
      :show-detail="true"
    ></AvatarBase>
    <div class="user-info">
      <div class="nick-name">
        {{ userInfo.name || userInfo.nickname }}
        <span v-if="userInfo.gender === 0" class="iconfont icon-woman"></span>
        <span v-if="userInfo.gender === 1" class="iconfont icon-man"></span>
      </div>
      <div class="info">账号：{{ userInfo.account || userInfo.contactAccount }}</div>
      <div v-if="showArea" class="info">地区：{{ proxy.$utils.getAreaInfo(userInfo.area) }}</div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.user-panel {
  display: flex;
  padding-bottom: 20px;

  .user-info {
    flex: 1;
    margin-left: 10px;

    .nick-name {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      color: #000000;
      font-size: 16px;

      .iconfont {
        font-size: 13px;
      }

      .icon-man {
        color: #2cb6fe;
      }

      .icon-woman {
        color: #fb7373;
      }
    }

    .info {
      font-size: 12px;
      color: #939393;
      margin-top: 3px;
    }
  }
}
</style>
