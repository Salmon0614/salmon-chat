import { defineStore } from 'pinia'

// 用户信息状态存储
export const useContactStateStore = defineStore('contactStateInfo', {
  state: () => {
    return {
      contactReload: null
    }
  },
  actions: {
    setContactReload(state) {
      this.contactReload = state
    }
  }
})
