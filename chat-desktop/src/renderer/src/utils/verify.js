const regs = {
  // emailRegex: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/,
  emailRegex1: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/,
  passwordRegex: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/, // 至少8个字符，包含大小写字母和数字
  versionRegex: /^[0-9\.]+$/,
  numberRegex: /^\+?[1-9][0-9]*$/,
  phoneNumberRegex: /^1[3-9]\d{9}$/
}

const verify = (rule, value, reg, callback) => {
  if (value) {
    if (reg.text(value)) {
      callback()
    } else {
      callback(new Error(rule.message))
    }
  } else {
    callback()
  }
}

// 校验密码（至少8个字符，包含大小写字母和数字）
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
  validatePassword,
  validateEmail,
  validatePhoneNumber,
  password,
  email,
  number,
  version
}
