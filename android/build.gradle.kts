
import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/* ===== Repositories ===== */
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

/* ===== Custom build directory (Flutter-style) ===== */
val newBuildDir =
    rootProject.layout.buildDirectory
        .dir("../../build")
        .get()

rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    project.layout.buildDirectory.value(newBuildDir.dir(project.name))
    project.evaluationDependsOn(":app")

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    plugins.withId("com.android.library") {
        extensions.findByType<BaseExtension>()?.apply {
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}

/* ===== Clean task ===== */
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
