# 프로젝트 코드 리뷰

## 목차

1. [메인페이지 배너 이미지 자동 슬라이드](#메인페이지-배너-자동-슬라이드)
2. [최근 검색어](#최근-검색어)
3. [주변장소 찾기 api 코드 전체적인 흐름](#주변장소-찾기)
4. [인기 검색어](#인기-검색어)
5. [회원 여행 목록 ajax를 사용해서 조건에 맞는 여행 출력](#회원-여행-목록-ajax를-사용해서-조건에-맞는-여행-출력)
6. [아이디 찾기](#아이디-찾기)
7. 평균 별점
8. 리뷰 글 개수

<br>

## 메인페이지 배너 자동 슬라이드

### swiper.js 라이브러리 사용
[사용법 참고](https://m.blog.naver.com/anedthh/222014406404)


### HTML 코드
```HTML
<!-- main.jsp -->
<div class="swiper-container">
    <div class="swiper-wrapper">
        <div class="swiper-slide"><img src="/resources/images/main-image1.png" alt="메인 이미지"></div>
        <div class="swiper-slide"><img src="/resources/images/main-image2.png" alt="메인 이미지"></div>
        <div class="swiper-slide"><img src="/resources/images/main-image3.png" alt="메인 이미지"></div>
        <div class="swiper-slide"><img src="/resources/images/main-image4.png" alt="메인 이미지"></div>
        <div class="swiper-slide"><img src="/resources/images/main-image5.png" alt="메인 이미지"></div>
    </div>
</div>
```

### javascript 코드
```javascript
let mySwiper = new Swiper(".swiper-container", {
    autoplay: true,                 // 자동 슬라이드
    loop: true                      // 이미지 반복
});
```

<br>

## 최근 검색어

### LocalStorage란?
- 쿠키와 비슷하게 웹사이트를 방문하는 유저들에 대한 정보를 저장하는 방법
- 쿠키의 최대 용량 `4KB` 로컬스토리지 최대 용량 `5MB` 
- 쿠키와 달리 `HTTP` 요청에서 데이터를 주고 받지 않으므로 트래픽 낭비 방지
- 문자열만 저장할 수 있는 쿠키와 달리 javascript의 `primitives`와 `object` 저장 가능
```javascript
// 값 저장
localStorage.setItem("key", "value");

// 값 반환
localStorage.getItem("key");
```

### 검색어 저장 코드

<details>
<summary>접기/펼치기</summary>
<div markdown="1">

```javascript
// 최근검색어 생성자
function RecentKeyword(keyword, contentTypeId, areaCode) {
    this.keyword = keyword;
    this.contentTypeId = contentTypeId;
    this.areaCode = areaCode;
}

// 로컬스토리지에서 recentKeyword로 저장된 value를 반환
let recentKeyword = localStorage.getItem("recentKeyword");
// 임시로 배열을 저장할 변수 선언
let recentKeywordArr;

if(recentKeyword == null) {         // 저장된 최근검색어가 존재하지 않으면
    recentKeywordArr = [];          // 최근검색어 객체배열 생성
    
} else {                            // 저장된 최근검색어가 존재하면       
    recentKeywordArr = JSON.parse(RecentKeyword);   // json -> 객체배열 파싱
    if(recentKeywordArr.length >= 10) {             // 저장된 최근검색어가 10개보다 많다면
        recentKeywordArr.shift();                   // 맨 앞에(0번 인덱스)항목 제거
    }
}
// 검색어 객체 생성자를 이용하여 객체 생성과 동시에 최근검색어배열에 추가
recentKeywordArr.push(new recentKeyword(
    keywordInput.value.trim(),                                              // 검색창의 입력값
    document.querySelector("input[name='contentTypeId']:checked").value,    // 검색 카테고리 입력 값 
    document.getElementById("areaCode").value                               // 검색 지역코드 입력 값
));

// 객체를 json형태로 변환하여 localStorage에 저장
localStorage.setItem("recentKeyword", JSON.stringify(recentKeywordArr));
```
</div>
</details>


### 검색어 출력 코드
<details>
<summary>접기/펼치기</summary>
<div markdown="1">

```javascript
// 로컬스토리지에서 recentKeyword로 저장된 value를 반환
let recentKeywordArr = localStorage.getItem("recentKeyword");

// 비어있지 않으면
if(recentKeywordArr != null) {
    // 배열로 파싱
    recentKeywordArr = JSON.parse(recentKeywordArr);
    
    for(let keyword of recentKeywordArr) {
        // HTML 조립코드...
    }
}
```
</div>
</details>



<br>

## 주변장소 찾기

### 사용 기술

- [Geolocation](https://www.zerocho.com/category/HTML&DOM/post/59155228a22a5d001827ea5d)
- [RestAPI - TourAPI4.0](https://api.visitkorea.or.kr/#/useKoreaGuide)
<details>
<summary>TourAPI 응답 폼</summary>
<div markdown="1">

![TourAPI 응답 폼](/TourAPI-response.png)
</div>
</details>

### 코드 흐름
1. 현재 접속위치 찾기 (위도 경도 좌표 반환)
    <details>
    <summary>접기/펼치기</summary>
    <div markdown="1">

    ```javascript
    javascript
    let latitude;  // 위도
    let longitude;  // 경도
    if(navigator.geolocation) {     // GPS 사용가능하면
        navigator.geolocation.getCurrentPosition(position => {
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;
            
            $.ajax({
                url: "/location/searchPlace",
                data: {
                    "latitude": latitude,
                    "longitude": longitude,
                    "contentTypeId" : "12"
                },
                type: "GET",
                success: result => {
                    console.log(result);
                    createPlaceList(result, "주변 관광지");
                },
                error: () =>{
                    console.log("error");
                }
            });
            $.ajax({
                url: "/location/searchPlace",
                data: {
                    "latitude": latitude,
                    "longitude": longitude,
                    "contentTypeId" : "39"
                },
                type: "GET",
                success: result => {
                    console.log(result);
                    createPlaceList(result, "주변 음식점");
                },
                error: () =>{
                    console.log("error");
                }
            });
        }, error => {
            console.log(error);
            return;
        }, {
            enableHighAccuracy: false,
            maximumAge: 0,
            timeout: Infinity
        });
    } else {
        alert("GPS를 지원하지 않습니다.");
    }
    ```
    </div>
    </details>

    

2. 접속위치를 인자로 API 요청
    <details>
    <summary>접기/펼치기</summary>
    <div markdown="1">
    
    1. 컨트롤러
  
        ```JAVA
        @Controller
        @RequestMapping("/location")
        public class LocationController {
            @Autowired
            private LocationService service;
            
            @ResponseBody
            @GetMapping("/searchPlace")
            public List<Place> serachPlace(String latitude, String longitude, String contentTypeId) throws Exception{
                List<Place> placeList = service.searchPlace(latitude, longitude, contentTypeId);
                
                return placeList;
            }
        }
        ```
    2. 서비스
  
        ```Java
        @Service
        public class LocationServiceImpl implements LocationService {
            
            @Autowired
            private LocationAPI api;
            
            @Autowired
            private ReviewService rService;
            
            @Override
            public List<Place> searchPlace(String latitude, String longitude, String contentTypeId) throws Exception {
                
                // 파라미터 맵
                Map<String, String> paramMap = new HashMap<>();
                
                // API에 요청할 queryString에 필요한 파라미터들을 세팅
                paramMap.put("mapX", longitude);
                paramMap.put("mapY", latitude);
                paramMap.put("radius", "3000");
                paramMap.put("numOfRows", "10");
                paramMap.put("contentTypeId", contentTypeId);
                
                // api 요청 메서드 호출
                List<Place> placeList = api.serachPlace(paramMap);
                return placeList;
            }
        }
        ```
    3. api(DAO)

        ```Java
        @Component
        public class LocationAPI {
            // API 엔드포인트
            private final String HOST = "http://apis.data.go.kr/B551011/KorService/locationBasedList";
            // 요청 필수 파라미터
            private final String ESSENTIAL_PARAM = "?MobileOS=ETC&MobileApp=AppTest&_type=json&serviceKey=";
            // 발급받은 키
            private String key = "e+nonJ082FY6zfX+tup0hvcGTRAqHZV2OGGnVkjpa+zYdVpUYTHuuqfHYuIEzFwYXjbQXAhQa9tTuyiYdd0Eyw==";
            
            @Autowired
            private ReviewService rService;
            
            // 생성자
            public LocationAPI() throws Exception{
                // key를 인코딩
                this.key = URLEncoder.encode(key, "UTF-8");
            }
            
            /** 위치기반 관광정보 조회
            * @param paramMap
            * @return placeList
            */
            public List<Place> serachPlace(Map<String, String> paramMap) throws Exception {
                // 결과 반환 위한 변수
                List<Place> placeList = null;
                
                // 매개변수로 전달받은 파라미터를 사용하여 QueryString 생성
                String param = Util.createQueryString(paramMap);
                
                // 요청할 URL객체 생성
                URL url = new URL(HOST + ESSENTIAL_PARAM + key + param);
                
                // 커넥션 열기
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                // 요청 방식 설정
                conn.setRequestMethod("GET");
                
                // 응답내용을 담을 객체
                StringBuilder response = new StringBuilder();
                
                // 한줄씩 읽어올 문자열변수
                String readline = "";
                
                // 커넥션에서 스트림 얻어오기
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                
                // 읽어온 내용이 null 일때까지 한줄 씩 읽어옴
                while((readline = br.readLine()) != null) {
                    response.append(readline);	// 한줄씩 response객체에 추가
                }
                br.close(); 				// 스트림객체 사용 후 자원 반환(APIConnection 반환)
                
                placeList = Util.jsonToPlaceList(response.toString());
                rService.connectReview(placeList);
                
                return placeList;
            }
        }
        ```
    - 사용한 Util 함수
  
        ```Java
        public static String createQueryString(Map<String, String> paramMap) {
            // 문자열 연산을 위한 StringBuilder 객체 생성
            StringBuilder sb = new StringBuilder();

            // Map에서 Entry단위로 하나씩 접근해 &key값=value값 형태로 문자열 연산 진행
            for (Entry<String, String> e : paramMap.entrySet()) {
                sb.append("&").append(e.getKey()).append("=").append(e.getValue());
            }

            // Builder에 저장된 값들을 문자열로 합쳐 반환
            return sb.toString();
	    }

        public static List<Place> jsonToPlaceList(String json) throws Exception {
            // 반환할 값을 담을 변수
		    List<Place> placeList = null;

            // API응답 문자열에서 원하는 부분의 값만 파싱해서 문자열로 반환
            String items = new JSONObject(json).getJSONObject("response").getJSONObject("body").get("items").toString();

            // 응답값이 있다면
            if (!items.equals("")) {
                // 배열형식으로 데이터를 꺼내
                String item = new JSONObject(items).getJSONArray("item").toString();

                
                // json을 한번에 Java객체로 파싱하기 위한 jackson라이브러리의 ObjectMapper 생성
                ObjectMapper om = new ObjectMapper();
                
                // API응답에 필요없는 값은 버리기위한 설정 코드
                om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                // JSON을 Java객체로 파싱하는 코드
                placeList = om.readValue(item, new TypeReference<List<Place>>() {
                });
            }
            return placeList;
        }
        ```
    </div>
    </details>
    
    
<br>

## 인기 검색어

### 코드 흐름
1. 검색 시 인터셉터 호출
```XML
<interceptors>
    <interceptor>
        <!-- beans라는 태그안에서 bean을 만들기 위한 태그 -->
        <mapping path="/search"/>   <!-- 검색 컨트롤러 -->
        <beans:bean id="searchInterceptor" class="kh.team.travelcompass.common.interceptor.SearchInterceptor" />
    </interceptor>
</interceptors>
```

2. 인터셉터 내부에서 검색어 횟수 축적
    <details>
    <summary>접기/펼치기</summary>
    <div markdown="1">

    ```Java
    public class SearchInterceptor implements HandlerInterceptor{

        // 스프링을 통해 ServletContext 의존성 주입
        @Autowired
        ServletContext application;
        
        // 검색할 때 사용한 키워드에 반환되는 결과가 있을 때만 데이터 축적을 하기 위해 postHandle 오버라이드
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                ModelAndView modelAndView) throws Exception {
            
            // 컨트롤러에서 로직 수행 후 응답한 Model 객체를 인터셉터에서 가져옴
            Map<String, Object> model = modelAndView.getModel();
            if(model == null) {	// 응답이 잘못되면 리턴
                return;
            }

            // 검색 결과가 있는지 확인 (API 응답폼 참고)
            int totalCount = (int)((Map<String, Object>) modelAndView.getModel().get("placeMap")).get("totalCount");

            if(totalCount > 0) {	// 검색결과가 있으면 (유효한 키워드면)

                // 스케줄러에서 정렬할 때 Map의 순서를 기억하기 위해 LinkedHashMap 자료구조 사용
                // 어플리케이션 스코프에서 keywordMap으로 저장된 데이터 반환받기                

                /*  검색어 저장 구조
                    LinkedHashMap<검색어, 검색된 횟수>
                 */
                LinkedHashMap<String, Integer> keywordMap = (LinkedHashMap<String, Integer>) application.getAttribute("keywordMap");

                if(keywordMap == null ) {	// 어플리케이션 스코프에 popularKeywordMap키값으로 저장된 map 이없으면
                    keywordMap = new LinkedHashMap<String, Integer>(); // 생성
                    application.setAttribute("keywordMap", keywordMap); // 추가
                }
                
                // keyword 가져오기
                String keyword = request.getParameter("keyword");
                
                // 1. 쿠키를 가져와서 중복확인
                // 모든 쿠키 가져오기
                Cookie[] cookies = request.getCookies();	
                Cookie c = null;		// searchKeyword 이름의 쿠키가 있으면 저장할 변수
                
                // 쿠키가 하나라도 있으면
                if(cookies != null) {
                    for(Cookie temp : cookies) {		// 모든 쿠키에 접근하여
                        if(temp.getName().equals("searchKeyword")) {		// 쿠키 이름이 searchKeyword인 쿠키 찾기
                            c = temp;
                            break;
                        }
                    }
                }
                if(c != null) {												// searchKeyword 쿠키를 찾았으면
                    // 키워드가 쿠키value중에 있는지 확인
                    // 없으면 coocke에 keyword 추가
                    if(c.getValue().indexOf("|" + keyword + "|") == -1) {
                        c.setValue(c.getValue() + "|" + keyword + "|");
                        
                        // popularKeywordMap에 현재검색한 키워드가 key값으로 이미 존재하면 value + 1, 존재하지 않으면 1
                        keywordMap.put(keyword, keywordMap.get(keyword) == null ? 1 : keywordMap.get(keyword) + 1);
                    }
                    // 있으면 추가 x
                    
                } else {													// 못찾았으면
                    // 새로운 쿠키 생성 후 값 저장 |keyword|
                    c = new Cookie("searchKeyword", "|" + keyword + "|");
                    keywordMap.put(keyword, keywordMap.get(keyword) == null ? 1 : keywordMap.get(keyword) + 1);
                }
                
                c.setPath("/");
                
                // 오늘 23시 59분 59초 까지 남은 시간을 초단위로 구하기
                
                Date a = new Date();
                Calendar cal = Calendar.getInstance();
                
                cal.add(cal.DATE, 1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date temp = new Date(cal.getTimeInMillis());
                
                Date b = sdf.parse(sdf.format(temp));
                
                long diff = b.getTime() - a.getTime();
                
                c.setMaxAge((int)diff/1000);
                response.addCookie(c);
            } // 검색결과가 없으면 아무것도 하지 않음
            
            HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        }
    }
    ```
    </div>
    </details>


3. 스케줄러로 일정 주기마다 검색어 정렬

    - [Collection.sort 메서드 사용](https://wjheo.tistory.com/entry/Java-%EC%A0%95%EB%A0%AC%EB%B0%A9%EB%B2%95-Collectionssort)
    - [Java 람다 익명함수 사용](https://mangkyu.tistory.com/113) 
        ```Java
        // 기존 자바 코드
        public String hello() {
            return "Hello World!";
        }
        
        // 람다식
        () -> "Hello World!";
        ```
    <br>
  
    <details>
    <summary>접기/펼치기</summary>
    <div markdown="1">

    ```Java
    @Component
    public class PopularKeywordScheduling {
        
        @Autowired
        ServletContext application;
        
        @Scheduled(fixedRate = 1000 * 60)		// 1분마다
        public void updatePopularKeyword() {
            // application scope에 저장된 맵 가져오기
            LinkedHashMap<String, Integer> keywordMap = (LinkedHashMap<String, Integer>) application.getAttribute("keywordMap"); 
            // 맵이 null이 아니면
            if(keywordMap != null) {
                // 리스트에 entry 단위로 옮겨 담고
                List<Entry<String, Integer>> tempList = new ArrayList<>(keywordMap.entrySet());	
                
                // 리스트를 Entry의 value 내림차순으로 정렬
                // Collections.sort(정렬할 컬렉션 객체, 정렬 방법(람다 익명함수 사용))

                Collections.sort(tempList, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
                
                /* Integer 클래스에 내장되어있는 compareTo, compare 메서드 (tempList에 저장되어있는 entry의 value가 Integer이기 때문에 사용 가능)

                    public int compareTo(Integer anotherInteger) {
                        return compare(this.value, anotherInteger.value);
                    }
                    
                    public static int compare(int x, int y) {
                        return (x < y) ? -1 : ((x == y) ? 0 : 1);
                    }
                */

                // 정렬 완료한 리스트를 다시 map으로 변경
                keywordMap = new LinkedHashMap<String, Integer>();
                // 맵 정렬
                for(Entry<String, Integer> e : tempList) {
                    keywordMap.put(e.getKey(), e.getValue());
                }
                // 맵 추가 끝 -----------------------------------
                
                // 화면에 보낼 키워드 10개만 잘라 저장
                // 이미 value(검색된 횟수)의 내림차순으로 정렬되어 있으므로 key만 가져와 리스트를 만듬
                List<String> keywordList = new ArrayList<>(keywordMap.keySet());
                
                // 검색어 리스트의 길이가 10보다 길 경우
                if(keywordList.size() > 10) {
                    // 0 ~ 10 번째 인덱스만 잘라 저장
                    keywordList = keywordList.subList(0, 10);
                }
                
                // 화면에 출력할 용도의 데이터를 application scope에 저장
                application.setAttribute("popularKeyword", keywordList);
            }
        }
        
        @Scheduled(cron="0 59 * * * *")
        public void initPoPopularKeyword() {
            // 맵 가져오기
            LinkedHashMap<String, Integer> keywordMap = (LinkedHashMap<String, Integer>) application.getAttribute("keywordMap");
            // 맵 초기화
            keywordMap = new LinkedHashMap<String, Integer>();
            application.setAttribute("keywordMap", keywordMap);
            
            // 초기화된 Map을 application scope에 저장하여 인기 검색어 초기화
            keywordMap = (LinkedHashMap<String, Integer>) application.getAttribute("keywordMap");
        }
    }
    ```
    </div>
    </details>

<br>

## 회원 여행 목록 ajax를 사용해서 조건에 맞는 여행 출력

### HTML
<details>
<summary>접기/펼치기</summary>
<div markdown="1">

```HTML
<ul class="travel-nav">
    <li>
        <input type="radio" id="totalTravel" name="travelCategory" checked value="2">
        <label for="totalTravel" class="travel-scope">
            <span>모든 여행</span>
        </label>
    </li>
    <li>
        <input type="radio" id="privateTravel" name="travelCategory" value="1">
        <label for="privateTravel" class="travel-scope">
            <span>여행(비공개)</span>
        </label>
    </li>
    <li>
        <input type="radio" id="publicTravel" name="travelCategory" value="0">
        <label for="publicTravel" class="travel-scope">
            <span>여행(공개)</span>
        </label>
    </li>
    <li>
        <input type="radio" id="scrapTravel" name="travelCategory" value="-1">
        <label for="scrapTravel" class="travel-scope">
            <span>스크랩한 여행</span>
        </label>
    </li>
</ul>
```
</div>
</details>


### Javascript
<details>
<summary>접기/펼치기</summary>
<div markdown="1">

```javascript
// travel-category 이벤트 달기
(()=>{
    const categoryArr = document.getElementsByClassName("travel-scope");
    if(categoryArr != null) {
        for(let category of categoryArr) {
            category.addEventListener("click", e=>{
                const privateFlag = e.currentTarget.previousElementSibling.value;
                createTravelList(privateFlag);
            });
        }
    }
})();

// createTravelList 구현부
function createTravelList(privateFlag) {
    console.log(document.querySelector("input[name='travelCategory']:checked").value);
    $.ajax({
        url: "/travel/select",
        data: {
            "memberNo": hostNo,
            "privateFlag": privateFlag
        },
        success: result => {
            // HTML 조립코드
        }
    });
}
```
</div>
</details>

### JAVA
<details>
<summary>접기/펼치기</summary>
<div markdown="1">

1. 컨트롤러

    ```Java
    /** 비동기 여행 목록 조회
    * @param memberNo
    * @return travelList
    */
    @ResponseBody
    @GetMapping("/select")
    public List<Travel> selectTravelList(int memberNo, int privateFlag) {
        // 결과 반환할 변수 선언
        List<Travel> travelList = null; 

        // 공개 범위에 따른 서비스 조회
        if(privateFlag == -1) {
            travelList = service.selectTravelScrapList(memberNo);
        } else {
            Map<String, Integer> paramMap = new HashMap<>();
            paramMap.put("memberNo", memberNo);
            paramMap.put("privateFlag", privateFlag);
            System.out.println(privateFlag);
            travelList = service.selectTravelList(paramMap);			
        }
        return travelList;
    }
    ```

2. SQL

    ```XML
    <!-- 특정회원 여행목록 조회 -->
    <select id="selectTravelList" parameterType="map" resultMap="travel_rm">
		SELECT T.TRAVEL_NO, T.TRAVEL_TITLE, T.PRIVATE_FL, T.MEMBER_NO,
			TO_CHAR(T.TRAVEL_DATE, 'YYYY"년" MM"월" DD"일"') TRAVEL_DATE,
			(SELECT PS.FIRST_IMAGE FROM "PLACE_SCRAP" PS
				WHERE PS.PLACE_SCRAP_NO = 
					(SELECT TL.PLACE_SCRAP_NO  
					FROM "TRAVEL_LIST" TL
					WHERE TRAVEL_LIST_ORDER = (SELECT MIN(TRAVEL_LIST_ORDER) FROM "TRAVEL_LIST" WHERE TL.TRAVEL_NO = T.TRAVEL_NO))
					) TRAVEL_FIRST_IMAGE,
			(SELECT COUNT(*) FROM "TRAVEL_LIST" TL WHERE TL.TRAVEL_NO = T.TRAVEL_NO) TRAVEL_PLACE_COUNT,
			(SELECT MEMBER_NICKNAME FROM "MEMBER" M WHERE M.MEMBER_NO = T.MEMBER_NO) MEMBER_NICKNAME,
			(SELECT PROFILE_IMAGE FROM "MEMBER" M WHERE M.MEMBER_NO = T.MEMBER_NO) PROFILE_IMAGE,
			(SELECT COUNT(*) FROM "TRAVEL_LIKE" WHERE TRAVEL_NO = T.TRAVEL_NO) TRAVEL_LIKE_COUNT
		FROM TRAVEL T
		WHERE MEMBER_NO = #{memberNo}
		AND T.TRAVEL_DEL_FL = 'N'		
		<if test="privateFlag == 0">		<!-- 공개만 -->
			AND T.PRIVATE_FL = 'N'
		</if>
		<if test="privateFlag == 1">		<!-- 비공개만 -->
			AND T.PRIVATE_FL = 'Y'
		</if>
	</select>

    <!-- 특정 회원이 스크랩한 여행 목록 조회 -->
	<select id="selectTravelScrapList" resultMap="travel_rm">
		SELECT T.TRAVEL_NO, T.TRAVEL_TITLE, T.PRIVATE_FL, T.MEMBER_NO,
			TO_CHAR(T.TRAVEL_DATE, 'YYYY"년" MM"월" DD"일"') TRAVEL_DATE,
			(SELECT PS.FIRST_IMAGE FROM "PLACE_SCRAP" PS
				WHERE PS.PLACE_SCRAP_NO = 
					(SELECT TL.PLACE_SCRAP_NO  
					FROM "TRAVEL_LIST" TL
					WHERE TRAVEL_LIST_ORDER = (SELECT MIN(TRAVEL_LIST_ORDER) FROM "TRAVEL_LIST" WHERE TL.TRAVEL_NO = T.TRAVEL_NO))
					) TRAVEL_FIRST_IMAGE,
			(SELECT COUNT(*) FROM "TRAVEL_LIST" TL WHERE TL.TRAVEL_NO = T.TRAVEL_NO) TRAVEL_PLACE_COUNT,
			(SELECT MEMBER_NICKNAME FROM "MEMBER" M WHERE M.MEMBER_NO = T.MEMBER_NO) MEMBER_NICKNAME,
			(SELECT PROFILE_IMAGE FROM "MEMBER" M WHERE M.MEMBER_NO = T.MEMBER_NO) PROFILE_IMAGE,
			(SELECT COUNT(*) FROM "TRAVEL_LIKE" WHERE TRAVEL_NO = T.TRAVEL_NO) TRAVEL_LIKE_COUNT
		FROM TRAVEL T
		JOIN TRAVEL_SCRAP TS ON (T.TRAVEL_NO = TS.TRAVEL_NO)
		WHERE TS.MEMBER_NO = #{memberNo}
		AND T.TRAVEL_DEL_FL = 'N'
		AND T.PRIVATE_FL = 'N'
	</select>
    ```
</div>
</details>

<br>

## 아이디 찾기
### 컨트롤러
<details>
<summary>접기/펼치기</summary>
<div markdown="1">

```Java
@PostMapping("/findEmail")
	public String findEmail(Member inputMember, String[] memberRRN, RedirectAttributes ra) {
		
		// 주민등록번호 가공
		String combineMemberRRN = String.join("-", memberRRN);
		
        // VO에 세팅
		inputMember.setMemberRRN(combineMemberRRN);
		
		// service 호출
		Member findMember = service.findEmail(inputMember);

		if(findMember != null) {							// 일치하는 정보 유무에 따라 넘겨줄 값 세팅
			ra.addFlashAttribute("result", findMember.getMemberEmail());
		}

		ra.addFlashAttribute("memberName", inputMember.getMemberName());
		return "redirect:/member/result";
	}
```
</div>
</details>

### 결과페이지-result.jsp
<details>
<summary>접기/펼치기</summary>
<div markdown="1">

```HTML
<p class="result-message"><span id="memberName">${memberName }</span>님의 회원 아이디</p>
<c:choose>
    <c:when test="${not empty result }">
        <div class="result-box">
            "${result}"
        </div>
        
        <div class="btn-box">
            <a href="/member/login">로그인</a>
            <a href="/member/findPw">비밀번호 찾기</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="result-box">
            "일치하는 회원정보가 존재하지않습니다."
        </div>
    
        <div class="btn-box">
            <a href="/">메인페이지</a>
            <a href="/member/findEmail">아이디 찾기</a>
        </div>
    </c:otherwise>
</c:choose>
```
</div>
</details>

<br>

## 미니홈피 TODAY

1. 미니홈피 요청시 인터셉터 호출
    ```XML
    <interceptors>
        <interceptor>
            <mapping path="/minihome/*"/>
            <beans:bean id="minihomeInterceptor" class="com.team.cubespace.common.interceptor.MinihomeInterceptor"></beans:bean>
        </interceptor>
    </interceptors>
    ```
2. 인터셉터 로직 수행
    ```JAVA
    public class MinihomeInterceptor implements HandlerInterceptor {
        @Autowired
        private MinihomeService service;
        
        @Autowired
        private HttpSession session;
        
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // pathVariable의 값들을 Map으로 가져오기 가져오기
            Map<String, String> pathVariable = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            // Map에서 memberNo의 값만 반환받기
            int memberNo = Integer.parseInt(pathVariable.get("memberNo"));

            // session에서 로그인한 회원의 번호 반환받기
            int loginMemberNo = ((Member)session.getAttribute("loginMember")).getMemberNo();
            
            // 쿠키를 사용하여 1일에 1번만 TODAY 증가
            // 자신이 자신의 미니홈피를 방문할 때가 아니면
            if(loginMemberNo != memberNo) {
                // 쿠키배열 가져오기
                Cookie[] cookies = request.getCookies();
                Cookie c = null;
                // 쿠키 배열이 비어있지 않다면
                if(cookies != null) {
                    // 쿠키배열에서 visitedMiniHome 쿠키 찾기
                    for(Cookie temp : cookies) {
                        if(temp.getName().equals("visitedMiniHome")) { // 찾으면
                            c = temp;	// 대입
                            break;		// 검색 중단
                        }
                    }
                }
                if(c != null) {	// visitedMiniHome를 찾았다면
                    if(c.getValue().indexOf("|" + memberNo + "|") == -1) {	// 오늘 방문한 적이 없다면
                        // 업데이트 서비스 호출
                        int result = service.updateTodayTotal(memberNo);
                        
                        // 업데이트 완료 시
                        if(result > 0) {
                            c.setValue(c.getValue() + "|" + memberNo + "|");
                        }
                    }
                } else {		// 찾지 못했다면
                    int result = service.updateTodayTotal(memberNo);
                    // 업데이트 완료 시
                    if(result > 0) {
                        c = new Cookie("visitedMiniHome", "|" + memberNo + "|");
                    }
                }
                
                if(c != null) {
                    c.setPath("/");
                    
                    Date a = new Date();
                    Calendar cal = Calendar.getInstance();
                    
                    cal.add(cal.DATE, 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date temp = new Date(cal.getTimeInMillis());
                    
                    Date b = sdf.parse(sdf.format(temp));
                    
                    long diff = b.getTime() - a.getTime();
                    
                    c.setMaxAge((int)diff/1000);
                    response.addCookie(c);			
                }
                
            }
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
    }
    ```

<br>

## FFmpeg를 사용하여 동영상 인코딩

### FFmpeg
- 동영상을 다루기 위한 프로그램
- Java에서 프로그램을 실행시켜 커맨드를 입력받는 객체를 생성하여 커맨드 실행

### 컨트롤러
```JAVA
@ResponseBody
@PostMapping("/videoWrite")
public String videoWrite(Video video,
        @RequestParam("inputVideo") MultipartFile inputVideo,
        @SessionAttribute("loginMember") Member loginMember,
        HttpSession session) throws Exception {
    
    // 작성된 동영상에 작성자 번호 세팅
    video.setMemberNo(loginMember.getMemberNo());
    
    // 웹 어플리케이션에 비디오가 저장될 경로
    String videoWebPath = "/resources/video/";

    // 컴퓨터에 비디오가 저장될 경로
    String videoFolderPath = session.getServletContext().getRealPath(videoWebPath);
    
    // 컴퓨터에 FFmpeg관련 .exe파일이 저장되어있는 폴더경로
    String ffmpegPath = "/resources/ffmpeg-5.1.2-essentials_build";
    ffmpegPath = session.getServletContext().getRealPath(ffmpegPath);
    
    // 웹 어플리케이션에 썸네일이 저장될 경로
    String thumbnailWebPath = "/resources/videothumbnail/";

    // 컴퓨터에 썸네일이 저장될 경로
    String thumbnailFolderPath = session.getServletContext().getRealPath(thumbnailWebPath);
    
    // 비디오 작성 서비스 호출
    int videoNo = service.videoWrite(video, inputVideo, videoWebPath, 
            videoFolderPath, ffmpegPath, thumbnailWebPath, thumbnailFolderPath);
    
    Map<String, Integer> resultMap = new HashMap<>();
    resultMap.put("folderNo", video.getFolderNo());
    resultMap.put("videoNo", videoNo);
    
    return new Gson().toJson(resultMap);
}    
```

### 서비스
```Java
// 비디오 글 작성
@Override
public int videoWrite(Video video, MultipartFile inputVideo, String videoWebPath, String videoFolderPath,
        String ffmpegPath, String thumbnailWebPath, String thumbnailFolderPath) throws Exception {
    // 비디오 글 내용 XSS, Line 처리
    video.setVideoTitle(Util.XSSHandling(video.getVideoTitle()));
    if(video.getVideoContent() != null) {
        video.setVideoContent(Util.XSSHandling(video.getVideoContent()));
        video.setVideoContent(Util.newLineHandling(video.getVideoContent()));
    }

    // 인코딩 전 임시 파일 변경명
    String tempRename = Util.fileRename(inputVideo.getOriginalFilename());
    
    // 동영상 파일 변경명
    String fileRename = Util.fileRename(inputVideo.getOriginalFilename().substring(0, inputVideo.getOriginalFilename().lastIndexOf("."))+".mp4");

    // 동영상 썸네일 파일 변경명
    String thumbnailRename = fileRename.substring(0, fileRename.lastIndexOf(".")) + ".png";    
    
    // VO에 값 세팅
    video.setVideoPath(videoWebPath);
    video.setVideoOriginalName(inputVideo.getOriginalFilename());
    video.setVideoRename(fileRename);
    video.setVideoThumbnail(thumbnailWebPath + thumbnailRename);
    
    // DB에 비디오 정보 INSERT
    int videoNo = dao.albumWrite(video);
    
    if(videoNo > 0) { 	// DB에 video 정보 등록 성공 시
        
        // 영상 인코딩 후 서버에 저장
        try {
            Util.uploadVideo(ffmpegPath, videoFolderPath, tempRename, fileRename, thumbnailFolderPath, thumbnailRename, inputVideo);				
        }catch(Exception e) {
            throw new Exception();
        }
    }
    return videoNo;
}
```

### Util 클래스 비디오 업로드 메서드
```Java
public static void uploadVideo(String ffmpegPath, String videoFolderPath, String tempVideoName, String videoName, 
								String thumbnailFolderPath, String thumbnailName, MultipartFile inputVideo) throws Exception {
	
    // 인코딩 전 동영상 저장
    File tempFile = new File(videoFolderPath + tempVideoName);
    inputVideo.transferTo(tempFile);
    
    // 매개변수로 받은 컴퓨터의 .exe파일 경로를 사용해
    // 인코딩을 위한 FFmpeg 관련 .exe파일 객체 생성
    FFmpeg ffmpeg = new FFmpeg(ffmpegPath+ "/ffmpeg");
    FFprobe ffprobe = new FFprobe(ffmpegPath + "/ffprobe");

    // 인코딩 커맨드 입력 객체 생성 후 설정값 세팅
    // 저장한 인코딩하지 않은 원본파일을 가져와 객체 생성
    FFmpegBuilder builder = new FFmpegBuilder().setInput(videoFolderPath + tempVideoName)	// 파일 경로
            .overrideOutputFiles(true)	// 오버라이드
            .addOutput(videoFolderPath + videoName)	// 저장경로
            .setFormat("mp4")								// 포맷(확장자)
            .setVideoCodec("libx264")                       // 비디오 코덱
            .setAudioChannels(2)						// 오디오 채널(1ㅣ모노, 2:스테레오)
//				.setVideoResolution(1280, 720)					// 동영상 해상도
            .setVideoBitRate(1464800)						// 동영상 비트레이트 (프레임) 1464800 30프레임
            .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)	// ffmpeg 빌더 실행 허용
            .done();                                        // 커맨드 입력 종료
    
    // .exe 파일 실행을 위한 객체 생성
    FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
    // 커맨드 실행 메서드
    executor.createJob(builder).run();

    // 임시로 저장한 인코딩 하기전의 원본 동영상 파일 삭제
    tempFile.delete();
    
    // 썸네일 이미지 추출 후 서버에 저장
    // 저장한 동영상 파일을 다시 불러와서
    // 썸네일을 추출하기 위한 커맨드 세팅
    FFmpegBuilder thumbnailBuilder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(videoFolderPath + videoName)             // 파일경로
                .addExtraArgs("-ss", "00:00:01")
                .addOutput(thumbnailFolderPath + thumbnailName)    // 결과 파일 저장할 경로
                .setFrames(1)                                      // 프레임 수
                .done();                                           // 커맨드 입력 종료
    // .exe 파일 실행을 위한 객체 생성
    FFmpegExecutor thumbnailExecutor = new FFmpegExecutor(ffmpeg, ffprobe);
    // 커맨드 실행 메서드
    thumbnailExecutor.createJob(thumbnailBuilder).run();
}
```

