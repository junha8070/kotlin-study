# Java + Kotlin 공부용 프로젝트 (VS Code)

## 구조
- `src/main/java/study/` — Java 연습 파일
- `src/main/kotlin/study/` — Kotlin 연습 파일

## 실행
- **Java**: `.java` 파일의 `main` 위에 뜨는 **Run | Debug** 클릭 (또는 F5)
- **Kotlin**: `Cmd+Shift+B` (기본 빌드 작업 = `./gradlew run`)
  - 터미널에서 직접: `./gradlew run`
  - 다른 날 코드 실행: `./gradlew run -PmainClass=study.day01.Day01Kt`
    (`study/day01/Day01.kt` → `study.day01.Day01Kt`)

## 새 파일 만들기
- Java: `src/main/java/study/Xxx.java` 첫 줄 `package study;`
- Kotlin: 하루에 폴더 하나 `src/main/kotlin/study/dayNN/DayNN.kt`, 첫 줄 `package study.dayNN` + `fun main() { }`
  - 날마다 패키지를 나누는 이유: 같은 패키지에 `main`이나 `Device` 같은 이름이 겹치면 컴파일 에러가 나요
