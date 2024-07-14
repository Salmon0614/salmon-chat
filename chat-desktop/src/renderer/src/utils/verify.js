const regs = {
  // emailRegex: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/,
  emailRegex1: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/,
  passwordRegex:
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d!@#$%^&*()_+\-\[\]{};':"\\|,.<>\/?`~]{8,18}$/, // 至少8个字符，最多18个字符，必须包含大小写字母、数字，可填写特殊字符（不强制）
  versionRegex: /^[0-9\.]+$/,
  numberRegex: /^\+?[1-9][0-9]*$/,
  phoneNumberRegex: /^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/
}

const regErrMsg = {
  passwordError: '密码必须包含大小写字母、数字（8-18位）',
  rePasswordError: '两次输入的密码不一致',
  emailError: '请输入正确的邮箱',
  versionError: '',
  numberError: '',
  phoneError: ''
}

const verify = (rule, value, reg, callback) => {
  if (value) {
    if (reg.test(value)) {
      callback()
    } else {
      callback(new Error(rule.message))
    }
  } else {
    callback()
  }
}

// 校验密码（至少8个字符，最多18个字符，必须包含大小写字母、数字，可填写特殊字符（不强制））
const validatePassword = (password) => {
  return regs.passwordRegex.test(password)
}

// 校验邮箱
const validateEmail = (email) => {
  return regs.emailRegex1.test(email)
}

// 校验手机号
const validatePhoneNumber = (phoneNumber) => {
  return regs.phoneNumberRegex.test(phoneNumber)
}

// element form 校验
const password = (rule, value, callback) => {
  verify(rule, value, regs.passwordRegex, callback)
}

const email = (rule, value, callback) => {
  verify(rule, value, regs.emailRegex1, callback)
}

const number = (rule, value, callback) => {
  verify(rule, value, regs.numberRegex, callback)
}

const version = (rule, value, callback) => {
  verify(rule, value, regs.versionRegex, callback)
}

export default {
  regErrMsg,
  validatePassword,
  validateEmail,
  validatePhoneNumber,
  password,
  email,
  number,
  version
}
