import { ipcMain } from 'electron'
import store from './store'
import { initWs } from './wsClient'

/**
 * 监听登录、注册、忘记密码窗口变化
 * @param callback
 */
const onLoginOrRegisterOrForget = (callback) => {
  // 主进程监听渲染进程发送的消息（渲染进程触发该信号，主进程接收信号，实现渲染进程到主进程的通信）
  ipcMain.on('loginOrRegisterOrForget', (event, viewType) => {
    callback(event, viewType)
  })
}

/**
 * 监听登录成功后到聊天窗口主体变化
 * @param callback
 */
const onLogin = (callback) => {
  ipcMain.on('openChatMain', (event, config) => {
    store.initUserId(config.userId)
    store.setUserData('token', config.token)
    callback(event, config)
    // 初始化ws连接
    initWs(config, event.sender)
    // todo 增加用户配置，本地存储的时候（本地数据库）
  })
}

/**
 * 监听注销登录聊天窗口主体变化
 * @param callback
 */
const onLogout = (callback) => {
  ipcMain.on('closeChatMain', (event, config) => {
    callback(config)
  })
}

/**
 * 调整窗口大小
 * @param mainWindow
 * @param width
 * @param height
 * @param isAllow
 */
const resizeWindow = (mainWindow, width, height, isAllow = false) => {
  console.log('调整窗口大小...', width, height)
  // 临时允许修改窗口大小
  mainWindow.setResizable(true)
  mainWindow.setSize(width, height)
  // 不允许修改窗口大小
  mainWindow.setResizable(isAllow)
}

/**
 * 窗口操作
 */
const winOperate = (callback) => {
  ipcMain.on('winOperate', (event, { action, data }) => {
    callback(event, { action, data })
  })
}

/**
 * 监听数据存储
 */
const onSetLocalStore = () => {
  ipcMain.on('setLocalStore', (event, { key, value }) => {
    store.setData(key, value)
    console.log(store.getData(key))
  })
}

/**
 * 监听获取数据
 */
const onGetLocalStore = () => {
  ipcMain.on('getLocalStore', (event, key) => {
    console.log('收到渲染进程的获取事件', key)
    event.sender.send('getLocalStoreCallback', '这是主进程返回的内容：' + store.getData(key))
  })
}

export {
  onLoginOrRegisterOrForget,
  onLogin,
  onLogout,
  resizeWindow,
  winOperate,
  onSetLocalStore,
  onGetLocalStore
}
