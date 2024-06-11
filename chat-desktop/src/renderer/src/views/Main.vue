<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'

const userStore = useUserStore()
const router = useRouter()
const { proxy } = getCurrentInstance()

const menuList = ref([
  {
    name: 'chat',
    icon: 'icon-chat',
    path: '/chat',
    countKey: 'chatCount',
    position: 'top'
  },
  {
    name: 'contact',
    icon: 'icon-user',
    path: '/contact',
    countKey: 'contactApplyCount',
    position: 'top'
  },
  {
    name: 'setting',
    icon: 'icon-more2',
    path: '/setting',
    position: 'bottom'
  }
])

const currentMenu = ref(menuList.value[0])
/**
 * 改变菜单
 * @param menu
 */
const changeMenu = (menu) => {
  currentMenu.value = menu
  router.push(menu.path)
}

const getLoginInfo = async () => {
  let result = await proxy.$request({
    url: proxy.$api.userInfo.getUserInfo
  })
  if (!result || !result.isSuccess) {
    return
  }
  userStore.setUserInfo(result.data)
}
onMounted(() => {
  getLoginInfo()
})
</script>

<template>
  <div class="main">
    <div class="left-sidebar">
      <div class=""></div>
      <div class="menu-list">
        <template v-for="(menu, index) in menuList" :key="index">
          <div
            v-if="menu.position === 'top'"
            :class="[
              'tab-item iconfont',
              menu.icon,
              currentMenu.name === menu.name ? 'active' : ''
            ]"
            @click="changeMenu(menu)"
          >
            <template v-if="menu.name === 'chat'"></template>
          </div>
        </template>
      </div>
      <div class="menu-list menu-bottom">
        <template v-for="(menu, index) in menuList" :key="index">
          <div
            v-if="menu.position === 'bottom'"
            :class="[
              'tab-item iconfont',
              menu.icon,
              currentMenu.name === menu.name ? 'active' : ''
            ]"
            @click="changeMenu(menu)"
          ></div>
        </template>
      </div>
    </div>
    <div class="right-container">
      <!-- router-view 自动为你提供了当前匹配的组件，并通过作用域插槽传递出来 -->
      <router-view v-slot="{ Component }">
        <!-- 缓存包裹的动态组件，chat 组件会被缓存，其他组件在被移除时不会被缓存 -->
        <!-- 指定要渲染的组件，这里 Component 是从 router-view 传递过来的当前匹配的路由组件。-->
        <keep-alive include="chat">
          <component :is="Component" ref="componentRef"></component>
        </keep-alive>
      </router-view>
    </div>
  </div>
</template>

<style scoped lang="scss">
.main {
  background: #ddd;
  display: flex;
  border-radius: 0 3px 3px 0;
  overflow: hidden;

  //左侧边栏
  .left-sidebar {
    width: 67px;
    background: #2e2e2e;
    text-align: center;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 35px;
    border: 1px solid #2e2e2e;
    border-right: none;
    padding-bottom: 10px;

    .menu-list {
      width: 100%;
      flex: 1;

      .tab-item {
        color: #d3d3d3;
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 10px;
        cursor: pointer;
        font-size: 22px;
        position: relative;
      }

      .active {
        color: #07c160;
      }
    }

    .menu-bottom {
      display: flex;
      flex-direction: column;
      justify-content: flex-end;
    }
  }

  // 右边区域
  .right-container {
    flex: 1;
    overflow: hidden;
    border: 1px solid #ddd;
    border-left: none;
  }
}

.popover-user-panel {
  padding: 10px;

  .popover-user {
    display: flex;
    border-bottom: 1px solid #ddd;
    padding-bottom: 20px;
  }

  .send-message {
    margin-top: 10px;
    text-align: center;
    padding: 20px 0 0 0;
  }
}
</style>
