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
const currentLocaBtn = document.getElementById('currentLocaBtn');

let lat;
let lng;
let circle;
let me;
let markers;
let infoWindows;
let zoom = 15; // 초기 줌 레벨
let map;

var options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0
}

function loadNaverMap(lat, lng){
    const latLng = new naver.maps.LatLng(lat, lng);

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

    var marker = new naver.maps.Marker({
        position: latLng,
        map: map,
        icon: {
            content: '<i style="margin: 0px; padding: 0px; border: 0px solid transparent; display: block; max-width: none; max-height: none; ' +
                '-webkit-user-select: none; position: absolute; width: 36px; height: 36px; left: 0px; top: 0px; color: var(--color-error-red); font-size: 36px" class="fa-solid fa-location-crosshairs"></i>',
            size: new naver.maps.Size(36, 36),
            anchor: new naver.maps.Point(18, 18)
        }
    });

    currentLocaBtn.addEventListener('click', (e) => {
        e.preventDefault();
        map.panTo(latLng);
    });
}



navigator.geolocation.getCurrentPosition(function(pos) {
    loadNaverMap(pos.coords.latitude, pos.coords.longitude, options);
});

