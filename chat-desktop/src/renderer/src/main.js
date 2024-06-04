import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import router from '@/router'

// 样式
import 'element-plus/dist/index.css'
import '@/assets/cust-elementplus.scss'
import '@/assets/base.scss'
import '@/assets/icon/iconfont.css'

// 常用工具
import utils from "@/utils/utils";
import verify from "@/utils/verify";
import request from "@/utils/request";
import message from "@/utils/message";
import api from "@/utils/api";

const app = createApp(App)

app.use(router)
app.use(ElementPlus)

app.config.globalProperties.utils = utils;
app.config.globalProperties.verify = verify;
app.config.globalProperties.request = request;
app.config.globalProperties.message = message;
app.config.globalProperties.api = api;
app.mount('#app')
