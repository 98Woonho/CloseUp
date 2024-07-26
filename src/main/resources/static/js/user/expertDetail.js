/*************** 케러셀 ***************/
// 서비스 목록 캐러셀
const rootNodeService = document.querySelector('.embla_service');
const viewportNodeService = rootNodeService.querySelector('.embla__viewport_service');
// 포트폴리오 목록 캐러셀
const rootNodePortfolio = document.querySelector('.embla_portfolio');
const viewportNodePortfolio = rootNodePortfolio.querySelector('.embla__viewport_portfolio');

// 서비스 캐러셀 버튼
const prevButtonNodeService = rootNodeService.querySelector('.embla__prev_service');
const nextButtonNodeService = rootNodeService.querySelector('.embla__next_service');
// 포티폴리오 캐러셀 버튼
const prevButtonNodePortfolio = rootNodePortfolio.querySelector('.embla__prev_portfolio');
const nextButtonNodePortfolio = rootNodePortfolio.querySelector('.embla__next_portfolio');

// 서비스
const emblaService = EmblaCarousel(viewportNodeService);
// 포트폴리오
const emblaPortfolio = EmblaCarousel(viewportNodePortfolio);

// 서비스
prevButtonNodeService.addEventListener('click', emblaService.scrollPrev, false);
nextButtonNodeService.addEventListener('click', emblaService.scrollNext, false);
// 포트폴리오
prevButtonNodePortfolio.addEventListener('click', emblaPortfolio.scrollPrev, false);
nextButtonNodePortfolio.addEventListener('click', emblaPortfolio.scrollNext, false);


const consultBtn = document.getElementById('consultBtn');
const expertNickname = document.getElementById('expertNickname').value;

// 상담하기 버튼 클릭 event
consultBtn.addEventListener('click', function(e) {
    e.preventDefault();

    // 채팅 방 생성
    axios.post('/chat/room', { expertNickname: expertNickname })
        .then(res => {
            location.href = `/myPage/chats`;
        })
        .catch(err => {
            console.log(err);
        })
})