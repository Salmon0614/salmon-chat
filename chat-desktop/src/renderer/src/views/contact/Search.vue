<script setup>
import { computed, getCurrentInstance, nextTick, ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import ContentPanel from '@/components/ContentPanel.vue'
import SearchAdd from '@/views/contact/SearchAdd.vue'

const { proxy } = getCurrentInstance()
const userStore = useUserStore()
const contactTypeName = computed(() => {
  console.log(userStore.getUserInfo())
  console.log(searchResult.value)
  if (userStore.getUserInfo().account === searchResult.value.account) {
    return '自己'
  }
  return searchResult.value.contactTypeDesc
})
const isShow = ref(false)
const isClickSearch = ref(false)
const contactId = ref(null)
const searchResult = ref({})
const searchAddRef = ref()
const search = async () => {
  // SG73689195198
  if (contactId.value == null) {
    return
  }
  isClickSearch.value = true
  let result = await proxy.$request({
    url: proxy.$api.contact.search,
    params: {
      keyword: contactId.value
    }
  })
  if (!result || !result.isSuccess) {
    isShow.value = true
    return
  }
  console.log(result)
  if (proxy.$utils.isEmpty(result.data)) {
    isShow.value = true
    return
  }
  isShow.value = false
  searchResult.value = result.data
}
/**
 * 添加联系人
 */
const applyContact = () => {
  searchAddRef.value.show(searchResult.value)
}
const sendMessage = () => {}
const resetForm = () => {}
</script>

<template>
  <ContentPanel>
    <div class="search-form">
      <el-input
        v-model="contactId"
        clearable
        placeholder="请输入用户账号或者群聊号"
        size="large"
        @keydown.enter="search"
      ></el-input>
      <div class="search-btn iconfont icon-search" @click="search"></div>
    </div>
    <div v-if="searchResult && Object.keys(searchResult).length > 0" class="search-result-panel">
      <div class="search-result">
        <span class="contact-type">{{ contactTypeName }}</span>
        <UserBaseInfo
          :user-info="searchResult"
          :show-area="searchResult.contactType === 0"
        ></UserBaseInfo>
      </div>
      <div v-if="searchResult.account !== userStore.getUserInfo().account" class="op-btn">
        <el-button
          v-if="
            searchResult.status === null ||
            searchResult.status === 0 ||
            searchResult.status === 2 ||
            searchResult.status === 3 ||
            searchResult.status === 4
          "
          type="primary"
          @click="applyContact"
        >
          {{ searchResult.contactType === 0 ? '添加到联系人' : '申请加入到群组' }}
        </el-button>
        <el-button v-if="searchResult.status === 1" type="primary" @click="sendMessage"
          >发消息
        </el-button>
        <span v-if="searchResult.status === 5 || searchResult.status === 6" type="primary">
          对方拉黑了你
        </span>
      </div>
    </div>
    <div v-if="isClickSearch && isShow" class="no-data">没有搜索到任何结果</div>
  </ContentPanel>
  <SearchAdd ref="searchAddRef" @reload="resetForm"></SearchAdd>
</template>

<style scoped lang="scss">
.search-form {
  padding-top: 50px;
  display: flex;
  align-items: center;

  :deep(.el-input__wrapper) {
    border-radius: 4px 0 0 4px;
    border-right: none;
  }

  .search-btn {
    background: #07c160 !important;
    color: #fff;
    line-height: 40px;
    width: 80px;
    text-align: center;
    border-radius: 0 5px 5px 0;
    cursor: pointer;

    &:hover {
      background: #0dd36c;
    }
  }
}

.no-data {
  padding: 30px 0;
}

.search-result-panel {
  .search-result {
    padding: 30px 20px 20px 20px;
    background: #fff;
    border-radius: 5px;
    margin-top: 10px;
    position: relative;

    .contact-type {
      position: absolute;
      left: 0;
      top: 0;
      background: #2cb6fe;
      padding: 2px 5px;
      color: #fff;
      border-radius: 5px 0 0 0;
      font-size: 12px;
    }
  }

  .op-btn {
    border-radius: 5px;
    margin-top: 10px;
    padding: 10px;
    background: #fff;
    text-align: center;
  }
}
</style>
