// 判断是否为空
const isEmpty = (value) => {
  return value == null || value === '' || value === undefined
}

// 校验指定长度
const validateLength = (string, minLength, maxLength) => {
  return string.length >= minLength && string.length <= maxLength
}

export default {
  isEmpty,
  validateLength
}
