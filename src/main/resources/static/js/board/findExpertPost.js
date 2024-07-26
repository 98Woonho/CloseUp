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

const payMethodBtns = document.querySelectorAll('.pay-method-btn');
let payMethod;

function clickPayMethodBtn(btn) {
    payMethod = btn.dataset.method;
    payMethodBtns.forEach(payMethodBtn => {
        if (payMethodBtn.dataset.method === payMethod) {
            payMethodBtn.classList.add('clicked');
        } else {
            payMethodBtn.classList.remove('clicked');
        }
    })
}

const purchaseBtn = document.getElementById('purchaseBtn');
const payBtn = document.getElementById('payBtn')
const cancelBtn = document.getElementById('cancelBtn');
const paymentMethodDialog = document.getElementById('paymentMethodDialog');

purchaseBtn.addEventListener('click', function (e) {
    e.preventDefault();

    paymentMethodDialog.classList.add('visible');
})

cancelBtn.addEventListener('click', function (e) {
    e.preventDefault();

    paymentMethodDialog.classList.remove('visible');
})

payBtn.addEventListener('click', function (e) {
    e.preventDefault();

    const expertServiceId = payBtn.dataset.expertserviceid;
    const phone = payBtn.dataset.phone;

    let pg;
    let pay_method;

    if (!payMethod) {
        alert('결제 수단을 선택해 주세요.');
        return false;
    }

    if (payMethod === 'card') {
        pg = "html5_inicis";
        pay_method = "card";
    }

    if (payMethod === 'kakaopay') {
        pg = "kakaopay";
        pay_method = "card";
    }

    if (payMethod === 'tosspay') {
        pg = "tosspay";
        pay_method = "card";
    }

    if (payMethod === 'payco') {
        pg = "payco"
        pay_method = "card";
    }

    if (payMethod === 'phone') {
        pg = "danal";
        pay_method = "phone";
    }

    IMP.init("imp82217082");

    IMP.request_pay({
            pg: pg,
            pay_method: pay_method,
            merchant_uid: "merchant_" + new Date().getTime(),
            name: '전문가 닉네임',
            buyer_tel: phone,
            amount: 100 // 총 가격
        },
        function (resp) {
            if (resp.success) {
                axios.post("/board/findExpertWrite", {
                    impUid: resp.imp_uid,
                    merchantUid: resp.merchant_uid,
                    payMethod: resp.pay_method,
                    paidAmount: resp.paid_amount,
                    expertServiceId: expertServiceId
                })
                    .then(res => {
                        if (res.status === 200) {
                            alert(res.data);
                            location.href = "/myPage/payment"
                        }
                    })
                    .catch(err => {
                        alert('알 수 없는 이유로 결제에 실패 하였습니다. 잠시 후 다시 시도해 주세요.')
                    })
            } else {
                if (resp.error_code === 'F1001') {
                    alert('결제가 취소 되었습니다.');
                } else {
                    // 그 외의 결제 실패
                    alert(`결제에 실패하였습니다. 에러 메시지: ${resp.error_msg}`);
                }
            }

        });
})