## ！重要提醒

**前端使用Node版本推荐16，高于17的Node会导致部分依赖无法安装，且dev server无法正常启动**

**参考版本16.14.0，如果觉得切换Node版本麻烦，可以了解一下nvm工具**



## 开发

请在当前目录打开cmd执行下面的命令

```bash
# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npmmirror.com

# 启动服务
npm run dev
```

浏览器访问 http://localhost:80

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```