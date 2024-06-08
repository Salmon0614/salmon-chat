<script setup>
import Layout from '../../components/Layout.vue'
import { ref, reactive, getCurrentInstance, nextTick } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const { proxy } = getCurrentInstance()

const searchKey = ref(null)

let partList = ref([
  {
    partName: '新朋友',
    children: [
      {
        name: '搜好友',
        icon: 'icon-search',
        iconBgColor: '#fa9d3b',
        path: '/contact/search'
      },
      {
        name: '新的朋友',
        icon: 'icon-plane',
        iconBgColor: '#08bf61',
        path: '/contact/contactNotice',
        showTitle: true,
        countKey: 'contactApplyCount'
      }
    ]
  },
  {
    partName: '我的群聊',
    children: [
      {
        name: '新建群聊',
        icon: 'icon-add-group',
        iconBgColor: '#1485ee',
        path: '/contact/createGroup'
      }
    ],
    contactId: 'groupId',
    contactName: 'groupName',
    showTitle: true,
    contactData: [],
    contactPath: '/contact/groupDetail'
  },
  {
    partName: '我加入的群聊',
    contactId: 'contactId',
    contactName: 'contactName',
    showTitle: true,
    contactData: [],
    contactPath: '/contact/groupDetail',
    emptyMsg: '暂未加入群聊'
  },
  {
    partName: '我的好友',
    children: [],
    contactId: 'contactId',
    contactName: 'contactName',
    contactData: [],
    contactPath: '/contact/Detail',
    emptyMsg: '暂无好友'
  }
])

const search = () => {}
const partJump = (part) => {}
</script>

<template>
  <Layout>
    <template #left-content>
      <div class="drag-panel drag"></div>
      <div class="top-search">
        <el-input clearable placeholder="搜索" v-model="searchKey" size="small" @click="search">
          <template #prefix>
            <span class="iconfont icon-search"></span>
          </template>
        </el-input>
      </div>
      <div class="contact-list">
        <template v-for="(part, index) in partList" :key="index">
          <div class="part-title">{{ part.partName }}</div>
          <div class="part-list">
            <div
              v-for="(subPart, index1) in part.children"
              :key="index1"
              :class="['part-item', subPart.path === route.path ? 'active' : '']"
              @click="partJump(subPart)"
            >
              <div
                :class="['iconfont', subPart.icon]"
                :style="{ background: subPart.iconBgColor }"
              ></div>
              <div class="text">{{ subPart.name }}</div>
            </div>
            <template v-for="(contact, index2) in part.contactData" :key="index2"> </template>

            <template v-if="part.contactData && part.contactData.length === 0">
              <div class="no-data">{{ part.emptyMsg }}</div>
            </template>
          </div>
        </template>
      </div>
    </template>
    <template #right-content>
      <div class="title-panel drag"></div>
    </template>
  </Layout>
</template>

<style scoped lang="scss">
.drag-panel {
  height: 20px;
  background: #f7f7f7;
}

.top-search {
  padding: 0 10px 9px 10px;
  background: #f7f7f7;
  display: flex;
  align-items: center;

  // 重写 element-ui 的 input
  :deep(.el-input__wrapper) {
    //box-shadow: none;
    background-color: #ddd;
  }

  .iconfont {
    font-size: 12px;
  }
}

.contact-list {
  border-top: 1px solid #ddd;
  height: calc(100vh - 62px);
  overflow: hidden;

  &:hover {
    overflow: auto;
  }

  .part-title {
    color: #515151;
    padding-left: 10px;
    margin-top: 10px;
  }

  .part-list {
    border-bottom: 1px solid #d6d6d6;

    .part-item {
      display: flex;
      align-items: center;
      padding: 10px 10px;
      position: relative;

      &:hover {
        cursor: pointer;
        background: #d6d6d7;
      }

      .iconfont {
        width: 35px;
        height: 35px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 20px;
        color: #fff;
      }

      .text {
        flex: 1;
        color: #000000;
        margin-left: 10px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }

    .no-data {
      text-align: center;
      font-size: 12px;
      color: #9d9d9d;
      line-height: 30px;
    }

    .active {
      background: #c4c4c4;

      &:hover {
        background: #c4c4c4;
      }
    }
  }
}

.title-panel {
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  padding-left: 10px;
  font-size: 18px;
  color: #000000;
}
</style>
