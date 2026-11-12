package study.day02

/*
 * ============================================================================
 *  2일차 · Null 안전성, class, data class  (2026-11-12)
 * ============================================================================
 *  배운 것
 *  - String 과 String? 은 다른 타입. ? 가 붙어야 null을 넣을 수 있다
 *  - ?. (안전 호출), ?: (엘비스), if (x != null) 스마트 캐스트, !! (쓰지 말 것)
 *  - toIntOrNull() ?: 기본값  ← 사용자 입력 처리에 자주 씀
 *  - class: 속성 + 동작을 묶는 틀. 생성자 괄호 안 val/var 가 곧 속성
 *  - data class: toString / == (내용 비교) / copy() 자동 생성
 *
 *  실행: ./gradlew run -PmainClass=study.day02.Day02Kt
 * ============================================================================
 */


// ============================================================================
//  내가 쓴 코드 (숙제 원본 그대로)
// ============================================================================

// 문제 1. name이 null이면 "알 수 없는 기기" (한 줄로, ?: 사용)
fun displayName(name: String?): String {
    return name ?: "알 수 없는 기기"
}

// 문제 2. 포트 문자열을 숫자로, 실패하면 8080
fun parseAddress(ip: String, portText: String): String {
    return "$ip:${portText?.toIntOrNull() ?: 8080}"
}

// 문제 3. data class
data class Device(val name: String, val ip: String, val port: Int = 8080)

// 문제 4. 공유 세션
class ShareSession(val device: Device) {
    var isSharing: Boolean = false

    fun start(): Boolean {
        if (!isSharing) {
            isSharing = true
            println("${device.name} 공유 시작")
        }else {
            println("이미 공유 중이에요")
        }

        return isSharing
    }

    fun stop(): Boolean {
        if(isSharing){
            isSharing = false
            println("공유 종료")
        }

        return isSharing
    }
}

// 문제 5. val device: Device? = null 일 때
// (1) println(device?.name ?: "없음") → 답: 없음 출력
// (2) device!!.name                  → 답: crash 발생


// ============================================================================
//  개선한 코드 (선생님 피드백 반영)
// ============================================================================
object Better {

    // 문제 1: 한 줄 함수(=)로
    fun displayName(name: String?) = name ?: "알 수 없는 기기"

    // 문제 2: portText는 String(물음표 없음) → ?. 필요 없음
    //         ?: 는 toIntOrNull()이 null을 돌려줄 수 있어서 필요
    fun parseAddress(ip: String, portText: String) = "$ip:${portText.toIntOrNull() ?: 8080}"

    // 문제 4: 반환값 제거, private set, early return
    class ShareSession(val device: Device) {
        var isSharing = false
            private set          // 밖에서는 읽기만, 바꾸기는 클래스 안에서만

        fun start() {
            if (isSharing) {
                println("이미 공유 중이에요")
                return
            }
            isSharing = true
            println("${device.name} 공유 시작")
        }

        fun stop() {
            if (!isSharing) return
            isSharing = false
            println("공유 종료")
        }
    }
}


fun main() {
    println("===== 내 코드 =====")
    println(displayName(null))
    println(displayName("Galaxy Fold"))
    println(parseAddress("192.168.0.12", "9000"))
    println(parseAddress("192.168.0.12", "abc"))

    var device = Device("Galaxy Fold", "192.168.0.1", 8080)
    println(device)
    println(device == device.copy(name = "차량 디스플레이"))
    println(device.copy(name = "차량 디스플레이"))

    val session = ShareSession(Device("Galaxy Fold", "192.168.0.12"))
    session.start()
    session.start()
    session.stop()

    println()
    println("===== 개선 코드 =====")
    println(Better.displayName(null))
    println(Better.parseAddress("192.168.0.12", "abc"))

    // 문제 3: var → val, copy는 한 번 만들어서 이름 붙이기, port 기본값 생략
    val fold = Device("Galaxy Fold", "192.168.0.12")
    val car = fold.copy(name = "차량 디스플레이")
    println(fold)
    println(car)
    println(fold == car)   // false

    val betterSession = Better.ShareSession(fold)
    betterSession.start()
    betterSession.start()
    println("공유 중? ${betterSession.isSharing}")   // 읽기 OK
    // betterSession.isSharing = false              // ❌ private set
    betterSession.stop()
}


