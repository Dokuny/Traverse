# TRAVERSE

![트래버스 로고 촤종](https://github.com/Dokuny/traverse/assets/87813831/02109778-e8bf-4ca7-907e-790392c0d268)

> 여행지를 찾고, 여행 계획을 세우고, 기록을 남기는 여행 플래너 + 여행 SNS 프로젝트입니다.

<br><br>


## 📜주요 기능 소개
- SNS 간편 로그인 / 회원가입
  -  HTTP Interface를 이용하여 카카오와 네이버의 OAuth 2.0 API를 통해 회원 정보 조회 및 회원가입 분기 처리
  -  Spring Security와 Json Web Token을 이용하여 서버에 대한 API 권한 인증 처리
  -  Refresh Token을 이용한 RTR(Refresh Token Rotation)방식으로 로그인 유지를 통해 UX 및 보안 향상

- 대시보드
  -  Geolocation을 이용하여 현재 위치를 파악한 후, 위치 정보를 기반으로 날씨 공공 데이터 API와 주소 정보 API를 활용하여 현재 위치 기반 날씨 정보 제공
  -  내가 세운 여행 계획을 기반으로 현재 날짜에서 가장 가까운 계획까지의 D-Day 제공
  -  내 여행 계획 기반으로 한 통계 정보(자주 가는 지역, 여행 타입) 제공
  -  스프링 스케줄링과 Youtube API를 활용한 여행 관련 인기 영상 제공
  -  iframe을 이용한 기차/버스 시간표 조회

- 여행 계획 세우기
  -  관광지 정보 공공데이터를 활용하여 관광지, 숙박, 레포츠, 문화시설, 음식점, 쇼핑 정보에 대한 필터링 검색 기능 제공
  -  부족한 위치 정보는 Kakao 장소 검색 API를 이용하여 추가할 수 있는 신규 장소 등록 기능 제공
  -  Google Map을 활용한 지도 마커 기능 제공

- 여행 계획 관리
  -  내 계획 리스트를 한 눈에 볼 수 있도록 토글 형식의 페이지 제공
  -  Google Map의 Polyline과 Marker, Kakao Mobility의 경로 안내 API를 활용한 계획의 최적 경로 안내를 시각적으로 제공
  -  계획 수정 및 삭제 기능 제공
 
- 여행지 기반 채팅
  - 해당 여행지에서 동행을 구할 수 있도록 RabbitMQ와 WebSocket, STOMP를 활용한 여행지 기반 채팅 기능 제공

- 여행 기록
  - 여행지와 계획을 기반으로 사진과 간단한 글을 남길 수 있는 기록 기능 제공
  - Oracle Cloud Storage를 이용하여 사진 업로드

<br><br>





## 🚀 Demo

<table align="center">
<thead>
<tr margin-bottom=3px>
<td width="300" align="center">
<b style="color:#8fe3d9">손쉬운 간편 로그인 (naver, kakao)<b>
</td>
<td width="300" align="center">
<b>
프로필 수정
</b>



</td>
</tr>
</thead>
<tbody>
<tr>
<td width="300" align="center">
<img src="https://github.com/Dokuny/traverse/assets/87813831/f53cc554-155b-472a-8e55-61ec21596398" width="350">
</td>
<td width="300" align="center">
<img src="https://github.com/Dokuny/traverse/assets/87813831/e1030682-d0c7-4f97-8f70-e13938bd53b5" width="350">
</td>
</tr>
<tr>
<td width="300" align="center">

<b>
대시보드 (현재 날씨, D-Day, 통계, 추천 영상 등)
</b>
</td>
<td width="300" align="center">


<b>
계획 세우기
</b>
</td>
</tr>
<tr>
<td width="300" align="center">
<img src= "https://github.com/Dokuny/traverse/assets/87813831/4b60908b-a1af-4733-a9bd-4d75de8cf791" width="350"  > 
</td>
<td width="300" align="center">
<img src="https://github.com/Dokuny/traverse/assets/87813831/d07e1f45-1a36-4b2f-848b-c70df36cc45d" width="350" >
</td>
</tr>
<tr>
<td width="300" align="center">


<b>
계획 조회
</b>
</td>
<td width="300" align="center">

<b>
여행지 채팅
</b>
</td>
</tr>
<tr>
<td width="300" align="center">
<img src="https://github.com/Dokuny/traverse/assets/87813831/fe4764fd-7719-4d8b-9fb2-72e4e1b6248e" width="350">
</td>
<td width="300" align="center">
<img src="https://github.com/Dokuny/traverse/assets/87813831/5a58e871-3eea-4ce7-8ec4-a42f76526574" width="350">
</td>
</tr>
<tr>
<td width="300" align="center">

<b>
참여 신청 알림 
</b>
</td>
<td width="300" align="center">

<b>
팀원과 의사소통하는 팀 채팅
</b>
</td>
</tr>
<tr>
<td width="300" align="center">
<img src="https://user-images.githubusercontent.com/49369306/195658135-52696018-8437-4e23-9c1c-fd7893e04c81.gif" width="350">
</td>
<td width="300" align="center">
<img src="https://user-images.githubusercontent.com/49369306/195594127-c82f606f-8326-471e-8213-0a780722a422.gif" width="350">
</td>
</tr>
<tr>
<td width="300" align="center">

<b>
이번 주 인기 기술 스택 통계 
</b>
</td>
<td width="300" align="center">

<b>
기술스택 맞춤 임박 공고 이메일 추천 기능
</b>
</td>
</tr>
<tr>
<td width="300" align="center">
<img src="https://user-images.githubusercontent.com/49369306/195584647-2f17ae6b-1a41-47ec-964a-d67cba0a247a.gif" width="350">
</td>
<td width="300" align="center">
<img src="https://user-images.githubusercontent.com/49369306/195590532-b5569b8c-26e0-4520-91b0-11639688ec01.png" width="350">
</td>
</tr>
</tbody>
</table>


<br><br>

## 🧑‍🤝‍🧑 역할

|   ![imoge](https://avatars.githubusercontent.com/u/87813831?v=4)  |
| :---------------------------------------------------------------------------------------------------------------------------: | 
| Full-Stack |
| [👑이도훈](https://github.com/Dokuny) |

<br>

## 🔧Skills
### Front-End
* Vue.js
* Vite
* STOMP
* WebSocket

### Back-End
* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* MyBatis
* MySQL
* Spring AMQP (RabbitMQ)
  
### Infrastructure
* Oracl Cloud Infra (OCI)
  * Storage
  * Computing
* Netlify


<br><br>

## 🏢Architecture
![시스템아키텍처](https://github.com/Dokuny/traverse/assets/87813831/033fe769-6b38-4a33-a539-0dfb8d26ce19)



<br><br>

## ERD
![image](https://github.com/Dokuny/traverse/assets/87813831/5641c617-7431-48dc-8816-b3ad150f214a)

