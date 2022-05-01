package com.project.picpicker

import com.project.picpicker.dependency.helper.ApplicationPlugin
import com.project.picpicker.dependency.helper.LibraryPlugin
import com.project.picpicker.dependency.helper.addAppPlug
import com.project.picpicker.dependency.helper.addLibPlug

val libraryPlugin : LibraryPlugin = addLibPlug(
    "com.android.library",
    "app-plugin"
)

val applicationPlugin : ApplicationPlugin = addAppPlug(
    "com.android.application",
    "app-plugin",
)