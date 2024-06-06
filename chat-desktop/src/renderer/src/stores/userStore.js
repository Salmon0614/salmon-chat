import { defineStore } from 'pinia'

// 用户信息状态存储
export const useUserStore = defineStore('userInfo', {
  state: () => {
    return {
      userInfo: {}
    }
  },
  actions: {
    setUserInfo(userInfo) {
      this.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    getUserInfo() {
      return this.userInfo
    }
  }
})
