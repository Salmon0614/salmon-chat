// preload.js 预加载脚本
import { contextBridge, ipcRenderer } from 'electron'
import { electronAPI } from '@electron-toolkit/preload'

// Custom APIs for renderer
const api = {}

// Use `contextBridge` APIs to expose Electron APIs to
// renderer only if context isolation is enabled, otherwise
// just add to the DOM global.
if (process.contextIsolated) {
  try {
    contextBridge.exposeInMainWorld('electron', electronAPI)
    contextBridge.exposeInMainWorld('api', api)
    // 将使用node模块的接口暴露给渲染进程使用
    // 调整窗口
    contextBridge.exposeInMainWorld('changeWindowSize', {
      changeLoginWindow: (data) => {
        ipcRenderer.send('loginOrRegisterOrForget', data)
      },
      changeChatWindow: (config) => {
        ipcRenderer.send('openChatMain', config)
      }
    })
    // 操作窗口
    contextBridge.exposeInMainWorld('operateWindow', {
      operateWindowTitle: ({ action, data }) => {
        ipcRenderer.send('winOperate', { action, data })
      }
    })
    // 数据操作
    contextBridge.exposeInMainWorld('localStore', {
      set: ({ key, value }) => {
        ipcRenderer.send('setLocalStore', { key, value })
      },
      get: (key) => {
        ipcRenderer.send('getLocalStore', key)
      },
      getCallback: (callback) => {
        ipcRenderer.on('getLocalStoreCallback', (event, data) => {
          callback(data)
        })
      }
    })
  } catch (error) {
    console.error(error)
  }
} else {
  window.electron = electronAPI
  window.api = api
}
