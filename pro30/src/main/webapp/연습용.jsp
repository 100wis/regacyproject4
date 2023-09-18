<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<script>
//댓글리스트 가져오기


// 댓글 출력하는 함수
// 함수인자로  replyDTO넣어줘야함
function innertext_reply(reply){
	
	  // 댓글을 표시할 요소 생성
    const replyItem = document.createElement("div");
    replyItem.classList.add("reply-item");
    
    // 댓글 내용 표시
    const pElement = document.createElement("p");
    
    // 댓글 번호 표시
    const rReplynum = document.createElement("span");
    rReplynum.classList.add("reply-num");
    rReplynum.innerText = reply.replyid; 
    pElement.appendChild(rReplynum);

    // 댓글 내용 표시
    const rContents = document.createElement("span");
    rContents.classList.add("reply-contents");
    rContents.innerText = " " + reply.contents;
    pElement.appendChild(rContents);
    
    // 작성자 표시
    const rWriterUid = document.createElement("span");
    rWriterUid.classList.add("reply-writer-uid");
    rWriterUid.innerText = " 작성자: " + reply.writer_uid;
    pElement.appendChild(rWriterUid);
    
    // 작성일 표시
    const rRegDate = document.createElement("span");
    rRegDate.classList.add("reply-reg-date");
    rRegDate.innerText = " 작성일: " + reply.reg_date;
    pElement.appendChild(rRegDate);
    
    //수정 버튼 표시
    const rEditLink = document.createElement("a");
    rEditLink.classList.add("reply-edit-link");
    rEditLink.href = "#"; // 수정 동작을 처리할 자바스크립트 함수 또는 URL로 연결하세요
    rEditLink.innerText = " 수정";
    //수정버튼 클릭시 발생하는 함수 이벤트
    rEditLink.onclick = function() {
    	 //event.preventDefault();
    	 
    	 if (sessionStorage.getItem('isLogined') === null) {
                alert("로그인 후 이용 가능합니다.");
                return; // 함수 실행 중단
            }
    	 
    		//세션에 저장된 로그인 정보 가져오기
    	    var userId = '${loginmember.userid}'
    	    // 댓글 작성자의 아이디 가져오기
    	    var writer_uid = this.closest(".reply-item").querySelector(".reply-writer-uid").innerText;
    	    writer_uid = writer_uid.replace(" 작성자: ", "");
    	    
    	    console.log("로그인 아이디 : ", userId);
    	    console.log("댓글 작성자 아이디: ", writer_uid);
    	 
    	   // 자기가 쓴 댓글만 수정 가능
    	    if (userId != writer_uid) {
    	        alert("자신의 게시글만 수정할 수 있습니다.");
    	        return; 
    	    }
    	   
    	   
    	    var rReplynum = this.closest(".reply-item").querySelector(".reply-num").innerText;

    	    // 수정 다이얼로그 열기
    	    $("#update_reply").data("rReplynum", rReplynum).dialog("open");
    
    }; //수정 온클릭 함수 끝
    
    //p태그안에 넣어주기
    pElement.appendChild(rEditLink);
    
    
    //삭제 버튼표시
    const rDeleteLink = document.createElement("a");
    rDeleteLink.classList.add("reply-delete-link");
    rDeleteLink.href = "#"; 
    rDeleteLink.innerText = " 삭제";
    // 삭제 버튼 클리 시 실행되는 함수
    rDeleteLink.onclick = function() {
        event.preventDefault(); 
        
        // 로그인한 사람만이 삭제 가능
        if (sessionStorage.getItem('isLogined') === null) {
            alert("로그인 후 이용 가능합니다.");
            return; // 함수 실행 중단
        }
        
      //세션에 저장된 로그인 정보 가져오기
	    var userId = '${loginmember.userid}'
	    // 댓글 작성자의 아이디 가져오기
	    var writer_uid = this.closest(".reply-item").querySelector(".reply-writer-uid").innerText;
	    writer_uid = writer_uid.replace(" 작성자: ", "");
	    
	    console.log("로그인 아이디 : ", userId);
	    console.log("댓글 작성자 아이디: ", writer_uid);
	 
	   // 자기가 쓴 댓글만 삭제 가능
	    if (userId != writer_uid) {
	        alert("자신의 게시글만 삭제할 수 있습니다.");
	        return; 
	    }
        
		// 삭제 할 댓글번호 찾기
        var reply_num = this.closest(".reply-item").querySelector(".reply-num").innerText;
		console.log(reply_num);
        
        const data = {
            replyid: reply_num
        }
        
        $.ajax({
            url: "<c:url value='/reply/delete.do'/>",
            method: "POST",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify(data),
            success: function (json) {
                alert(json.message);
                if (json.status) {
                    replyItem.remove(); // 댓글 아이템을 삭제
                }
            },
            error: function (error) {
                console.error("Error:", error);
            }
        });
    };
    //삭제 버튼 p태그안에 넣기
    pElement.appendChild(rDeleteLink);
    
	// div영역에 elenent들 추가
    replyItem.appendChild(pElement);
    replyItemContainer.appendChild(replyItem);
} 



	




















