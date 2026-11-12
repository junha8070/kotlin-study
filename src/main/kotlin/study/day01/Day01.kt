package study.day01

/*
 * ============================================================================
 *  1일차 · Kotlin의 기본 재료  (2026-11-11)
 * ============================================================================
 *  배운 것
 *  - val / var, 기본 타입(Int, Long, Double, Boolean, String), 문자열 템플릿 "$name", "${식}"
 *  - if / when 은 "값을 돌려주는 식"이다
 *  - 함수, 한 줄 함수(=), 기본값 인자, 이름 붙인 인자
 *
 *  실행: ./gradlew run -PmainClass=study.day01.Day01Kt
 *        (VS Code에서는 main 위의 Run 버튼)
 * ============================================================================
 */


// ============================================================================
//  내가 쓴 코드 (숙제 원본 그대로)
// ============================================================================

// 문제 1. deviceName, ip, port를 만들고 "Galaxy Fold (192.168.0.12:8080)" 출력
fun problem1() {
    val deviceName = "Galaxy Fold"
    val ip = "192.168.0.12"
    val port = 8080

    println("${deviceName} (${ip}:${port})")
}

// 문제 2. 배터리 퍼센트에 따라 문구를 돌려주는 함수 (when 사용)
fun batteryStatus(percent: Int): String{
    val percent = percent;
    return when {
        percent >= 80 -> "충분"
        percent >= 20 -> "보통"
        percent >= 1 -> "부족 - 충전하세요"
        else -> "꺼짐"
    }
}

// 문제 3. startSharing(quality = "HD", withAudio = false)
fun startSharing(quality: String = "HD", withAudio: Boolean = false) {
    println("공유 시작: ${quality}, 소리 ${if(withAudio) "있음" else "없음"}")
}

// 문제 4. val count = 0; count = count + 1 이 에러인 이유
// -> val은 상수로 값 변경이 불가


// ============================================================================
//  개선한 코드 (선생님 피드백 반영)
// ============================================================================
object Better {

    // 문제 1: 변수 이름만 넣을 땐 {} 생략. ${}는 식(port + 1, user.name)을 넣을 때만
    fun problem1() {
        val deviceName = "Galaxy Fold"
        val ip = "192.168.0.12"
        val port = 8080
        println("$deviceName ($ip:$port)")
    }

    // 문제 2: 매개변수는 원래 val → 다시 담을 필요 없음, 세미콜론 X
    //         본문이 when 하나뿐이면 = 로 한 줄 함수
    fun batteryStatus(percent: Int) = when {
        percent >= 80 -> "충분"
        percent >= 20 -> "보통"
        percent >= 1  -> "부족 - 충전하세요"
        else          -> "꺼짐"
    }

    // 문제 3: 함수는 그대로 좋음 (문자열 안의 if식 👍)
    //         고칠 점은 "호출" 쪽 → main 참고
}


fun main() {
    println("===== 내 코드 =====")
    problem1()
    println(batteryStatus(0))
    startSharing("HD", true)

    println()
    println("===== 개선 코드 =====")
    Better.problem1()
    println(Better.batteryStatus(0))
    // 문제 조건: 기본값으로 한 번, withAudio만 바꿔서 한 번
    startSharing()                   // 공유 시작: HD, 소리 없음
    startSharing(withAudio = true)   // 공유 시작: HD, 소리 있음
}


/*
 * ============================================================================
 *  피드백 정리
 * ============================================================================
 *  문제 1 ✅  "${deviceName}" → "$deviceName" 로 충분. {}는 식을 넣을 때만.
 *
 *  문제 2 ✅  동작은 정답.
 *            - val percent = percent  → 필요 없음. 매개변수는 이미 val.
 *              같은 이름으로 다시 만들면 "Name shadowed" 경고가 뜸.
 *            - Kotlin은 세미콜론(;)을 안 씀.
 *            - println("${batteryStatus(0)}") → println(batteryStatus(0)). 이미 String.
 *
 *  문제 3 🔺  함수는 좋았는데 문제 조건(기본값 호출 1번 + withAudio = true 호출 1번)을 놓침.
 *            startSharing("HD", true) 는 true가 뭔지 코드만 보고 알 수 없음.
 *            startSharing(withAudio = true) 처럼 이름 붙인 인자를 쓰면 읽힘.
 *            → Compose 코드가 Text(text = ..., fontSize = ...) 모양인 이유.
 *
 *  문제 4 ✅  거의 정답. 단, val은 "상수"가 아니라 "다시 대입할 수 없는 변수(읽기 전용)".
 *            진짜 상수는 const val MAX_PORT = 65535 처럼 따로 있음.
 *
 * ----------------------------------------------------------------------------
 *  알아두면 좋은 것
 * ----------------------------------------------------------------------------
 *  - 기본은 val, 바꿀 필요가 생길 때만 var.
 *  - when 은 나중에 Compose에서 when (상태) { 찾는중 -> ..., 연결됨 -> ... } 로
 *    화면을 나누는 핵심 문법이 됨.
 *  - main을 매번 주석 처리하지 말고, main 하나에서 여러 함수를 호출하면 됨.
 *  - 에러 메시지를 읽는 것도 공부. 일부러 틀려보고 메시지를 읽어보기.
 * ============================================================================
 */
