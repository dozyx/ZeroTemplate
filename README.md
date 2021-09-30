本来想要的是创建一套个人快速开发模板，结果又把一些 sample 加了进去，所以该库作为存放个人模板和 demo 代码。



## 开发说明

### 新增 module

module 名称规范：小写，下划线分隔

清理：

* 使用通用模板修改 `build.gradle`
* 删除 `androidTest`、`test`、`libs`、`.gitignore`

`build.gradle` 处理：

```groovy
// app module
apply from: "${rootDir}/buildApp.gradle"

// lib module
apply from: "${rootDir}/buildLib.gradle"
```

appliationId 会根据 module 名生成，格式：`cn.dozyx.[subfolder.].module_name`

