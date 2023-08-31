gradle的配置主要是用来管理gradle自己的运行环境和我们的项目
    管理gradle自己的运行环境比如java11的运行环境，更大的运行空间等，这类配置一般是固定的
    管理我们的项目 管理代码文件和资源文件，module，依赖及打包信息，具有一定的动态可配性
    配置的优先级
        command-line flags：命令行标志，如--stacktrace这些优先于属性和环境变量
        system properties：系统属性，如systemProp.http.proxyHost=somehost.org存储在gradle.properties文件中
        gradle properties：gradle属性，如org.gradle.caching=true通常存储在项目根目录或GRADLE_USER_HOME环境变量中的gradle.properties中
        environment variable：环境变量，如GRADLE_OPTS由执行的环境源提供
    gradle配置
        gradle-wrapper
        build.gradle(Project)
        build.gradle(module)
        setting.gradle
        gradle.properties
        local.properties
        ----------------------------
        gradle-wrapper
            |--gradle
            |    |--wrapper
            |        |--gradle-wrapper.jar 主要是gradle的运行逻辑，包含下载gradle
            |        |--gradle-wrapper.properties gradle-wrapper的配置文件，核心是定义了gradle的版本
            |--gradlew gradle wrapper的简称，Linux下的执行脚本
            |--gradlew.bat windows下的执行脚本
            gradle-wrapper.properties
                distributionBase=GRADLE_USER_HOME 下载的gradle压缩包解压后的主目录
                distributionPath=wrapper/dists 同distributionBase，不过是存放zip的主目录
                zipStoreBase=GRADLE_USER_HOME 相对于distributionBase解压后的gradle的路径，为wrapper/dists
                zipStorePath=wrapper/dists 同distributionPath不过是存放zip压缩包的
                distributionUrl=https\://services.gradle.org/distributions/gradle-6.7.1-all.zip
        build.gradle(Project)
            7.0之后project下的build.gradle文件变动很大，默认只有plugin引用了，其他原有配置都挪到setting.gradle文件中了
            apply plugin:'' 叫做二进制插件，一般是被打包在一个jar中独立发布的
            apply from:'' 叫做应用脚本插件，就是把这个脚本加载进来，可以是本地的，也可以是网络的
            buildscript中的生命是gradle脚本本身需要用到的资源。可以声明的资源包含依赖项、第三方插件、maven仓库地址等。7.0之后挪到了setting中配置pluginManagement
            ext 项目全局属性，多用于自定义
            repositories：仓库 如Google() maven() jcenter() jitpack等三方托管平台
            dependencies：配置了仓库还需要在dependencies{}里面配置classpath
            allprojects:里面的repositories由于多项目构建，为所有项目提供共同所需要依赖的依赖包
            task clean(type: Delete) { delete rootProject.buildDir} 执行gradlew clean，删除生成的build文件
        build.gradle(Project) 用于特定模块配置，7.0前后变化不大
            app插件id com.android.application
            library插件id com.android.library
            kotlin插件id com.jetbrains.kotlin.android
            buildTypes 构建类型，默认模式为debug/release
                applicationIdSuffix 应用id后缀
                versionNameSuffix 应用id后缀
                ProguardFiles 混淆文件
                multiDexKeepFile 指定文件编译进主Dex
                multiDexKeepProguard 指定混淆文件编译进主Dex
            productFlavors 多渠道打包，实现定制版本的需求
            buildConfigField 是BuildConfig文件的一个函数，而BuildConfig这个类是Android gradle构架脚本在编译后生成的，如版本号之类的
            build Variants 选择编译的版本
            buildFeatures 开启或关闭构建功能，常见的有viewBing，dataBing，compose等
    依赖
        依赖类型
            本地模块 implementation project(':mylibrary')
            本地二进制文件 implementation fileTree(dir:'libs', include:['*.jar'])
            远端二进制文件 implementation 'com.xx.xx:xx:1.0'
                GAV  group:artifactId:version
        依赖传递
            implementation
            api
            annotationProcessor 添加注解处理器的库，必须使用annotationProcessor配置将其添加到注解处理器的类路径，这是因为此配置可以将编译类路径和注解处理器路径分开，从而提高build性能
        版本决议
            同一个模块的多个相同依赖，优先选择最高版本
            多一个模块的多个相同依赖，优先选择主模块（app）的版本，并默认有strictly关键字约束
            多一个模块的多个相同依赖，因为主模块（app）的版本默认有strictly关键字约束，所以子模块的强制版本约束是失效的
                implementation('com.squareup.okhttp3:okhttp') {
                    version{
                        strictly("4.10.0")
                    }
                }
                strictly可以简写为：implementation 'com.squareup.okhttp3:okhttp:4.10.0!!'
            force优先级高于strictly，二者同时显示声明会报错,以下代码会报错
                implementation( 'com.squareup.okhttp3:okhttp:4.10.0'){
                    force(true)
                }
                implementation 'com.squareup.okhttp3:okhttp:5.0.0-alpha.11!!'
            同时使用force强制依赖版本的时候，版本决议跟依赖顺序相关，最早依赖的版本优先，以下代码依赖4.10.0
                implementation( 'com.squareup.okhttp3:okhttp:4.10.0'){
                    force(true)
                }
                implementation( 'com.squareup.okhttp3:okhttp:5.0.0-alpha.11'){
                    force(true)
                }
            当项目中的依赖和第三方库中的依赖相同时，优先选择高版本
            当多个三方库中的依赖相同时，优先选择高版本，且子随父
        解决冲突
            1 做好版本管理
                新建一个或多个gradle文件来做依赖和版本的双重管理，如version.gradle
                使用ext{}
                使用buildSrc，相比于ext，buildSrc可以把依赖和版本都单独抽出去，且支持提示和跳转
                7.0之后的catalog，对所有的module可见，可统一管理所有module的依赖，支持在项目间共享依赖
            2 强制版本
                force 新版本已废弃
                strictly
                exclude 通过排除规则，来排除此依赖的可传递性依赖
                    implementation('com.squareup.retrofit2:retrofit:2.9.0') {
                        exclude(group: "com.squareup.okhttp3", module: "okhttp")
                    }
                transitive 是否排除当前依赖包含的可传递依赖项 false不传递 true传递
                    implementation('com.squareup.retrofit2:retrofit:2.9.0') {
                        transitive(false)
                    }
                configurations 基于gradle生命周期hook的后置操作，终极方案
                    第一种 遍历指定版本
                    configurations.all {
                        resolutionStrategy.eachDependency { DependencyResolveDetails details ->
                            def requested = details.requested
                            if (requested.group == 'com.yechaoa.plugin' && requested.name == 'plugin') {
                                details.useVersion PLUGIN_VERSION
                            }
                        }
                    }
                    // PLUGIN_VERSION为gradle.properties定义的变量
                    第二种 强制指定版本
                    configurations.all {
                        resolutionStrategy.force 'com.squareup.okhttp3:okhttp:4.10.0'
                        或者
                        resolutionStrategy{
                            force('com.squareup.okhttp3:okhttp:4.10.0')
                        }
                    }
                    ps:当存在版本冲突，又不知道哪些是重复的时候，可以开启版本冲突报错模式，编译解析时候只要有重复的版本，控制台就会直接报错并输出依赖和版本
                        configuration.all {
                            resolutionStrategy{ failOnVersionConflict() }
                        }
