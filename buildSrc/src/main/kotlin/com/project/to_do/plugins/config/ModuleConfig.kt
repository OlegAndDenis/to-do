package com.project.to_do.plugins.config

import com.android.build.gradle.LibraryExtension
import com.project.to_do.dependency.helper.*
import com.project.to_do.libraryPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.module(
    appDependency: Dependency = EmptyDependency,
    plugins: Plugin = EmptyPlugins,
    androidLibraryConfiguration: (LibraryExtension.() -> Unit) = {}
) {
    addPlugins(libraryPlugin + plugins)
    applyDependency(appDependency)

    configure<LibraryExtension> {
        androidLibraryConfiguration()
    }
}