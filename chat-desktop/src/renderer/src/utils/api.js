const api = {
  // 后端服务地址
  domain: {
    prod: 'http://127.0.0.1:5050',
    dev: 'http://127.0.0.1:5050',
    wsProd: 'ws:/127.0.0.1:5051/ws',
    wsDev: 'ws:/127.0.0.1:5051/ws'
  },
  // 账号相关
  account: {
    getCheckCode: '/account/getCheckCode', // 验证码
    loginByEmail: '/account/loginByEmail', // 登录
    registerByEmail: '/account/registerByEmail', // 注册
    forgetPassword: '/account/forgetPassword', // 忘记密码
    logout: '/account/logout' // 退出登录
  },
  // 用户信息相关
  userInfo: {
    saveUserInfo: '/user/saveUserInfo', // 保存当前用户信息
    getUserInfo: '/user/getUserInfo', // 获取当前用户信息
    updatePassword: '/user/updatePassword' // 更新当前用户密码
  },
  // 群组相关
  group: {
    loadMyGroup: '/group/loadMyGroup', //获取我创建的群组
    saveOrUpdateGroup: '/group/saveOrUpdateGroup', //保存群组
    getGroupInfo: '/group/getGroupInfo', //获取群组信息
    getGroupInfo4Chat: '/group/getGroupInfo4Chat', //获取群聊详细信息
    dissolutionGroup: '/group/dissolutionGroup', //解散群组
    leaveGroup: '/group/leaveGroup', //退出群组
    addOrRemoveGroupUser: '/group/addOrRemoveGroupUser' //添加或者删除群成员
  },
  // 好友相关
  contact: {
    search: '/contact/search', //搜索好友/群
    applyAdd: '/contact/applyAdd', //申请加入
    loadApply: '/contact/loadApply', //获取申请列表
    dealWithApply: '/contact/dealWithApply', //处理申请
    loadContact: '/contact/loadContact', //获取联系人列表
    getContactUserInfo: '/contact/getContactUserInfo', //获取联系人详情（必须是好友（包含被拉黑或者被删））
    addContactToBlackList: '/contact/addContactToBlackList', //拉黑联系人
    delContact: '/contact/delContact', //删除联系人
    getContactInfo: '/contact/getContactInfo' //获取联系人详情（不一定是好友，比如群成员里看详情））
  },
  // 聊天相关
  chat: {
    sendMessage: '/chat/sendMessage', //发送消息
    uploadFile: '/chat/uploadFile' //上传文件地址
  },
  // 后台管理相关
  admin: {
    user: {
      queryUserPage: '/admin/queryUserPage', //后台获取用户列表
      updateUserStatus: '/admin/updateUserStatus', //后台更新用户状态
      forceOffLine: '/admin/forceOffLine' //强制下线
    },
    group: {
      queryGroupPage: '/admin/queryGroupPage', //群组列表
      adminDissolutionGroup: '/admin/dissolutionGroup' //解散群组
    },
    setting: {
      getSysSetting: '/admin/getSystemConfig', // 获取系统设置
      saveSysSetting: '/admin/saveSysSetting' //保存系统设置
    },
    app: {
      loadUpdateDataList: '/admin/appUpdate/queryPage', //获取APP发布列表
      delAppUpdate: '/admin/appUpdate/delete', //删除APP发布信息
      addAppUpdate: '/admin/appUpdate/add', //添加APP发布信息
      updateAppUpdate: '/admin/appUpdate/update', //更新APP发布信息
      postAppUpdate: '/admin/appUpdate/post' //发布更新
    },
    userBeauty: {
      queryBeautyAccountPage: '/admin/userBeauty/queryPage', //靓号列表
      addBeautAccount: '/admin/userBeauty/add', //保存靓号
      updateBeautyAccount: '/admin/userBeauty/update', //更新靓号
      delBeautAccount: '/admin/userBeauty/delete' //删除靓号
    }
  },
  // app 相关
  app: {
    checkVersion: '/app/update/checkVersion' //更新检测
  }
}

export default api
