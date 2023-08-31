Task
--------------------
task的创建是在project中，一个build.gradle对应一个project文件，所以可以直接在build.gradle中创建task
使用TaskContainer的register方法创建task
执行task
    .\gradlew taskname1 taskname2
task执行结果
    Executed 表示task执行
    UP-TO-DATE 表示task的输出没有改变
        1 输入输出都没有改变
        2 输出没有改变
        3 task没有操作有依赖，但依赖的内容是最新的或者跳过了或者复用了
        4 task没有操作也没有依赖
    FORCE-CACHE 可以从缓存中复用上一次的执行结果
    SKIPPED 表示跳过，比如被排除（gradlew dist --exclude-task taskName）
    NO-SOURCE task不需要执行，有输入和输出，但没有来源
task增量构建
    当task的输入和输出没有变化时，跳过action的执行，当输入或输出发生变化时，在action中只对发生变化的输入或输出进行处理，这样可以避免一个没有变化的task被反复构建，当task变化时候也只处理变化的部分，这样可以提高gradle的构建效率，缩短构建时间
    输入和输出
        编写task时，需要告诉gradle那些task属性时输入哪些是输出。如果task属性影响输出，务必将其注册成输入，否则当它不是时候task会被视为最新状态。相反如果属性不影响输出，不要注册为输入，否则task可能在不需要时执行。还要注意可能完全相同的输入生成不同输出的非确定性task，不应该配置为增量构建，因为UP-TO-DATE将不起作用
    增量构建的两种形式
        1 task完全可以复用，输入和输出都没有任何变化，即UP-TO-DATE
        2 部分变化，只需要针对变化的部分进行操作
    增量构建的原理
        首次执行task，gradle会获取输入的指纹，包括文件的路径和每个文件内容的散列。然后执行task，如果成功完成会获取输出的指纹，包含一组输出文件和每个文件的散列，gradle会在下次执行是保留两个指纹。
        后续每次执行前都会对输入和输出进行指纹识别
查找task
    查找task主要涉及TaskContainer，它是task容器的管理类，提供了两个方法
    findByPath(String path) 参数可空
        def aa = tasks.findByPath("aa").doFirst{ xxx }
        getByPath(String path) 参数可空，找不到会抛出异常UnknownTaskException
    同时TaskContainer继承自TaskCollection和NameDomainObjectCollection，又增加了两个方法
        findByName
            def aa = tasks.findByName("aa").doFirst{ xxx }
            // 找到名为aa的task，并增加一个doFirst Action
        getByName
    named 通过名称查找task
    tasks.named("aa"){}
    其他
        withType
            tasks.withType(DefaultTask).configureEach(task->{ xxx })
        each/forEach/configureEach
register和create
    通过register创建，只有在task被需要时候才会创建和配置（按需创建，这样gradle执行性能更好）
    通过create创建，会立即创建和配置该task并添加到TaskContainer中
Task Tree
    通过task-tree插件可以查看task的依赖关系
        plugins {
            id "com.dorongold.task-tree" version "2.1.1"
        }
        使用./gradlew build taskTree
