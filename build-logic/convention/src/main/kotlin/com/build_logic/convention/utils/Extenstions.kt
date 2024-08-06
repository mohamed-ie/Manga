package com.build_logic.convention.utils

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.library(libraryName: String) = libs.findLibrary(libraryName).get()

internal fun Project.version(key: String): String = extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")
    .findVersion(key)
    .get()
    .requiredVersion

internal fun Project.versionInt(key:String) = version(key).toInt()

internal fun DependencyHandlerDelegate.implementation(dependency: Any) {
    add("implementation", dependency)
}

internal fun DependencyHandlerDelegate.compileOnly(dependency: Any) {
    add("compileOnly", dependency)
}

internal fun DependencyHandlerDelegate.testImplementation(dependency: Any) {
    add("testImplementation", dependency)
}

internal fun DependencyHandlerDelegate.androidTestImplementation(dependency: Any) {
    add("androidTestImplementation", dependency)
}

internal fun DependencyHandlerDelegate.ksp(dependency: Any) {
    add("ksp", dependency)
}

internal fun DependencyHandlerDelegate.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}


fun KotlinMultiplatformExtension.sourceSets(configure: Action<org.gradle.api.NamedDomainObjectContainer<org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet>>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("sourceSets", configure)