function getreplyList(boardid,startnum,endnum) {
		//태그가져오기
	 	const replyItemContainer = document.querySelector(".reply-item-container");

		const data = {
				boardid: boardid
		      };
		
		console.log("data:" , data);

		 $.ajax({
		      url: "<c:url value='/reply/replylist.do'/>",
		      method: "POST",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify(data),
		      success: function (data) {
		    	  console.log("data 어캐생겼는지좀 보자 :",data)
		    	  const replyListArray = Object.values(data.replyList)
		    	  for (let i = 0; i < replyListArray.length; i++) {
		    	        const reply = replyListArray[i];
		    	        
		    	     // 댓글을 표시할 요소 생성
		    	        const replyItem = document.createElement("div");
		    	        replyItem.classList.add("reply-item");
		    	        
		    	        // 댓글 내용 표시
		    	        const pElement = document.createElement("p");
		    	        
		    	        const rReplynum = document.createElement("span");
			            rReplynum.classList.add("reply-num");
			            rReplynum.innerText = reply.replyid; 
			            pElement.appendChild(rReplynum);

		    	        
		    	        const rContents = document.createElement("span");
		    	        rContents.classList.add("reply-contents");
		    	        rContents.innerText = " " + reply.contents;
		    	        pElement.appendChild(rContents);
		    	        
		    	        // 작성자 표시
		    	        const rWriterUid = document.createElement("span");
		    	        rWriterUid.classList.add("reply-writer-uid");
		    	        rWriterUid.innerText = " 작성자: " + reply.writer_uid;
		    	        pElement.appendChild(rWriterUid);
		    	        
		    	        // 작성일 표시
		    	        const rRegDate = document.createElement("span");
		    	        rRegDate.classList.add("reply-reg-date");
		    	        rRegDate.innerText = " 작성일: " + reply.reg_date;
		    	        pElement.appendChild(rRegDate);
		    	        
		    	        //수정
		    	        const rEditLink = document.createElement("a");
		    	        rEditLink.classList.add("reply-edit-link");
		    	        rEditLink.href = "#"; // 수정 동작을 처리할 자바스크립트 함수 또는 URL로 연결하세요
		    	        rEditLink.innerText = " 수정";
		    	        rEditLink.onclick = function() {
		    	        	 //event.preventDefault();
		    	        	 
		    	        	 if (sessionStorage.getItem('isLogined') === null) {
			    	                alert("로그인 후 이용 가능합니다.");
			    	                return; // 함수 실행 중단
			    	            }
		    	        	 
		    	        	//세션값 가져오기
		    	        	    var userId = '${loginmember.userid}'
		    	        	    var writer_uid = this.closest(".reply-item").querySelector(".reply-writer-uid").innerText;
		    	        	    writer_uid = writer_uid.replace(" 작성자: ", "");
		    	        	    console.log("userId : ", userId);
		    	        	    console.log("writer_uid : ", writer_uid);
		    	        	 
		    	        	   // 자기가 쓴 게시글만 삭제 가능
		    	        	    if (userId != writer_uid) {
		    	        	        alert("자신의 게시글만 삭제할 수 있습니다.");
		    	        	        return; // 함수 실행 중단
		    	        	    }
		    	        	   
		    	        	   
		    	        	    var rReplynum = this.closest(".reply-item").querySelector(".reply-num").innerText;

		    	        	    var replyItem = {
		    	        	        "replyid": rReplynum, // replyid에 텍스트 내용을 할당
		    	        	        "contents": reply.contents
		    	        	    };
		    	        
		    	        	   
		    	        	   
		    	        	    // 수정 다이얼로그 열기
		    	        	    $("#update_reply").data("replyItem", replyItem).dialog("open");
		    	        
		    	        }; //수정 온클릭 함수 끝
		    	        pElement.appendChild(rEditLink);
		    	        
		    	        
		    	        //삭제
		    	        const rDeleteLink = document.createElement("a");
		    	        rDeleteLink.classList.add("reply-delete-link");
		    	        rDeleteLink.href = "#"; // 삭제 동작을 처리할 자바스크립트 함수 또는 URL로 연결하세요
		    	        rDeleteLink.innerText = " 삭제";
		    	        rDeleteLink.onclick = function() {
		    	            event.preventDefault(); // 기본 링크 동작을 중단합니다.
		    	            
		    	            
		    	            
		    	            if (sessionStorage.getItem('isLogined') === null) {
		    	                alert("로그인 후 이용 가능합니다.");
		    	                return; // 함수 실행 중단
		    	            }

		    	            var reply_num = this.closest(".reply-item").querySelector(".reply-num").innerText;
							
		    	            console.log(reply_num);
		    	            
		    	            const data = {
		    	                replyid: reply_num
		    	            }
		    	            

		    	            $.ajax({
		    	                url: "<c:url value='/reply/delete.do'/>",
		    	                method: "POST",
		    	                contentType: "application/json; charset=UTF-8",
		    	                data: JSON.stringify(data),
		    	                success: function (json) {
		    	                    alert(json.message);
		    	                    if (json.status) {
		    	                        replyItem.remove(); // 댓글 아이템을 삭제
		    	                    }
		    	                },
		    	                error: function (error) {
		    	                    console.error("Error:", error);
		    	                }
		    	            });
		    	        };
		    	        pElement.appendChild(rDeleteLink);
		    	        
		    	        replyItem.appendChild(pElement);
		    	        
		    	        // 댓글을 목록에 추가
		    	        replyItemContainer.appendChild(replyItem);
		    	    } 
 				
		    	   $("#detailcontent").dialog("open");
		        
		      },
		      error: function (error) {
		        console.error("Error:", error);
		      }
		    });
		
		return false;
		
	}

</script>
</body>
</html>