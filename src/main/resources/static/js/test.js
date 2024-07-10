const testBtn = document.getElementById('testBtn');

IMP.init('imp82217082');

testBtn.addEventListener('click', function(e) {
    e.preventDefault();

    IMP.certification(
        {
            pg: 'inicis_unified',
            merchant_uid: `mid_${new Date().getTime()}`,
        },
        function (res) {
            if (res.success) {
                axios.post(`/user/certification/${res.imp_uid}`)
                    .then(resp=> {
                        console.log(resp); // 여기에 인증 정보가 담겨있음.
                    })
                    .catch(error=>{console.log("local msg ",error);});
            } else {
                alert('인증에 실패하였습니다. 에러 내용: ' + res.error_msg);
            }
        }
    )
})