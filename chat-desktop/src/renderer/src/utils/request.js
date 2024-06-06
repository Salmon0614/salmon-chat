import axios from 'axios'
import { ElLoading } from 'element-plus'
import Message from '@/utils/message'
import Api from '@/utils/api'

const contentTypeForm = 'application/x-www-form-urlencoded;charset=UTF-8'
const contentTypeJson = 'application/json'
const responseTypeJson = 'json'
// 加载实例
let loading = null

const instance = axios.create({
  withCredentials: true,
  baseURL: (import.meta.env.PROD ? Api.domain.prod : '') + '/api',
  timeout: 10 * 1000
})

//请求前拦截器
instance.interceptors.request.use(
  (config) => {
    if (config.showLoading) {
      loading = ElLoading.service({
        lock: true,
        text: '加载中......',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    }
    return config
  },
  (error) => {
    if (config.showLoading && loading) {
      loading.close()
    }
    Message.error('请求发送失败')
    return Promise.reject('请求发送失败')
  }
)

//请求后拦截器
instance.interceptors.response.use(
  (response) => {
    const { showLoading, errorCallback, showError = true, responseType } = response.config
    if (showLoading && loading) {
      loading.close()
    }
    const responseData = response.data
    // 如果数据流，直接返回
    if (responseType === 'arraybuffer' || responseType === 'blob') {
      return responseData
    }
    //正常请求
    if (responseData.code === 20000) {
      return responseData
    } else if (responseData.code === 40100) {
      //登录超时
      setTimeout(() => {
        window.ipcRenderer.send('reLogin')
      }, 2000)
      return Promise.reject({ showError: true, msg: '登录超时' })
    } else {
      //其他错误
      if (errorCallback) {
        errorCallback(responseData)
      }
      return Promise.reject({ showError: showError, msg: responseData.message })
    }
  },
  (error) => {
    if (error.config.showLoading && loading) {
      loading.close()
    }
    return Promise.reject({ showError: true, msg: '网络异常' })
  }
)

const request = (config) => {
  const {
    url,
    params,
    dataType,
    showLoading = true,
    responseType = responseTypeJson,
    showError = true,
    headersConfig = {}
  } = config
  let contentType = contentTypeForm
  let formData = new FormData() // 创建form对象
  for (let key in params) {
    formData.append(key, params[key] === undefined ? '' : params[key])
  }
  if (dataType != null && dataType === 'json') {
    contentType = contentTypeJson
  }
  const token = localStorage.getItem('token')
  let headers = {
    'Content-Type': contentType,
    'X-Requested-With': 'XMLHttpRequest',
    token: token,
    ...headersConfig
  }
  return instance
    .post(url, formData, {
      headers: headers,
      showLoading: showLoading,
      errorCallback: config.errorCallback,
      showError: showError,
      responseType: responseType
    })
    .catch((error) => {
      if (error.showError) {
        Message.error(error.msg)
      }
      return null
    })
}

export default request
