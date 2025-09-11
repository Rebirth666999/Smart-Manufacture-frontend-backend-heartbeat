<template>
  <el-dialog title="扫码登录" :visible.sync="visible" width="300px">
    <div v-loading="loading" class="qr-container">
      <!-- 添加二维码显示 -->
      <img v-if="qrCodeUrl" :src="qrCodeUrl" alt="扫码登录" class="qrcode-img">

      <div class="status">{{ statusText }}</div>
      <div class="expire">有效期: {{ countdown }}秒</div>
      <el-button v-if="isExpired" @click="generateNew">刷新二维码</el-button>
    </div>
  </el-dialog>
</template>

<script>
import QRCode from 'qrcode'
import { generateQr, checkQrStatus } from '@/api/login'

export default {
  data() {
    return {
      visible: false,
      qrCodeUrl: '',
      qrToken: '',
      timer: null,
      countdown: 120,
      statusText: '正在生成二维码...',
      loading: false,
      isExpired: false
    }
  },
  methods: {
    open() {
      this.visible = true
      this.generateQrCode()
    },

// 完善二维码显示和状态处理
    async generateQrCode() {
      try {
        const res = await generateQr();
        this.qrToken = res.data.qrToken;
        this.countdown = 120;

        // 生成二维码
        const qrContent = `applogin://${this.qrToken}`;
        QRCode.toDataURL(qrContent, { width: 200 }, (err, url) => {
          if (err) throw err;
          this.qrCodeUrl = url;
          this.statusText = '请使用APP扫码';
          this.startPolling();
        });
      } catch (e) {
        this.$message.error('二维码生成失败');
        console.error(e);
      }
    },

    startPolling() {
      clearInterval(this.timer)
      this.timer = setInterval(async () => {
        if (this.countdown <= 0) {
          clearInterval(this.timer)
          this.statusText = '二维码已过期'
          this.isExpired = true
          return
        }

        this.countdown--

        try {
          const res = await checkQrStatus(this.qrToken)
          switch (res.data.state) {
            case 'SCANNED':
              this.statusText = '已扫码，请在APP确认'
              break
            case 'CONFIRMED':
              clearInterval(this.timer)
              // 修改状态处理方法
              this.loginWithToken(res.data.tempToken); // 使用临时令牌
              break
          }
        } catch (e) {
          console.error('轮询错误', e)
        }
      }, 1000)
    },

    // 修改登录方法
// QrLogin.vue
    async loginWithToken(tempToken) {
      try {
        await this.$store.dispatch('QrLogin', tempToken);
        this.$message.success('登录成功');
        this.visible = false;
        this.$router.push('/');
      } catch (e) {
        this.statusText = '登录失败: ' + (e.message || '未知错误');
        // 添加调试信息
        console.error('扫码登录错误:', e);
      }
    },

    generateNew() {
      this.isExpired = false
      this.generateQrCode()
    }
  },

  beforeDestroy() {
    clearInterval(this.timer)
  }
}
</script>

<style scoped>
.qr-container {
  text-align: center;
  padding: 15px;
}
.status {
  margin: 10px 0;
  font-weight: bold;
  color: #409EFF;
}
.expire {
  color: #909399;
  font-size: 12px;
}
</style>
