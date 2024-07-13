<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useContactStateStore } from '@/stores/contactStateStore'

const { proxy } = getCurrentInstance()
const contactStateStore = useContactStateStore()

let current = 1 // 当前页
let pageSize = 10 // 每页数量
let pageTotal = 1 // 总分页数
const applyList = ref([])
/**
 * 加载申请数据
 * @returns {Promise<void>}
 */
const loadApply = async () => {
  if (current > pageTotal) {
    return
  }
  let result = await proxy.$request({
    url: proxy.$api.contact.loadApply,
    params: {
      current: current,
      pageSize: pageSize
    }
  })
  if (!result || !result.isSuccess) {
    return
  }
  pageTotal = result.data.pages
  if (pageTotal === 1) {
    applyList.value = []
  }
  applyList.value = applyList.value.concat(result.data.records)
  current = result.data.current
  current++
}
/**
 * 初始加载
 */
loadApply()

/**
 * 处理好友申请
 * @param applyId 申请ID
 * @param contactType 好友类型
 * @param status 状态
 */
const dealWithApply = (applyId, contactType, status) => {
  proxy.$confirm({
    message: '您确定要执行该操作吗？',
    okfun: async () => {
      let result = await proxy.$request({
        url: proxy.$api.contact.dealWithApply,
        params: {
          applyId,
          status
        }
      })
      if (!result || !result.isSuccess) {
        return
      }
      proxy.$message.success('操作成功')
      current = 1
      await loadApply()
      // 如果是好友类型的申请，同意的话，重新加载好友列表
      if (contactType === 0 && status === 1) {
        contactStateStore.setContactReload(0)
      } else if (contactType === 1 && status === 1) {
        contactStateStore.setContactReload(2)
      }
    }
  })
}

// todo 监听新的朋友数量改变 socket....
</script>

<template>
  <ContentPanel
    :show-top-border="true"
    :infinite-scroll-immediate="false"
    v-infinite-scroll="loadApply"
  >
    <div>
      <div class="apply-item" v-for="(item, index) in applyList" :key="index">
        <div :class="['contact-type', item.contactType === 0 ? 'user-contact' : '']">
          {{ item.contactType === 0 ? '好友' : '群聊' }}
        </div>
        <Avatar
          :width="60"
          :height="60"
          :user-id="item.applyUserId"
          :image-src="item.avatar"
        ></Avatar>
        <div class="contact-info">
          <div class="nick-name">{{ item.contactName }}</div>
          <div class="origin">来源类型：{{ item.originTypeDesc }}</div>
          <div class="apply-info">{{ item.applyInfo }}</div>
        </div>
        <div class="op-btn">
          <div v-if="item.status === 0">
            <el-dropdown placement="bottom-end" trigger="click">
              <span class="el-dropdown-link">
                <el-button type="primary" size="small">处理</el-button>
              </span>
              <template #dropdown>
                <el-dropdown-item @click="dealWithApply(item.id, item.contactType, 1)">
                  同意
                </el-dropdown-item>
                <el-dropdown-item @click="dealWithApply(item.id, item.contactType, 2)">
                  拒绝
                </el-dropdown-item>
                <el-dropdown-item @click="dealWithApply(item.id, item.contactType, 3)">
                  拉黑
                </el-dropdown-item>
              </template>
            </el-dropdown>
          </div>
          <div v-else class="result-name">{{ item.statusDesc }}</div>
        </div>
      </div>
    </div>
    <div v-if="applyList.length === 0" class="no-data" style="margin-top: 10px">暂无申请记录</div>
  </ContentPanel>
</template>

<style scoped lang="scss">
.apply-item {
  display: flex;
  align-items: center;
  border-bottom: 1px solid #ddd;
  padding: 10px 0;

  .contact-type {
    display: flex;
    justify-content: center;
    writing-mode: vertical-rl; // 控制文本的书写模式和方向。具体来说，这个属性设置了文本的书写方向为垂直从右到左（right-to-left）
    vertical-align: middle;
    background: #2cb6fe;
    color: #fff;
    border-radius: 5px 0 0 5px;
    height: 60px;
  }

  .user-contact {
    background: #08bf61;
  }

  .contact-info {
    width: 260px;
    margin-left: 10px;

    .nick-name {
      color: #000000;
    }

    .origin {
      margin-top: 5px;
      color: #999999;
      font-size: 12px;
    }

    .apply-info {
      color: #999999;
      font-size: 12px;
      margin-top: 5px;
      overflow: hidden;
      text-overflow: ellipsis; // 设置当文本溢出包含元素时的显示方式，此处为显示省略号（…）
      white-space: nowrap;
    }
  }

  .op-btn {
    width: 50px;
    text-align: center;

    .result-name {
      color: #999999;
      font-size: 12px;
    }
  }
}
</style>
