gradle命令
-----------------------------
命令结构：gradlew [taskName...] [--option-name...] 多个任务用空格分隔
常见命令：
    .\gradlew -v 查看版本号
    .\gradlew build 检查依赖并编译打包
    .\gradlew assembleDebug 编译并打出debug包
    .\gradlew assembleRelease 
    .\gradlew installDebug 编译打出debug包并安装
    .\gradlew installRelease
    .\gradlew assembleDebug --info 编译打包并打印日志
    .\gradlew assembleRelease --info 
    .\gradlew clean
    .\gradlew uninstallDebug 卸载本项目安装包
    .\gradlew uninstallRelease
        adb uninstall 包名 （adb卸载须指定报名）
    .\gradlew assembleDebug --stacktrace 并打印堆栈日志
    .\gradlew tasks 查看主要task
    .\gradlew tasks --all 查看全部task
    .\gradlew taskName 执行task
        .\gradlew :moduleName:taskName
    .\gradlew dependencies 查看依赖
    .\gradlew assembleDebug --offline 离线编译
    .\gradlew assembleDebug --build-cache 构建缓存 开启
    .\gradlew assembleDebug --no-build-cache 构建缓存 不开启
    .\gradlew assembleDebug --configuration-cache 配置缓存 开启
    .\gradlew assembleDebug --no-configuration-cache 配置缓存 不开启
    .\gradlew assembleDebug --parallel 并行构建 开启
    .\gradlew assembleDebug --no-parallel 并行构建 不开启
    .\gradlew assembleDebug --profile 编译并输出性能报告 输出的是html文件，位于构建项目的GradleX/build/reports/profile/
    .\gradlew assembleDebug --scan 编译并输出更详细性能报告 
        首次执行需要邮箱验证授权，报告比profile更加详细
    动态传参 --project-prop常用-P表示，用来设置跟项目的属性
        .\gradlew assembleDebug -PisTest=true 用-P传了一个字段isTest并赋值true，在build.gradle中编译如下代码获取这个参数
            if(hasProperty("isTest")){
                println("---hasProperty isTest")
            }
        getProperty('isTest')获取参数用单引号
            ps：获取的参数都是对象，使用需要转换类型
自定义操作

