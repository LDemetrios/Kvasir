package org.ldemetrios

import com.intellij.ide.plugins.DynamicPlugins
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import com.sun.jna.Platform
import org.ldemetrios.tyko.ffi.TypstSharedLibrary
import org.ldemetrios.utilities.cast
import java.io.File
import kotlin.io.path.Path

private val osName = System.getProperty("os.name")
private val archName = System.getProperty("os.arch")
private val folder = run {
    when (Platform.getOSType()) {
        Platform.ANDROID -> "android"
        Platform.LINUX -> "linux"
        Platform.AIX -> "aix"
        Platform.MAC -> if (osName.startsWith("Darwin")) "darwin" else "mac"
        Platform.WINDOWS -> "windows"
        Platform.SOLARIS -> "solaris"
        Platform.FREEBSD -> "freebsd"
        Platform.OPENBSD -> "openbsd"
        Platform.GNU -> "gnu"
        Platform.NETBSD -> "netbsd"
        else -> "unknown"
    } + "-" + Platform.ARCH
}

//val instance = TypstSharedLibrary.instance(Path("/home/ldemetrios/Workspace/TypstNKotlinInterop/typst-custom-serialize/target/release/libtypst_shared.so"))
val instance = run {
    try {
        TypstSharedLibrary.instance(Path(folder, "libtypst_shared.so"))
    } catch (e: UnsatisfiedLinkError) {
        val name = System.mapLibraryName("typst_shared");
        val path = System.getenv("PATH")

        path?.split(File.pathSeparator)
            ?.asSequence()
            ?.filter { it.isNotBlank() }
            ?.map { Path(it) }
            ?.map { it.resolve(name) }
            ?.mapNotNull {
                try {
                    TypstSharedLibrary.instance(it)
                } catch (e: UnsatisfiedLinkError) {
                    null
                }
            }
            ?.firstOrNull()
    }
}
//val instance = Native.load("typst_shared", TypstSharedLibrary::class.java)!!
//val x = println("PATH: ${System.getenv("PATH")};")

fun askForShutdown(project: Project) {
    val errorMessage = """
        Fatal error occurred in Kvasir: shared library not found. Probably, your architecture and OS combination is not usual.
        (OS: $osName, Arch: $archName, inferred target: $folder). 
        Please, follow the instructions about additional setup at [link].
        If:
         - Your arch+OS pair is listed as supported in the readme, or
         - You continue to get this error message even after additional setup,
        then report [here].

        Kvasir will now be unloaded. Click "OK" to proceed.
        """.trimIndent()

    Messages.showErrorDialog(errorMessage, "Fatal Error in Kvasir")

    val pluginId = PluginId.getId("org.ldemetrios.kvasir")
    PluginManagerCore.disablePlugin(pluginId)
    val pluginDescriptor = PluginManagerCore.getPlugin(pluginId)

    if (pluginDescriptor != null && DynamicPlugins.allowLoadUnloadWithoutRestart(pluginDescriptor.cast())) {
        DynamicPlugins.unloadPlugin(pluginDescriptor.cast())
    } else {
        Messages.showWarningDialog(
            "A restart is required to fully unload the plugin.",
            "Plugin Unload Warning"
        )
    }
}