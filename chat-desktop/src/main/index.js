import {app, shell, BrowserWindow, ipcMain} from 'electron'
import {join} from 'path'
import {electronApp, optimizer, is} from '@electron-toolkit/utils'
import icon from '../../resources/icon.png?asset'

const NODE_ENV = process.env.NODE_ENV

const login_width = 330;
const login_height = 440;
const register_height = 570;
const forget_password_height = 460;

let iconPath;
// 根据平台设置图标路径
if (process.platform === 'darwin') {
  iconPath = join(__dirname, '../../resources/icon.icns?asset');
} else if (process.platform === 'win32') {
  iconPath = join(__dirname, '../../resources/icon.ico?asset');
} else if (process.platform === 'linux') {
  iconPath = join(__dirname, '../../resources/icon.png?asset');
}

function createWindow() {
  // Create the browser window.
  const mainWindow = new BrowserWindow({
    title: "SalmonChat",
    icon: iconPath,
    width: login_width,
    height: login_height,
    show: false,
    autoHideMenuBar: true,
    // 窗口的bar隐藏
    titleBarStyle: "hidden",
    // 是否可以调整窗口大小
    resizable: false,
    // 隐藏窗口的边框
    frame: false,
    // 创建透明窗口
    transparent: true,
    webPreferences: {
      nodeIntegration: true, // 为了解决require 识别问题
      preload: join(__dirname, '../preload/index.js'),
      sandbox: false,
      // 上下文隔离（官方不建议关闭）
      // contextIsolation: false,
    },
  })

  // 主进程监听渲染进程发送的消息（渲染进程触发该信号，主进程接收信号，实现渲染进程到主进程的通信）
  ipcMain.on("loginOrRegisterOrForget", (event, viewType) => {
    // 临时允许修改窗口大小
    mainWindow.setResizable(true);
    if (viewType === 2) {
      mainWindow.setSize(login_width, forget_password_height);
    } else if (viewType === 1) {
      mainWindow.setSize(login_width, register_height);
    } else {
      mainWindow.setSize(login_width, login_height);
    }
    // 不允许修改窗口大小
    mainWindow.setResizable(false);
  })

  //打开控制台
  if (NODE_ENV === 'development') {
    mainWindow.webContents.openDevTools();
  }

  mainWindow.on('ready-to-show', () => {
    mainWindow.show();
    mainWindow.setTitle("SalmonChat");
  })

  mainWindow.webContents.setWindowOpenHandler((details) => {
    shell.openExternal(details.url)
    return {action: 'deny'}
  })

  // HMR for renderer base on electron-vite cli.
  // Load the remote URL for development or the local html file for production.
  if (is.dev && process.env['ELECTRON_RENDERER_URL']) {
    mainWindow.loadURL(process.env['ELECTRON_RENDERER_URL'])
  } else {
    mainWindow.loadFile(join(__dirname, '../renderer/index.html'))
  }
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

  if (process.platform === 'darwin') {
    // app.dock.setIcon(iconPath);
    app.setName("SalmonChat");
    // app.dock.setBadge("SalmonChat");
  }

  createWindow()

  app.on('activate', function () {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (BrowserWindow.getAllWindows().length === 0) createWindow()
  })
})

// Quit when all windows are closed, except on macOS. There, it's common
// for applications and their menu bar to stay active until the user quits
// explicitly with Cmd + Q.
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

// In this file you can include the rest of your app"s specific main process
// code. You can also put them in separate files and require them here.
