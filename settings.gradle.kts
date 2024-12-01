rootProject.name = "advent-of-code-2024"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

val kotestVersion = "5.8.0"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("kotest-runner-junit5", "io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
            library("kotest-assertions-core", "io.kotest:kotest-assertions-core-jvm:$kotestVersion")

        }
    }
}