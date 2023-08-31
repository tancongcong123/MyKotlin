#生命周期的本质是在各个阶段把任务task组合起来，然后按照我们的意图去构建项目
#Task是gradle构建的核心，其模型是有向无环图。
#Task之间是有依赖的，gradle会在构建配置阶段来生成依赖关系图，也就是task集合
-------------------------------
gradle分三个阶段评估和运行构建，分别是initialization、configuration、execution，且任何构建任务都会执行这三个阶段
    initialization
        1 决定构建中包含哪些项目----settings.gradle决定（单项目还是多项目构建）
            settings.gradle 核心是include，可以指向项目中包含的module路径，也可以指向硬盘中子项目的绝对路径，这在项目aar和源码切换时候非常方便，也是编译提速的重要手段之一
        2 为每个项目创建project实例
        ps：首先会在项目根目录下找settings.gradle，如果没找到作为单项目构建；如果找到了，会校验include的合法性，不合法继续作为单项目构建，合法则作为多项目构建
    configuration(配置)
        1 评估构建项目中包含的所有构建脚本，随后应用插件、使用DSL配置构建 项目的配置决定了task的执行顺序
        2 最后注册Task，同时惰性注册他们的输入（因为并一定会执行），简单来说配置阶段就是创建project对象，执行build.gradle文件，根据代码创建对应的task依赖关系图
        ps：无论请求执行那个task都会执行配置阶段，多以要避免在配置阶段执行任何耗时操作
        3.1 gradle构建时，会根据settings对象解析出来的项目结构为每个项目创建一个project对象，project对象和build.gradle存在一一对应的关系
            gradle生成task依赖图之前，project对象还做了几件事：
                引入插件 使用plugins，plugins是Project的一个方法，用于设置当前模块所使用的插件
                配置属性 android{}，android{}实质上是id 'com.android.application '插件的DSL配置，也就是说我们在build.gradle中所有的配置其实都是通过DSL对插件的配置，这些配置会影响插件的执行，从而影响整个构建流程
                编译依赖 dependencies{} 添加第三方库
    execution 
        执行构建所需要的task集合，真正的编译打包
-------------------------------
hook生命周期
    gradle在生命周期的各个阶段提供个丰富的回调，对做切面处理非常有用
    初始化阶段 settings.gradle中hook
        1 gradle.settingsEvaluated{} 表示settings对象评估完成，此时可以获取settings对象
            settings对象是管理项目和插件的，此时可以做一些全局的操作，比如为所有项目添加某个插件
        2 gradle.settingsEvaluated{}执行完是gradle.projectsLoaded{} 已经根据settings创建各个模块的project对象了，可以引用project对象设置hook
            如 gradle.allprojects{
                beforeEvaluate {
                    println("---Gradle：Projec beforeEvaluate Project开始评估，对象是 = "+project.name)
                }
                afterEvaluate {
                    println("---Gradle：Projec afterEvaluate Project评估完毕，对象是 = "+project.name)
                }
            }
            此时的project对象还只包含项目的基本信息，拿不到build.gradle里面的配置信息
    配置阶段 在build.gradle中操作
        此时可以拿到所有参与构建的项目配置，首先会执行app的build.gradle，然后执行module里面的build.gradle
            project.beforeEvaluate {
                println("---project：beforeEvaluate Project开始评估，对象是 = " + project.name)
            }
            project.afterEvaluate {
                println("---project：afterEvaluate Project评估完毕，对象是 = " + project.name)
            }
            gradle.projectsEvaluated {
                println("---gradle：projectsEvaluated 所有project对象评估完毕")
            }
            beforeEvaluate不会执行，因为到这个位置build.gradle的内容已经走完了，所以不会生效
            afterEvaluate表示project对象evaluate完毕，此时可以获取project对象里面的信息，因为这个阶段project刚刚配置完毕，因此很多动态任务都是在这个阶段添加到构建中的
            projectsEvaluated所有project对象evaluate完毕会回调，至此所有对象都已创建完毕（gradle，settings，各个模块的project对象）
    执行阶段
        可以添加TaskExecutionListener来hook Task的执行，7.3之后废弃了
            gradle.addBuildListener(new TaskExecutionListener(){
                @Override
                void beforeExecute(Task task) {
                    println("---Gradle：Task beforeExecute---")
                }
                @Override
                void afterExecute(Task task, TaskState state) {
                    println("---Gradle：Task afterExecute---")
                }
            })
        task是gradle的最小构建的单元，action是最小的执行单元，可以添加TaskActionListener来hook task action的执行,同上也废弃了
            gradle.addBuildListener(new TaskActionListener(){
                @Override
                void beforeActions(Task task) {
                    println("---Gradle：Task beforeActions---")
                }
                @Override
                void afterActions(Task task) {
                    println("---Gradle：Task afterActions---")
                }
            })
    结束
        构建结束会回调gradle.buildFinished也废弃了
    废弃的替代
        gradle提供了build service代替
        TaskActionListener
            // build.gradle.kts
            abstract class BuildListenerService :BuildService<BuildListenerService.Params>, org.gradle.tooling.events.OperationCompletionListener {
                interface Params : BuildServiceParameters
                override fun onFinish(event: org.gradle.tooling.events.FinishEvent) {
                    println("BuildListenerService got event $event")
                }
            }
            val buildServiceListener = gradle.sharedServices.registerIfAbsent("buildServiceListener", BuildListenerService::class.java) { }
            abstract class Services @Inject constructor(
                val buildEventsListenerRegistry: BuildEventsListenerRegistry
            )
            val services = objects.newInstance(Services::class)
            services.buildEventsListenerRegistry.onTaskCompletion(buildServiceListener)
        TaskExecutionListener BuildEventsListenerRegistry
        buildFinished OperationCompletionListener