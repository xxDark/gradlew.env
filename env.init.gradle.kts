import org.gradle.internal.nativeintegration.ProcessEnvironment
import org.gradle.kotlin.dsl.support.serviceOf
import java.io.FileNotFoundException

rootProject {
    val processEnvironment = serviceOf<ProcessEnvironment>()
    val envFile = rootProject.file(".env")
    try {
        envFile.bufferedReader()
    } catch (_: FileNotFoundException) {
        return@rootProject
    }.useLines {
        it.forEach {
            if (it.isEmpty() || it[0] == '#') return@forEach
            val idx = it.indexOf('=')
            require(idx != -1)
            processEnvironment.setEnvironmentVariable(it.substring(0, idx), it.substring(idx + 1))
        }
    }
}
