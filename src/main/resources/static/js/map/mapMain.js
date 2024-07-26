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
let currentAddress = document.getElementById('currentAddress');

let zoom = 16; // 초기 줌 레벨
let map;
let markers;


// 내 위치 정의
navigator.geolocation.getCurrentPosition(function(pos) {
    const curLat = pos.coords.latitude;
    const curLng = pos.coords.longitude;
    loadNaverMap(curLat, curLng, options);
});
// 내 위치


// 내 위치 정확도 높이는 옵션
var options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0
}


// 맵 로딩 함수
function loadNaverMap(lat, lng){
    const latLng = new naver.maps.LatLng(lat, lng);


    // 경도,위도로 내 위치의 도로명주소 가져오기
    naver.maps.Service.reverseGeocode({
        coords: latLng,
    }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            return alert('Something wrong!');
        }

        var result = response.v2, // 검색 결과의 컨테이너
            items = result.results, // 검색 결과의 배열
            address = result.address; // 검색 결과로 만든 주소

        if(address.roadAddress !== '') {
            currentAddress.textContent = address.roadAddress;
        } else {
            currentAddress.textContent = address.jibunAddress;
        }
    });


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
                '-webkit-user-select: none; position: absolute; left: 0px; top: 0px; color: var(--color-error-red); font-size: 24px" class="fa-solid fa-location-crosshairs"></i>',
            size: new naver.maps.Size(24, 24),
            anchor: new naver.maps.Point(12, 12)
        }
    });

    // 데이터베이스에서 값 가져오기
    fetch('/expert/mapData')
        .then(response => {
            return response.json()
        }).then(value => {
        value.forEach(item => {
            const nickname = item.nickname;
            const introduction = item.introduction;
            const address = item.address;
            const addressDetail = item.addressDetail;
            const profileImg = item.profileImg;
            const base64ProfileImg = `data:/image/*;base64, ${profileImg}`;

            // 주소를 좌표로 변환(지오코더 사용)
            naver.maps.Service.geocode({
                query: address
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
                        content: '<img src="' + base64ProfileImg + '" style="width: 40px; height: 40px; border-radius: 50%"/>',
                        size: new naver.maps.Size(40, 40),
                        anchor: new naver.maps.Point(20, 30)
                    }
                });

                // 마커 클릭 시 나오는 인포컨테이너
                (function(expertMarkers) {
                    var infoWindow = new naver.maps.InfoWindow({
                        content: '<div class="info-template">\n' +
                            '    <div class="name-con">\n' +
                            '        <p class="expert-name">' + nickname + '</p>\n' +
                            '        <p class="expert-desc">' + introduction + '</p>\n' +
                            '        <p class="expert-addr">' + address + ' ' +  addressDetail + '</p>\n' +
                            '    </div>\n' +
                            `    <button type="button" onclick="location.href='/user/expertDetail/${nickname}'">프로필<br/>보러가기</button>\n` +
                            '</div>',
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
        });
    });

    // 현재 위치 클릭 시 현재 위치로 이동
    currentLocaBtn.addEventListener('click', (e) => {
        e.preventDefault();
        map.panTo(latLng);
    });
}

const expertContainer = document.getElementById('expertContainer');

fetch('/expert/mapData')
    .then(response => {
        return response.json()
    }).then(value => {
    value.forEach(item => {
        const nickname = item.nickname;
        const introduction = item.introduction;
        const profileImg = item.profileImg;
        const base64ProfileImg = `data:/image/*;base64, ${profileImg}`;

        const slideDiv = document.createElement('div');
        slideDiv.classList.add('embla__slide');

        slideDiv.innerHTML = `
        <div class="img-container">
            <a href="/user/expertDetail/${nickname}">
                <img src="${base64ProfileImg}" alt="#">
            </a>
        </div>
        <div class="title-container">
            <a class="profile-title" href="#">${nickname}</a>
            <a class="profile-desc" href="#">${introduction}</a>
        </div>
        <div class="rate-container">
            <i class="fa-solid fa-star"></i>
            <span>5.0</span>
        </div>
    `;
    expertContainer.appendChild(slideDiv);
    });
});