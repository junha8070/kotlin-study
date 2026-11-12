plugins {
    kotlin("jvm") version "2.2.21"
    application
}

group = "study"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

application {
    // ./gradlew run 으로 실행할 메인 (기본값 = 가장 최근 공부한 날. study/dayNN/DayNN.kt -> study.dayNN.DayNNKt)
    mainClass.set(providers.gradleProperty("mainClass").orElse("study.day02.Day02Kt"))
}

tasks.test {
    useJUnitPlatform()
}
