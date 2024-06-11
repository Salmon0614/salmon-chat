import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import router from '@/router'
import * as Pinia from 'pinia'

// 样式
import 'element-plus/dist/index.css'
import '@/assets/cust-elementplus.scss'
import '@/assets/base.scss'
import '@/assets/icon/iconfont.css'

// 常用组件
import Layout from '@/components/Layout.vue'
import WinOp from '@/components/WinOp.vue'
import ContentPanel from '@/components/ContentPanel.vue'
import ShowLocalImage from '@/components/ShowLocalImage.vue'
import UserBaseInfo from '@/components/UserBaseInfo.vue'
import MyDialog from '@/components/MyDialog.vue'
import Avatar from '@/components/Avatar.vue'
import AvatarUpload from '@/components/AvatarUpload.vue'

// 常用工具
import utils from '@/utils/utils'
import verify from '@/utils/verify'
import request from '@/utils/request'
import message from '@/utils/message'
import api from '@/utils/api'
import confirm from '@/utils/confirm'

const app = createApp(App)

app.use(router)
app.use(ElementPlus)
app.use(Pinia.createPinia())
app.component('Layout', Layout)
app.component('WinOp', WinOp)
app.component('ContentPanel', ContentPanel)
app.component('ShowLocalImage', ShowLocalImage)
app.component('UserBaseInfo', UserBaseInfo)
app.component('MyDialog', MyDialog)
app.component('Avatar', Avatar)
app.component('AvatarUpload', AvatarUpload)
app.config.globalProperties.$utils = utils
app.config.globalProperties.$verify = verify
app.config.globalProperties.$request = request
app.config.globalProperties.$message = message
app.config.globalProperties.$api = api
app.config.globalProperties.$confirm = confirm
app.mount('#app')
