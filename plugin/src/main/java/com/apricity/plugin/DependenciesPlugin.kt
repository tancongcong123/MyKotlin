package com.apricity.plugin
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin;
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.ModuleVersionIdentifier
import org.gradle.api.artifacts.ResolvedDependency

class DependenciesPlugin: Plugin<Project>{
    private val TAG = "DependenciesPlugin>>>>>";
    override fun apply(project: Project) {
        println("$TAG ${this.javaClass.name}")
        var extension = project.extensions.create("printDependencies", DependenciesPluginExtension::class.java)
        project.afterEvaluate { pro: Project->
            extension.enable?.convention(false)
            if (extension.enable!!.get()){
                println("$TAG 已开启依赖打印")
                var androidExtension:AppExtension = pro.extensions.getByType(AppExtension::class.java)
                androidExtension.applicationVariants.all{ it ->
                    println("$TAG applicationVariant name=${it.name}")
                    // 方式一：查看build.gradle文件中添加的依赖
                    var configuration: Configuration = pro.configurations.getByName(it.name+"CompileClasspath")
                    var allDependencies: DependencySet = configuration.allDependencies
                    for (dependency in allDependencies){
                        println("dependency==${dependency.group}:${dependency.name}:${dependency.version}")
                    }
                    // 方式二：所有依赖，包括依赖中的依赖
                    var androidLibs: MutableList<String> = ArrayList()
                    var otherLibs: MutableList<String> = ArrayList()
                    configuration.resolvedConfiguration.lenientConfiguration.allModuleDependencies.forEach{resolveDependency: ResolvedDependency ->
                        var identifier: ModuleVersionIdentifier = resolveDependency.module.id
                        if (identifier.group.contains("androidX")||identifier.group.contains("androidx")||identifier.group.contains("google")||identifier.group.contains("org.jetbrains")){
                            androidLibs.add("${identifier.group}:${identifier.name}:${identifier.version}")
                        }else{
                            otherLibs.add("${identifier.group}:${identifier.name}:${identifier.version}")
                        }
                    }
                    println("---------官方依赖-----------------")
                    androidLibs.forEach { println(it) }
                    println("---------官方依赖end-----------------")
                    println("---------三方依赖-----------------")
                    otherLibs.forEach { println(it) }
                    println("---------官方依赖end-----------------")
                }
            }else{
                println("$TAG 已关闭依赖打印")
            }
        }
    }
}