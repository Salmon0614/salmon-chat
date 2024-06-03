import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'

import 'element-plus/dist/index.css'
import '@/assets/cust-elementplus.scss'
import '@/assets/base.scss'
import '@/assets/icon/iconfont.css'

import router from '@/router'
import utils from "@/utils/utils";
import verify from "@/utils/verify";

const app = createApp(App)

app.use(router)
app.use(ElementPlus)

app.config.globalProperties.utils=utils;
app.config.globalProperties.verify=verify;
app.mount('#app')
