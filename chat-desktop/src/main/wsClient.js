import WebSocket from 'ws'

const NODE_ENV = process.env.NODE_ENV
import store from './store'

let ws = null
let maxReconnectTimes = null // 最大重连次数

let wsUrl = null
let sender = null
let needReconnect = null
let lockReconnect = false
const initWs = (config, _sender) => {
  console.log('ws初始化...')
  wsUrl = `${NODE_ENV !== 'development' ? store.getData('prodWsDomain') : store.getData('devWsDomain')}?token=${config.token}`
  sender = _sender
  needReconnect = true
  maxReconnectTimes = 5
  createWs()
}

const closeWs = () => {
  needReconnect = false
  ws.close()
}

const createWs = () => {
  if (wsUrl == null) {
    return
  }
  console.log(wsUrl)
  ws = new WebSocket(wsUrl)
  ws.onopen = function () {
    console.log('客户端连接成功')
    ws.send('heart beat')
    maxReconnectTimes = 5
  }
  // 从服务端收到消息的回调函数
  ws.onmessage = function (e) {
    console.log('收到服务器消息', e.data)
    // sender.send('receiveMessage', e.data)
  }

  ws.onclose = function () {
    console.log('关闭客户端连接重连')
    reconnect()
  }

  ws.onerror = function () {
    console.log('连接失败了准备重连')
    reconnect()
  }

  const reconnect = () => {
    if (!needReconnect) {
      console.log('连接断开无需重连')
      return
    }
    if (ws != null) {
      ws.close()
    }
    if (lockReconnect) {
      return
    }
    lockReconnect = true
    if (maxReconnectTimes > 0) {
      console.log('准备重连，剩余重连次数：' + maxReconnectTimes, new Date().getTime())
      maxReconnectTimes--
      setTimeout(() => {
        createWs()
        lockReconnect = false
      }, 5000)
    } else {
      console.log('连接已经超时')
    }
  }
  setInterval(() => {
    if (ws != null && ws.readyState === 1) {
      ws.send('heart beat')
    }
  }, 5000)
}

export { initWs, createWs, closeWs }
