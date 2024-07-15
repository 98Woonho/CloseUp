// 캐러셀
// Grab wrapper nodes
const rootNode = document.querySelector('.embla');
const viewportNode = rootNode.querySelector('.embla__viewport');

// Grab button nodes
const prevButtonNode = rootNode.querySelector('.embla__prev');
const nextButtonNode = rootNode.querySelector('.embla__next');

// Initialize the carousel
const embla = EmblaCarousel(viewportNode);

// Add click listeners
prevButtonNode.addEventListener('click', embla.scrollPrev, false);
nextButtonNode.addEventListener('click', embla.scrollNext, false);

//////////////////// 네이버 지도 api ///////////////////////
//지도를 삽입할 HTML 요소 또는 HTML 요소의 id를 지정합니다.
var HOME_PATH = window.HOME_PATH || '.';
// 현재 위치 버튼
const currentLocaBtn = document.getElementById('currentLocaBtn');
// 인포컨테이너 템플릿
var infoTemplate = '<div class="info-template">\n' +
    '    <div class="name-con">\n' +
    '        <p class="expert-name">전문가 아이디</p>\n' +
    '        <p class="expert-desc">전문 분야/ 간단 설명</p>\n' +
    '    </div>\n' +
    '    <button type="button">프로필<br/>보기</button>\n' +
    '</div>'

let zoom = 15; // 초기 줌 레벨
let map;
var markers = [];

// 내 위치 정확도 높이는 옵션
var options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0
}

// 맵 로딩 함수
function loadNaverMap(lat, lng){
    const latLng = new naver.maps.LatLng(lat, lng);

    // 커스텀 맵 객체
    map = new naver.maps.Map('map',{
        center: new naver.maps.LatLng(latLng), //좌표
        zoom: zoom, //지도의 초기 줌 레벨
        minZoom: 6, //지도의 최소 줌 레벨
        draggable: true,
        pinchZoom: true,
        scrollWheel: true,
        disableKineticPan: false, // 관성드래깅
        scaleControl: false, // 스케일 컨트롤러
        logoControl: true, // 로고 컨트롤러
        logoControlOptions: {
            position: naver.maps.Position.TOP_RIGHT
        },
        mapDataControl: false,
        zoomControl: true, //줌컨트롤러
        zoomControlOptions: {
            position: naver.maps.Position.TOP_LEFT
        },
        mapTypeControl: false
    });

    // 현재 위치 마커
    var currentMarker = new naver.maps.Marker({
        position: latLng,
        map: map,
        icon: {
            content: '<i style="margin: 0px; padding: 0px; border: 0px solid transparent; display: block; max-width: none; max-height: none; ' +
                '-webkit-user-select: none; position: absolute; width: 36px; height: 36px; left: 0px; top: 0px; color: var(--color-error-red); font-size: 36px" class="fa-solid fa-location-crosshairs"></i>',
            size: new naver.maps.Size(36, 36),
            anchor: new naver.maps.Point(18, 18)
        }
    });

    // 내 주위 마커(일단은 랜덤)
    for (let i = 0; i < 3; i++) {
        var randomLat = lat + (Math.random() - 0.5) * 2 * 0.01; // 반경 1km 내의 랜덤 위도
        var randomLng = lng + (Math.random() - 0.5) * 2 * 0.01; // 반경 1km 내의 랜덤 경도
        var marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(randomLat, randomLng),
            map: map,
            icon: {
                content: '<img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" style="width: 36px; height: 36px;"/>',
                size: new naver.maps.Size(36, 36),
                anchor: new naver.maps.Point(18, 18)
            }
        });

        // 마커 클릭 시 나오는 인포컨테이너
        (function(marker) {
            var infoWindow = new naver.maps.InfoWindow({
                content: infoTemplate,
                borderWidth: 0
            });

            naver.maps.Event.addListener(marker, 'click', function() {
                if (infoWindow.getMap()) {
                    infoWindow.close();
                } else {
                    infoWindow.open(map, marker);
                }
            });

            // 인포컨테이너가 열린 상태로 맵 아무 곳 클릭 시 인포 닫힘
            naver.maps.Event.addListener(map, 'click', function () {
                if (infoWindow.getMap()) {
                    infoWindow.close();
                }
            })
        })(marker);

        markers.push(marker);

        // 현재 위치 클릭 시 현재 위치로 이동
        currentLocaBtn.addEventListener('click', (e) => {
            e.preventDefault();
            map.panTo(latLng);
        });
    }
}

// 내 위치 정의
navigator.geolocation.getCurrentPosition(function(pos) {
    loadNaverMap(pos.coords.latitude, pos.coords.longitude, options);
});
