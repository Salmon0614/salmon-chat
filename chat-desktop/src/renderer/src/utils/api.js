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
    forgetPassword: '/account/forgetPassword' // 忘记密码
  },
  // 用户信息相关
  userInfo: {
    saveUserInfo: '/userInfo/saveUserInfo', // 保存用户信息
    getUserInfo: '/userInfo/getUserInfo', // 获取用户信息
    updatePassword: '/userInfo/updatePassword', // 更新密码
    logout: '/userInfo/logout' // 退出登录
  },
  // 群组相关
  group: {
    loadMyGroup: '/group/loadMyGroup', //获取我创建的群组
    saveGroup: '/group/saveGroup', //保存群组
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
    addContact2BlackList: '/contact/addContact2BlackList', //拉黑联系人
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
    loadAdminAccount: '/admin/loadUser', //后台获取用户列表
    updateUserStatus: '/admin/updateUserStatus', //后台更新用户状态
    forceOffLine: '/admin/forceOffLine', //强制下线
    loadGroup: '/admin/loadGroup', //群组列表
    adminDissolutionGroup: '/admin/dissolutionGroup', //解散群组
    saveSysSetting: '/admin/saveSysSetting', //保存系统设置
    getSysSetting4Admin: '/admin/getSysSetting', //获取系统设置
    loadUpdateDataList: '/admin/loadUpdateList', //获取更新列表
    delUpdate: '/admin/delUpdate', //删除更新
    saveUpdate: '/admin/saveUpdate', //保存更新
    postUpdate: '/admin/postUpdate', //发布更新
    loadBeautyAccount: '/admin/loadBeautyAccountList', //靓号列表
    saveBeautAccount: '/admin/saveBeautAccount', //保存靓号
    delBeautAccount: '/admin/delBeautAccount' //删除靓号
  },
  // app 相关
  app: {
    checkVersion: '/app/update/checkVersion', //更新检测
    getSysSetting: '/app/getSystemConfig' // 获取系统设置
  }
}

export default api
