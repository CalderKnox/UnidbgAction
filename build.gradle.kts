import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")

    // please see https://plugins.gradle.org/plugin/io.freefair.lombok, when
    // you want get new lombok version,
    id("io.freefair.lombok") version ("8.11")
}

group = "com.azwpayne"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // unidbg
    implementation("com.github.zhkl0228:unidbg-android:0.9.9")
    implementation(platform("commons-io:commons-io:+"))
    implementation("com.github.zhkl0228:unidbg-api:0.9.8")

    // unidbg backend
    implementation("com.github.zhkl0228:unidbg-dynarmic:0.9.9")
    implementation("com.github.zhkl0228:unidbg-hypervisor:0.9.8")
    implementation("com.github.zhkl0228:unidbg-kvm:0.9.8")
    implementation("com.github.zhkl0228:unidbg-unicorn2:0.9.8")

    // default
    testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.slf4j:slf4j-api:2.0.20")
    implementation("org.slf4j:slf4j-reload4j:2.0.16")

}

// junit-jupiter does not bring the JUnit Platform launcher, so Gradle supplies the one bundled with
// Gradle itself (1.8.2 in Gradle 8.10). That is older than the Jupiter engine on the test classpath,
// which is pulled up by the commons-io platform above, and test discovery then aborts with
// "OutputDirectoryCreator not available". Declaring the launcher without a version takes the version
// from junit-bom, i.e. the one that matches the engine.
dependencies {
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()

    // By default Gradle only says "There were failing tests" and leaves the details in
    // build/reports/tests/test, which only exists on the machine that ran the build.
    testLogging {
        events(
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED,
        )
        exceptionFormat = TestExceptionFormat.FULL
        showStandardStreams = true
    }
}