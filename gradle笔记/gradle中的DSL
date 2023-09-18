gradle是一个构建工具，面向开发者的脚本语言是groovy和kotlin
---------------------------------
什么是DSL
    Domain Specific Language 特定领域语言，分为内部DSL和外部DSL
    外部DSL 也称独立DSL，他们是从零开始建立起来的语言，不基于任何现有宿主语言。构建工具make、语法分析器生成工具yacc、词法分析工具LEX都是常见的外部DSL
    内部DSL 也称嵌入式DSL，他们的实现是嵌入到宿主语言中与之合为一体。将一种现有的语言作为宿主语言，基于其设施建立专门面向特定领域的各种语义。如Groovy DSL、kotlin DSL等，这种DSL简化了语法结构，但是增加了理解成本
---------------------------------
gradle中的DSL
    groovy DSL
        groovy是Apache旗下一种强大的、可选类型和动态的语言，具有静态类型和静态编译功能，用于java平台，旨在通过简洁熟悉和易于学习的语法提高生产力。它和java程序顺利集成，并立即为应用程序提供强大的功能，包括脚本功能、特定领域语言创作、运行时和编辑时元编程和函数编程
    kotlin DSL
闭包 闭包的展现形式和kotlin中的lambda一样，是一段可执行的代码块或函数指针
    在groovy中是groovy.lang.Closure类的实例，可以赋值给变量或者作为参数传递
    语法： { [ closureParameters -> ] statements}
        闭包调用方式有两种 1 closure.call(param) 2 closure(param) 可以省略圆括号
            clos.call("Groovy Closure", "Params")
            clos("Groovy Closure22", "Params")
            clos"Groovy Closure33", "Params"
        函数的最后一个参数是闭包时候,可以写在参数括号外面;函数只有一个参数并且是闭包的时候,圆括号可以省略;没有参数可以省略->操作符
            GroovyClosure.displayId({"com.android.application"})
            GroovyClosure.displayId(){"com.android.application"}
            GroovyClosure.displayId{"com.android.application"}