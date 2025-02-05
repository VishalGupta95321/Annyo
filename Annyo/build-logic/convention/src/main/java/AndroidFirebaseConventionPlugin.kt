import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFirebaseConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                val firebaseBom = libs.findLibrary("firebase-bom").get()
                add("implementation",platform(firebaseBom))
            }
        }
    }
}

