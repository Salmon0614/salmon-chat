import electron, { app, shell, BrowserWindow, Menu, Tray } from 'electron'
import { join } from 'path'
import { electronApp, optimizer, is } from '@electron-toolkit/utils'
// import icon from '../../resources/icon.png?asset'

import { resizeWindow, onLoginOrRegisterOrForget, onLogin, onLogout, winOperate } from './ipc'

const NODE_ENV = process.env.NODE_ENV
const isMac = process.platform === 'darwin'

const login_width = 330
const login_height = 440
const register_height = 570
const forget_password_height = 470

let iconPath = join(__dirname, '../../resources/icon.png')
let willQuitApp = false

function createWindow() {
  // Create the browser window.
  const mainWindow = new BrowserWindow({
    title: 'SalmonChat',
    icon: iconPath, // 只对 windows/Linux 系统生效
    width: login_width,
    height: login_height,
    show: false,
    autoHideMenuBar: true,
    // 窗口的bar隐藏
    titleBarStyle: 'hidden',
    // 是否可以调整窗口大小
    resizable: false,
    // 隐藏窗口的边框
    frame: false,
    // 创建透明窗口
    transparent: true,
    webPreferences: {
      nodeIntegration: true, // 为了解决 require 识别问题
      preload: join(__dirname, '../preload/index.js'),
      // 上下文隔离（官方不建议关闭）
      contextIsolation: true, // 保持 contextIsolation 为 true 以确保安全
      sandbox: false
    }
  })

  //打开控制台
  if (NODE_ENV === 'development') {
    mainWindow.webContents.openDevTools()
  }

  // mac设置图标
  if (isMac) {
    electron.app.dock.setIcon(iconPath)
    electron.app.setName('SalmonChat')
  }

  mainWindow.on('ready-to-show', () => {
    mainWindow.show()
    mainWindow.setTitle('SalmonChat')
  })

  mainWindow.on('close', (event) => {
    // 阻止默认退出事件改为最小化到托盘
    if (isMac && !willQuitApp) {
      event.preventDefault()
      app.hide()
    }
  })

  mainWindow.webContents.setWindowOpenHandler((details) => {
    shell.openExternal(details.url)
    return { action: 'deny' }
  })

  // HMR for renderer base on electron-vite cli.
  // Load the remote URL for development or the local html file for production.
  if (is.dev && process.env['ELECTRON_RENDERER_URL']) {
    mainWindow.loadURL(process.env['ELECTRON_RENDERER_URL'])
  } else {
    mainWindow.loadFile(join(__dirname, '../renderer/index.html'))
  }

  // 托盘，mac默认有退出按钮，无需重复添加
  const contextMenu = isMac
    ? []
    : [
        {
          label: '退出',
          click: function () {
            app.exit()
          }
        }
      ]
  const tray = new Tray(iconPath)
  const menu = Menu.buildFromTemplate(contextMenu)
  Menu.setApplicationMenu(menu)
  tray.setTitle('SalmonChat')
  tray.setToolTip('SalmonChat')
  tray.setContextMenu(menu)
  tray.on('click', () => {
    mainWindow.setSkipTaskbar(false)
    mainWindow.show()
    app.show()
  })
  if (isMac) {
    app.dock.setMenu(menu)
  }

  // 监听登录、注册、忘记密码窗口
  onLoginOrRegisterOrForget((event, viewType) => {
    const webContents = event.sender
    // 拿到当前操作的窗口
    const win = BrowserWindow.fromWebContents(webContents)
    let width = login_width
    let height = login_height
    if (viewType === 2) {
      width = login_width
      height = forget_password_height
    } else if (viewType === 1) {
      width = login_width
      height = register_height
    }
    resizeWindow(win, width, height)
  })

  onLogin((event, config) => {
    const webContents = event.sender
    // 拿到当前操作的窗口
    const win = BrowserWindow.fromWebContents(webContents)
    resizeWindow(win, 850, 800, true)
    // 居中显示
    win.center()
    // 可以最大化
    win.setMaximizable(true)
    // 设置最小的窗口大小
    win.setMinimumSize(800, 600)

    // todo 管理后台的窗口操作，托盘操作
    if (config.isAdmin) {
      // 管理后台的窗口
      console.log('admin')
    }

    contextMenu.unshift(
      {
        label: '用户: ' + config.nickname,
        click: function () {}
      },
      {
        label: '退出登录',
        click: function () {}
      }
    )
    // 重新加载
    if (isMac) {
      app.dock.setMenu(Menu.buildFromTemplate(contextMenu))
    } else {
      tray.setContextMenu(Menu.buildFromTemplate(contextMenu))
    }
  })

  winOperate((event, { action, data }) => {
    const webContents = event.sender
    // 拿到当前操作的窗口
    const win = BrowserWindow.fromWebContents(webContents)
    switch (action) {
      case 'top': {
        win.setAlwaysOnTop(data.top)
        break
      }
      case 'minimize': {
        win.minimize()
        break
      }
      case 'unMaximize': {
        win.unmaximize()
        break
      }
      case 'maximize': {
        win.maximize()
        break
      }
      case 'close': {
        if (!isMac) {
          if (data.closeType === 0) {
            win.close()
          } else {
            // 隐藏于系统托盘区域，mac有自己的按钮，非自定义按钮
            // win缩小到托盘
            win.setSkipTaskbar(true) // 使窗口不显示在任务栏中
            win.hide() // 隐藏窗口
          }
        }
        break
      }
    }
  })
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.whenReady().then(() => {
  // Set app user model id for windows
  electronApp.setAppUserModelId('com.electron')

  // Default open or close DevTools by F12 in development
  // and ignore CommandOrControl + R in production.
  // see https://github.com/alex8088/electron-toolkit/tree/master/packages/utils
  app.on('browser-window-created', (_, window) => {
    optimizer.watchWindowShortcuts(window)
  })

  createWindow()

  // 点击MacOS底部菜单时重新启动窗口
  app.on('activate', function () {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (BrowserWindow.getAllWindows().length === 0) {
      createWindow()
    }
  })
})

// 只有显式调用quit才退出系统，区分MAC系统程序坞退出和点击X关闭退出
app.on('before-quit', () => {
  console.log('before-quit')
  willQuitApp = true
})

// Quit when all windows are closed, except on macOS. There, it's common
// for applications and their menu bar to stay active until the user quits
// explicitly with Cmd + Q.
app.on('window-all-closed', () => {
  // 在 macOS 上，除非用户用 Cmd + Q 确定地退出，
  // 否则绝大部分应用及其菜单栏会保持激活。
  if (!isMac) {
    app.quit()
  }
})

// In this file you can include the rest of your app"s specific main process
// code. You can also put them in separate files and require them here.
