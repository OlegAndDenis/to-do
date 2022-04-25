val appName = "app"
val buildSrc = "buildSrc"
val modulesName = "modules"
val gradlePathEnd = "/build.gradle.kts"

autoInclude()

buildCache {
    local {
        directory = File(rootDir, "build-cache")
    }
}

fun Settings.autoInclude() {
    includeCore()
    includeModules()
}

fun Settings.includeCore() {
    rootDir.walkBottomUp()
        .maxDepth(1)
        .filter { it.absolutePath != rootDir.path && it.name != buildSrc }
        .mapToModules()
        .findNameAndInclude()
}

fun Settings.includeModules() {
    File(rootDir, modulesName)
        .walkTopDown()
        .findNameAndInclude()

}

fun Sequence<File>.findNameAndInclude() {
    filter { it.isDirectory && existDirectory(it) }
        .forEach {
            val moduleName = ":${it.name}"
            include(moduleName)
            project(moduleName).projectDir = file(it.path)
        }
}

fun Sequence<File>.mapToModules(): Sequence<File> {
    filter(::isModule)
        .forEach {
            it.moveTo()
        }
    return this
}

fun File.moveTo() {
    replaceDefaultGradle(File("$rootDir/$name$gradlePathEnd"))
    renameTo(File("$rootDir/$modulesName/$name"))
    val settings = File("$rootDir/settings.gradle.kts")
    var text = settings.inputStream().bufferedReader().use { it.readText() }
    val findText = "include(\":$name\")"
    if (text.contains(findText)) {
        text = text.substring(0, text.length - findText.length - 2)
    }
    settings.bufferedWriter().use { it.write(text) }
}

fun replaceDefaultGradle(file: File) {
    val text = "plugins {\n" +
            "    id(\"com.android.library\")\n" +
            "    id(\"app-plugin\")\n" +
            "}"
    file.bufferedWriter().use { it.write(text) }
}

fun isModule(file: File): Boolean {
    return file.name != appName && file.isDirectory
            && existDirectory(file) && File("${file.absolutePath}/libs").exists()
}

fun existDirectory(file: File): Boolean =
    File("${file.absolutePath}$gradlePathEnd").exists()