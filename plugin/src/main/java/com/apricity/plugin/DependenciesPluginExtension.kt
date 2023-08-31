package com.apricity.plugin

import org.gradle.api.provider.Property

interface DependenciesPluginExtension {
    val enable:Property<Boolean>?
}