import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.date
import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.3.20-Beta2"
    id("org.jetbrains.intellij.platform") version "2.11.0"
    id("org.jetbrains.changelog") version "2.5.0"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create(properties("platformType"), properties("platformVersion"))
        pluginVerifier()
    }
}

changelog {
    version.set(properties("pluginVersion"))
    header.set(provider {"[${version.get()}] - ${date()}"})
    path.set("${project.projectDir}/CHANGELOG.md")
    groups.set(listOf("Added","Updated","Changed","Fixed","Removed","Misc"))
    title.set("Darkula Changelog")
}

val pluginDescription = """
    <div>
      <p>A darker Dracula Theme for JetBrains.</p>
      <p>Combines the <a href="https://github.com/dracula/jetbrains">Dracula</a> classic theme with <a href="https://github.com/OlyaB/DarkPurpleTheme">DarkPurpleTheme</a>.</p>
      <img src="https://github.com/ZimCodes/darkula/blob/main/docs/project.png?raw=true" alt="Preview of Darkula theme" width="450">
      <h2>License</h2>
      <p>
        <a href="https://raw.githubusercontent.com/ZimCodes/darkula/refs/heads/master/LICENSE">
          MIT License
        </a>
      </p>
    </div>
""".trimIndent()

intellijPlatform {
    buildSearchableOptions = false
    pluginConfiguration {
        name = properties("pluginName")
        version = properties("pluginVersion")
        description = pluginDescription
        changeNotes = provider {
            changelog.renderItem(changelog.getLatest(), Changelog.OutputType.HTML)
        }
        ideaVersion {
            untilBuild = provider { null }
        }
    }
    publishing {
        token = System.getProperty("jetbrains.token")
    }
    pluginVerification {
        ides {
            recommended()
            select {
                types = listOf(IntelliJPlatformType.IntellijIdeaCommunity, IntelliJPlatformType.IntellijIdeaUltimate)
                channels = listOf(ProductRelease.Channel.RELEASE)
                sinceBuild = "241"
                untilBuild = "242.*"
            }
        }
        failureLevel = listOf(
            VerifyPluginTask.FailureLevel.COMPATIBILITY_PROBLEMS, VerifyPluginTask.FailureLevel.INVALID_PLUGIN
        )
    }
}
