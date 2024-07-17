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
// 현재 위치 버튼
const currentLocaBtn = document.getElementById('currentLocaBtn');
const currentAddress = document.getElementById('currentAddress');

// 인포컨테이너 템플릿
var infoTemplate = '<div class="info-template">\n' +
    '    <div class="name-con">\n' +
    '        <p th:text="${expertInformation.userId}" class="expert-name">전문가 아이디</p>\n' +
    '        <p th:text="${expertInformation.introduction}" class="expert-desc">전문 분야/ 간단 설명</p>\n' +
    '    </div>\n' +
    '    <button type="button">프로필<br/>보기</button>\n' +
    '</div>'

let zoom = 16; // 초기 줌 레벨
let map;
let markers;

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
        minZoom: 15, //지도의 최소 줌 레벨
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

    // 주소를 좌표로 변환(지오코더 사용)
    naver.maps.Service.geocode({
        query: '중앙대로 366'
    }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            return alert('잘못된 주소를 입력하셨습니다.');
        }

        var result = response.v2, // 검색 결과의 컨테이너
            items = result.addresses; // 검색 결과의 배열

        let addrLat = items[0].y;
        let addrLng = items[0].x;

        var expertMarkers = new naver.maps.Marker({
            position: new naver.maps.LatLng(addrLat, addrLng),
            map: map,
            icon: {
                content: '<img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" style="width: 40px; height: 40px;"/>',
                size: new naver.maps.Size(40, 40),
                anchor: new naver.maps.Point(20, 30)
            }
        });

        // 마커 클릭 시 나오는 인포컨테이너
        (function(expertMarkers) {
            var infoWindow = new naver.maps.InfoWindow({
                content: infoTemplate,
                borderWidth: 0
            });

            naver.maps.Event.addListener(expertMarkers, 'click', function() {
                if (infoWindow.getMap()) {
                    infoWindow.close();
                } else {
                    infoWindow.open(map, expertMarkers);
                }
            });

            // 인포컨테이너가 열린 상태로 맵 아무 곳 클릭 시 인포 닫힘
            naver.maps.Event.addListener(map, 'click', function () {
                if (infoWindow.getMap()) {
                    infoWindow.close();
                }
            })
        })(expertMarkers);

        markers.push(expertMarkers);
    });


    // 현재 위치 클릭 시 현재 위치로 이동
    currentLocaBtn.addEventListener('click', (e) => {
        e.preventDefault();
        map.panTo(latLng);
    });

    // 보이는 지역만 마커 표시 함수
    // naver.maps.Event.addListener(map, 'idle', function() {
    //     updateMarkers(map, markers);
    // });

    // function updateMarkers(map, markers) {
    //     var mapBounds = map.getBounds();
    //     var marker, position;
    //
    //     for (var i = 0; i < markers.length; i++) {
    //         marker = markers[i]
    //         position = marker.getPosition();
    //
    //         if (mapBounds.hasLatLng(position)) {
    //             showMarker(map, marker);
    //         } else {
    //             hideMarker(map, marker);
    //         }
    //     }
    // }
    //
    // function showMarker(map, marker) {
    //     if (marker.getMap()) return;
    //     marker.setMap(map);
    // }
    //
    // function hideMarker(map, marker) {
    //     if (!marker.getMap()) return;
    //     marker.setMap(null);
    // }
}

// 내 위치 정의
navigator.geolocation.getCurrentPosition(function(pos) {
    loadNaverMap(pos.coords.latitude, pos.coords.longitude, options);
});
