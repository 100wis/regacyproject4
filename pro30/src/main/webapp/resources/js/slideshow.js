var interval = 2000;

// 슬라이드쇼 요소 선택
var slides = document.getElementById("slides");
var slideIndex = 0;

// 슬라이드쇼 자동 전환 함수
function startSlideShow() {
  setInterval(function() {
    nextSlide();
  }, interval);
}

// 다음 슬라이드로 전환 함수
function nextSlide() {
  slideIndex++;
  if (slideIndex >= slides.getElementsByTagName("img").length) {
    slideIndex = 0;
  }
  updateSlide();
}

// 슬라이드 업데이트 함수
function updateSlide() {
  var imgElements = slides.getElementsByTagName("img");
  for (var i = 0; i < imgElements.length; i++) {
    if (i === slideIndex) {
      imgElements[i].style.display = "block";
    } else {
      imgElements[i].style.display = "none";
    }
  }
}

// 이전 슬라이드로 전환 함수
function prevSlide() {
  slideIndex--;
  if (slideIndex < 0) {
    slideIndex = slides.getElementsByTagName("img").length - 1;
  }
  updateSlide();
}

// 이전/다음 버튼 클릭 이벤트 처리
document.getElementById("prev").addEventListener("click", prevSlide);
document.getElementById("next").addEventListener("click", nextSlide);

// 슬라이드쇼 시작
startSlideShow();


//document.querySelector("#logout").addEventListener("click", e => {
//  fetch("logout.do", {
//    method: "POST",
//    headers: {
//      "Content-Type": "application/json; charset=UTF-8",
//    },
//  })
//  .then((response) => {
//    if (response.ok) {
//      alert("로그아웃 되었습니다.");
//      location.replace("mainHomepageForm.do");
//    } else {
//      alert("로그아웃에 실패했습니다.");
//    }
//  })
//  .catch(error => {
//    console.error("로그아웃 오류:", error);
//    alert("로그아웃에 오류가 발생했습니다.");
//  });
//});