/*
 * ============================================================================
 *  피드백 정리
 * ============================================================================
 *  문제 1 ✅  "한 줄로" → fun displayName(name: String?) = name ?: "알 수 없는 기기"
 *
 *  문제 2 ✅  portText?. 의 ?. 는 불필요 (IDE 경고: Unnecessary safe call).
 *            ?. 는 String? 처럼 물음표 붙은 타입에만.
 *
 *  문제 3 🔺  - var device → val (다시 대입 안 하니까)
 *            - copy를 두 번 부르면 객체가 두 번 만들어짐 → 한 번 만들고 이름(car) 붙이기
 *            - port는 기본값 8080이라 생략 가능
 *
 *  문제 4 ✅  로직 완벽. 반환값(: Boolean)은 필요 없음 → isSharing을 밖에서 바로 읽으면 됨.
 *            + private set 으로 밖에서 못 바꾸게 막기
 *            + 안 되는 경우를 먼저 return 으로 걸러내면(early return) 중첩이 줄어듦
 *
 *  문제 5 ✅  정확한 용어: NullPointerException(NPE). 면접에서 쓰는 단어.
 *
 * ----------------------------------------------------------------------------
 *  내가 한 질문 정리
 * ----------------------------------------------------------------------------
 *  Q. fun start(): Boolean 에서 Boolean 대신 isSharing을 쓰면 안 되나요?
 *  A. 안 됨. : 뒤에는 "종류(타입)"만 옴. Boolean = 종류("과일"),
 *     isSharing = 그 종류의 구체적인 변수("내 책상 위 사과").
 *     무엇을 돌려줄지는 본문의 return isSharing 에서 정함.
 *     그리고 이 함수는 반환값 자체가 필요 없음 (속성을 밖에서 바로 읽으면 됨).
 *
 *  Q. println(device = device.copy(...)) 는 왜 안 되나요?
 *  A. 1) 함수 괄호 안의 "이름 = 값"은 이름 붙인 인자 문법.
 *        println의 매개변수 이름은 message라서 "device라는 매개변수 없음" 에러.
 *     2) Kotlin에서 대입(=)은 값을 만들지 않음 (Java/C와 다름, 실수 방지용).
 *     → val car = device.copy(...); println(car) 두 줄로.
 *
 *  Q. uiState = uiState.copy(isSearching = false) 에서 uiState는 var인가요?
 *  A. 네. 다시 대입하니까 var여야 함. Compose 화면 안에서는
 *     var uiState by remember { mutableStateOf(UiState()) } 모양으로 씀.
 *
 *  Q. copy가 새 객체를 만드는 거 아닌가요? val은 새 주소로 못 바꾸는데?
 *  A. 맞음. 핵심은 "새 주소를 어느 칸에 넣느냐".
 *
 *       private val _uiState = MutableStateFlow(DiscoverUiState())
 *       _uiState.value = _uiState.value.copy(isSearching = false)
 *
 *       _uiState (val) ──▶ 0x100 [상자]                     ← 주소 그대로 (val 지킴)
 *                                 value (var) ──▶ 0x300 [isSearching=false]  ← copy로 만든 새 객체
 *                                                 0x200 [isSearching=true]   ← 버려짐
 *
 *     - 변수의 val : 이름이 다른 객체로 못 갈아탐   (box = Box("포도") ❌)
 *     - 속성의 val : 객체 안의 그 값을 못 바꿈       (state.isSearching = false ❌)
 *     - val 변수가 가리키는 객체 안의 var 속성은 바꿀 수 있음 (box.value = "배" ✅)
 *
 * ----------------------------------------------------------------------------
 *  알아두면 좋은 것
 * ----------------------------------------------------------------------------
 *  - !! 는 쓰지 말기. 쓰고 싶어지면 ?: 나 if 로 처리할 방법을 찾기.
 *  - 상태 객체는 속성을 전부 val로 → 바꿀 땐 copy로 새 객체 → 통째로 교체.
 *    그래야 Compose / StateFlow가 "바뀌었다"를 알아챔. (2단계 상태관리의 핵심)
 *  - private set 과 private val _uiState / val uiState 패턴은 같은 목적:
 *    밖에서 상태를 마음대로 못 바꾸게 막기.
 * ============================================================================
 */
