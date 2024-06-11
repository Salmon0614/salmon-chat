<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from 'vue'

const { proxy } = getCurrentInstance()

const props = defineProps({
  modelValue: {
    // 文件或者url
    type: [String, Object],
    default: null
  }
})
const preview = ref(false)
const localFile = ref(null)

/**
 * 上传图片
 */
const uploadImage = async (file) => {
  console.log(file)
  console.log(file.file)
  // todo 上传图片
}
</script>

<template>
  <div class="avatar-upload">
    <div class="avatar-show">
      <template v-if="!proxy.$utils.isEmpty(modelValue)">
        <el-image v-if="preview" :src="localFile" fit="scale-down"></el-image>
        <show-local-image v-else :file-id="props.modelValue" part-type="avatar" :width="40" />
      </template>
      <template v-else>
        <el-upload
          name="file"
          :show-file-list="false"
          accept=".png,.PNG,.jpg,.JPG,.jpeg,.JPEG,.gif,.GIF,.bmp,.BMP"
          :multiple="false"
          :http-request="uploadImage"
        >
          <span class="iconfont icon-add"></span>
        </el-upload>
      </template>
    </div>
    <div class="select-btn">
      <el-upload
        name="file"
        :show-file-list="false"
        accept=".png,.PNG,.jpg,.JPG,.jpeg,.JPEG,.gif,.GIF,.bmp,.BMP"
        :multiple="false"
        :http-request="uploadImage"
      >
        <el-button type="primary" size="small">选择</el-button>
      </el-upload>
    </div>
  </div>
</template>

<style scoped lang="scss">
.avatar-upload {
  display: flex;
  justify-content: center;
  align-items: end;
  line-height: normal;

  .avatar-show {
    background: #ededed;
    width: 60px;
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    position: relative;

    .icon-add {
      font-size: 30px;
      color: #b9b9b9;
      width: 60px;
      height: 60px;
      text-align: center;
      line-height: 60px;
    }

    img {
      width: 100%;
      height: 100%;
    }

    .op {
      position: absolute;
      color: #0e8aef;
    }
  }

  .select-btn {
    vertical-align: bottom;
    margin-left: 5px;
  }
}
</style>
