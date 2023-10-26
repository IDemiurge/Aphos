plugins {
    id("java")
}

group = "org.wyrmring"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
    }
    test {
        java {
            srcDirs ("src/test/java")
        }
    }
}
dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    implementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.yaml:snakeyaml:1.8")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events ("passed", "skipped", "failed")
        showStandardStreams = true
    }
}