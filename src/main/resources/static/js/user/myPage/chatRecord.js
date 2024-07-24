function getExpertLocation(address) {

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

        var map = new naver.maps.Map('map', {
            center: new naver.maps.LatLng(addrLat, addrLng),
            zoom: 15,
            minZoom: 14, //지도의 최소 줌 레벨
            draggable: true,
            pinchZoom: true,
            scrollWheel: true,
            disableKineticPan: false, // 관성드래깅
            scaleControl: false, // 스케일 컨트롤러
            logoControl: true, // 로고 컨트롤러
            logoControlOptions: {
                position: naver.maps.Position.TOP_RIGHT
            },
            mapDataControl: false
        });

        var marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(addrLat, addrLng),
            map: map
        });
    });
}
