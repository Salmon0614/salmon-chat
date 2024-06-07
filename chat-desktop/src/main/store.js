const Store = require('electron-store')
const store = new Store()

//-------------用户ID-----------------
let userId = null

const initUserId = (_userId) => {
  userId = _userId
}

const getUserId = () => {
  return userId
}

// -------------------------------------

// -------------数据存储相关操作-----------
const setData = (key, value) => {
  store.set(key, value)
}

const getData = (key) => {
  return store.get(key)
}

const deleteData = (key) => {
  store.delete(key)
}

// -------------------------------------

//--------存储用户信息相关---------------
const setUserData = (key, value) => {
  setData(userId + ':' + key, value)
}

const getUserData = (key) => {
  return getData(userId + ':' + key)
}

const deleteUserData = (key) => {
  deleteData(userId + ':' + key)
}

// -------------------------------------

export default {
  setData,
  getData,
  deleteData,
  initUserId,
  getUserId,
  setUserData,
  getUserData,
  deleteUserData
}
