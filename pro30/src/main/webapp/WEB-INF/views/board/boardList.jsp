<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<c:url value='/resources/css/board/boardList.css' />">

<style>
    /* 링크 스타일 */
    a {
      text-decoration: none;
    }

    a:link { text-decoration: black;}
 	a:visited { text-decoration: black;}
</style>
    <h1 id="text">회원 게시판</h1>
           <!-- 게시판 목록 표시 -->
          	<div id="search">
          		<form id="Searchtitle" method="post", action="<c:url value='/board/titlesearch.do'/>" >
	          		<span>
		          		<input type="button" id="Searchbutton" value="검색" onclick="titleSearch()">
		          		<input type="text" name ="searchtext" id="searchtext"/>
	          		</span>
          		</form>
     
          		<c:if test="${loginmember != null}" >        		
          		<span><input type="button" id="insert_button" value="글쓰기"></span>
          		</c:if>
          	</div>
           <table >
	
	            <tbody id="boardList">
	            	<tr>
	            		 <th>글번호</th>
		                 <th>제목</th>
		                 <th>작성자</th>
		                 <th>작성날짜</th>
		                 <th>조회수</th>
	               	</tr>
				   	<c:forEach items="${boardList}" var="board">
	                  <tr>
		                <td>${board.boardid}</td>
						<td style="text-align: left">
							<span style="padding-left:${(board.level-1)*20}px"></span>
							${board.level != 1 ? "[답변] " : ""}
							<a href="#" onclick="dialogDetail(${board.boardid})">${board.title}</a>
							<input type="hidden" name="boardid" id="boardid" value="${board.boardid}"/> 
						</td>
		                <td>${board.writer_uid}</td>
		                <td>${board.reg_date}</td>
		                <td>${board.view_count}</td>
	                  </tr>
	                </c:forEach>
				</tbody>
				                
           	</table>
         

  <!-- 페이징 바 -->
  		<c:if test="${isSearched == false}" >       
           	<form id="pageForm" method="post" action="<c:url value='/board/list.do'/>" onsubmit="return false">
	           	<input type="hidden" name="startnum" id="startnum" />
	           	<input type="hidden" name="endnum" id="endnum" />
    			<div id="pagination" style="text-align: center;"></div>
			</form>
		</c:if>
		
		<c:if test="${isSearched == true}" >    
			<a href="<c:url value='/board/list.do'/>">전체 게시판 목록으로 돌아가기</a>
		</c:if>
          
  <!-- 게시판 상세보기 다이얼로그  -->
        <div id="detailcontent">
        	<div id = titleinfo>
        		<h5> 글번호 : <span id="boardid2"></span></h5>
			  	<h2 id="title2"></h2>
			  	<p> 
			  		작성일 : <span id="reg_date2"></span>
				  	작성자 : <span id="writer_uid2"></span>
				  	조회수 : <span id="view_count2"></span>
				</p>	
			</div>
			<div id = contents2><p>${board.contents}</p></div>
			<div id="reply">
			<input type="button" id="inser_reply_button" value="댓글쓰기">	
				<div class="reply-item-container">
			        <!-- 댓글이 추가될 위치 -->
			    </div>
				<input type="button" value="더보기" onclick="morereply()">	
			</div>
		</div>  
        

  <!-- 게시글 등록   -->
        <div id="insert_content_form" title="게시판 등록">
        	<input type="hidden" id="login_id" value="${loginmember.userid }"/>
	        <input type="text" id="title1" placeholder="제목">
	        <textarea id="contents1" placeholder="내용"></textarea>
       	</div>
       	
  <!-- 답변 등록   -->
        <div id="comment_insert" title="답변 등록" style="display: none;">
        	<input type="hidden" id="write_userid" value="${loginmember.userid }"/>
	        <input type="text" id="title3" placeholder="제목">
	        <textarea id="contents3" placeholder="내용"></textarea>
       	</div>
      
        	
 <!-- 게시글 수정하기   -->
        <div id="update_board" title="게시글 수정하기" style="display: none;">
	        <input type="text" id="title4" placeholder="제목">
	        <textarea id="contents4" placeholder="내용"></textarea>
       	</div>

 <!-- 댓글 작성하기   -->
        <div id="insert_reply" title="댓글 작성하기" style="display: none;">
	        <input type="text" id="insert_reply_contents" placeholder="댓글내용">
       	</div>
       	
 <!-- 댓글 수정하기   -->
        <div id="update_reply" title="댓글 수정하기" style="display: none;">
	        <input type="text" id="update_reply_contents" placeholder="댓글내용">
       	</div>
       	
           	

       
<script src="<c:url value='/resources/js/check.js'/>"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>

<script type="text/javascript">


/* function boardid_return(boardid) {
    console.log("return값 맞는지 확인: " + boardid);
    return boardid;
} */

//페이지당 항목 수와 현재 페이지 번호를 설정합니다.
var itemsPerPage = 10;
var currentPage = 1;

//전체 항목 수를 JavaScript 변수로 가져옵니다.
var totalItems = parseInt("${totalcount}");
console.log(totalItems);

//총 페이지 수를 계산합니다.
var totalPages = Math.ceil(totalItems / itemsPerPage);
console.log(totalPages);


//페이징 번호를 생성하는 함수를 정의합니다.
function generatePagination() {
 var paginationHtml = '';
 for (var i = 1; i <= totalPages; i++) {
     // 각 페이지 번호를 클릭할 때 해당 페이지로 이동하도록 설정합니다.
     paginationHtml += '<input type="submit" id="pagenum" onclick="changePage('+ i + ')" value="' + i + '" /> ';
 }
 // 생성된 페이지 번호를 pagination div에 추가합니다.
 document.getElementById('pagination').innerHTML = paginationHtml;
}


window.onload = function() {
    generatePagination();
};


function changePage(pageNumber) {
	
    const startnum = (pageNumber - 1) * 10 + 1;
    const endnum = pageNumber * 10;
    
    console.log("startnum : " + startnum);
    console.log("endnum : " + endnum);

    // startnum과 endnum을 폼에 업데이트
    document.getElementById('startnum').value = startnum;
    document.getElementById('endnum').value = endnum;

    // 폼을 제출하는 기본 동작을 막고, 원하는 동작을 수행
    document.getElementById('pageForm').submit();
}



//게시판 내용 상세보기 다이얼로그
	$("#detailcontent").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 800,
	    height: 500,
	    buttons: {
	    	"답글 작성하기": Comment,
	    	"수정": Update,
	        "삭제": Delete,
	        Close: function() {
	        	 removeAllReplies();
	            $(this).dialog("close");
	        }
	    },
	    close: function() {
	        // 다이얼로그가 닫힐 때도 removeAllReplies() 함수 호출
	        removeAllReplies();
	    }
	
	});

//게시글 등록 다이얼로그   
	$("#insert_content_form").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 400,
	    buttons: {
	    	"등록": Insert,
	        Close: function() {
	            $(this).dialog("close");
		        }
		    }
		});

//게시글 등록 다이얼로그  열기									
	$("#insert_button").on("click", function () {
		$("#insert_content_form").dialog("open");
	});
	
// 댓글 등록 다이얼로그   
	$("#insert_reply").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 400,
	    buttons: {
	    	"댓글 등록": Insert_reply,
	        Close: function() {
	            $(this).dialog("close");
		        }
		    }
		});
		
//댓글 등록 다이얼로그  열기									
	$("#inser_reply_button").on("click", function () {
		

		 // 로그인 상태를 확인하고 동작을 수행합니다.
	   if (sessionStorage.getItem('isLogined') === null) {
	       alert("로그인 후 이용 가능합니다.");
	       return; // 함수 실행 중단
	   }
		 
		$("#insert_reply").dialog("open");
	});
	
// 댓글 수정하기 다이얼로그	
	$("#update_reply").dialog({
    autoOpen: false,
    modal: true,
    buttons: {
        "수정하기": function() {
            // 수정된 내용 가져오기
            var editedContents = $("#update_reply_contents").val();
            
            //수정할 댓글 번호 가져오기
          	var replyNum = $(this).data("rReplynum");
            console.log(replyNum);
            
    
            
      /*       // replyNum을 사용하여 원하는 작업을 수행합니다.
            console.log("부모 replyItem " ,replyItem);
            console.log("수정할 댓글의 replyNum: " ,replyNum); */
           
		var data = {
                replyid: replyNum,
                contents: editedContents
            };

            $.ajax({
                url: "<c:url value='/reply/update.do'/>",
                method: "POST",
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify(data),
                success: function (json) {
		    alert(json.message);
                    if (json.status) {
                    	
                    	const replyItemContainer = document.querySelector(".reply-item-container");          
                    	const replyItems = replyItemContainer.querySelectorAll(".reply-item");
                    	replyItems.forEach(function(replyItem) {
                    	    // 댓글 요소에서 댓글 ID를 가져옵니다. (댓글 ID는 'reply-num' 클래스를 가진 요소에서 가져온다고 가정합니다.)
                    	    const replyId = replyItem.querySelector(".reply-num").innerText;
								
                    	    console.log("userid : " , json.updated_reply.replyid);
                    	    // 댓글 ID를 정수로 변환한 후 삭제 대상 댓글 ID와 비교합니다.
                    	    if (replyId == json.updated_reply.replyid) {
                    	        // 일치하는 댓글 요소를 삭제합니다.
                    	        replyItemContainer.removeChild(replyItem);
                    	    }
                    	});
                    	
                    	
                        var reply = json.updated_reply;
                        innertext_reply(reply)
                    }

                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });

            // 다이얼로그 닫기
            $(this).dialog("close");
        },
        "취소": function() {
            $(this).dialog("close");
        }
    }
});
	
    
//게시판 상세글 가져오기
function dialogDetail(boardid) {
	
	
	 	const boardid2 = document.querySelector("#boardid2");
		const title2 = document.querySelector("#title2");
		const contents2 = document.querySelector("#contents2");
		const writer_uid2 = document.querySelector("#writer_uid2");
		const reg_date2 = document.querySelector("#reg_date2");
		const view_count2 = document.querySelector("#view_count2");
		
		console.log("boardid2:", boardid2);
	    console.log("title2:", title2);
	    console.log("contents2:", contents2);
	    console.log("writer_uid2:", writer_uid2);
	    console.log("reg_date2:", reg_date2);
	    console.log("view_count2:", view_count2);
		
	
		console.log("boardid:", boardid);
		
		const data = {
				boardid: boardid 
		      };
		
		console.log("data:" , data);

		 $.ajax({
		      url: "<c:url value='/board/detail.do'/>",
		      method: "POST",
		      contentType: "application/json; charset=UTF-8",
		      data: JSON.stringify(data),
		      success: function (data) {
		      
		           boardid2.innerText = data.boardid;  
		    	   title2.innerText = data.title;  
		    	   contents2.innerText = data.contents;  
		    	   writer_uid2.innerText = data.writer_uid;  
		    	   reg_date2.innerText = data.reg_date;  
		    	   view_count2.innerText = data.view_count;  
		    	   getreplyList(data.boardid)
		        
		      },
		      error: function (error) {
		        console.error("Error:", error);
		      }
		    });
		
		return false;
		
	}	

// 게시글 등록
function Insert() {
	
	 // 로그인 상태를 확인하고 동작을 수행합니다.
    if (sessionStorage.getItem('isLogined') === null) {
        alert("로그인 후 이용 가능합니다.");
        return; // 함수 실행 중단
    }
	
	const boardtitle =$("#insert_content_form #title1").val()
	const boardcontents =$("#insert_content_form #contents1").val()
	const writer_uid = $("#insert_content_form #login_id").val()
	
	console.log("boardtitle:" + boardtitle);
	console.log("boardcontents:" + boardcontents);
	console.log("writer_uid:" + writer_uid);
	
	const data = {
			title: boardtitle,
			contents: boardcontents,
			writer_uid: writer_uid	
	    };
	
	console.log("data:", data);
	
	 $.ajax({
 	      url: "<c:url value='/board/insert.do'/>",
 	      method: "POST",
 	      contentType: "application/json; charset=UTF-8",
 	      data: JSON.stringify(data),
 	      success: function (json) {
 	        alert(json.message);
 	        location.href = "<c:url value='/board/list.do'/>";
 	      },
 	      error: function (error) {
 	        console.error("Error:", error);
 	      }
 	    });
	 return false;
}


//답변 등록
function Comment() {
	
	 // 로그인 상태를 확인하고 동작을 수행합니다.
    if (sessionStorage.getItem('isLogined') === null) {
        alert("로그인 후 이용 가능합니다.");
        return; // 함수 실행 중단
    }
	  
	// 답변 작성 창 다이얼로그를 열고		
	$("#comment_insert").dialog({
	    autoOpen: true, // 수정: 다이얼로그를 열 때 autoOpen을 true로 설정
	    modal: true,
	    width: 500,
	    height: 400,
	    buttons: {
	    	"답변 등록" : function(){
	    		const pid_boardid = $("#boardid2").text();
	    		const title = $("#title3").val();
	    		const contents = $("#contents3").val(); // 수정: contents3 선택자 수정
	    		const writer_uid = $("#write_userid").val();
	    		
	    		console.log("답변 게시글 boardid: ", pid_boardid);
	    		
	    		const data = {
	    			pid : pid_boardid,
	    			title: title, // 수정: 변수 이름 수정
	    			contents: contents, // 수정: 변수 이름 수정
	    			writer_uid: writer_uid	
	    	    };
	    		
	    		
	    		console.log("data:" + data);
	    		
	    		 $.ajax({
	    	 	      url: "<c:url value='/board/insert.do'/>",
	    	 	      method: "POST",
	    	 	      contentType: "application/json; charset=UTF-8",
	    	 	      data: JSON.stringify(data),
	    	 	      success: function (json) {
	    	 	        alert(json.message);
	    	 	        location.href = "<c:url value='/board/list.do'/>";
	    	 	      },
	    	 	      error: function (error) {
	    	 	        console.error("Error:", error);
	    	 	      }
	    	 	    });

	    	},
	        Close: function() {
	            $(this).dialog("close");
		        }
		    }
		});
	//return false;	
}


// 게시글 수정하기
function Update() {
	
	 // 로그인 상태를 확인하고 동작을 수행합니다.
    if (sessionStorage.getItem('isLogined') === null) {
        alert("로그인 후 이용 가능합니다.");
        return; // 함수 실행 중단
    }
    
	 //세션값 가져오기
    var userId = '${loginmember.userid}'
    var writer_uid = $("#writer_uid2").text()
    
    console.log("userId : ", userId);
    console.log("writer_uid2 : ", writer_uid);
    
    if (userId != writer_uid) {
        alert("자신의 게시글만 수정할 수 있습니다.");
        return; // 함수 실행 중단
    }

	// 답변 작성 창 다이얼로그를 열고		
	$("#update_board").dialog({
	    autoOpen: true, // 수정: 다이얼로그를 열 때 autoOpen을 true로 설정
	    modal: true,
	    width: 500,
	    height: 400,
	    buttons: {
	    	"수정하기" : function(){
	    		const boardid = $("#boardid2").text()
	    		const title = $("#title4").val();
	    		const contents = $("#contents4").val(); 

	    		console.log("수정하기 boardid: ", boardid);
	    		
	    		const data = {
	    			boardid : boardid,
	    			title: title, 
	    			contents: contents, 
	    	    };

	    		console.log("data:" + data);
	    		
	    		 $.ajax({
	    	 	      url: "<c:url value='/board/update.do'/>",
	    	 	      method: "POST",
	    	 	      contentType: "application/json; charset=UTF-8",
	    	 	      data: JSON.stringify(data),
	    	 	      success: function (json) {
	    	 	        alert(json.message);
	    	 	        location.href = "<c:url value='/board/list.do'/>";
	    	 	      },
	    	 	      error: function (error) {
	    	 	        console.error("Error:", error);
	    	 	      }
	    	 	    });

	    	},
	        Close: function() {
	            $(this).dialog("close");
		        }
		    }
		});
	//return false;	
}
	

function Delete() {
	 // 로그인 상태를 확인하고 동작을 수행합니다.
    if (sessionStorage.getItem('isLogined') === null) {
        alert("로그인 후 이용 가능합니다.");
        return; // 함수 실행 중단
    }
	 
    //세션값 가져오기
    var userId = '${loginmember.userid}'
    var writer_uid = $("#writer_uid2").text()
    
    console.log("userId : ", userId);
    console.log("writer_uid2 : ", writer_uid);
    
    // 자기가 쓴 게시글만 삭제 가능
    if (userId != writer_uid) {
        alert("자신의 게시글만 삭제할 수 있습니다.");
        return; // 함수 실행 중단
    }

	const boardid = $("#boardid2").text()
	
	console.log("수정하기 boardid: ", boardid);
	
	const data = {
		boardid : boardid,
    };

	console.log("data:" + data);
	
	 $.ajax({
 	      url: "<c:url value='/board/delete.do'/>",
 	      method: "POST",
 	      contentType: "application/json; charset=UTF-8",
 	      data: JSON.stringify(data),
 	      success: function (json) {
 	        alert(json.message);
 	        location.href = "<c:url value='/board/list.do'/>";
 	      },
 	      error: function (error) {
 	        console.error("Error:", error);
 	      }
 	    });
	
}


function titleSearch() {  
	
	const searchtext = $("#searchtext").val() ;

	console.log("searchtext : " + searchtext);


	// startnum과 endnum을 폼에 업데이트
	document.getElementById('searchtext').value = searchtext;


	// 폼을 제출하는 기본 동작을 막고, 원하는 동작을 수행
	document.getElementById('Searchtitle').submit();
}

//댓글리스트 가져오기
function getreplyList(boardid) {

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
		    	        // 댓글 추가 함수
		    	        innertext_reply(reply);

		    	    } 
 				
		    	   $("#detailcontent").dialog("open");
		        
		      },
		      error: function (error) {
		        console.error("Error:", error);
		      }
		    });
		
		return false;
		
	}

// 댓글 추가하기
function Insert_reply(){

	
	const boardid = $("#boardid2").text();
	const insert_reply_contents = $("#insert_reply_contents").val();
	const writer_uid = $("#write_userid").val();
	
	console.log("boardid : ", boardid)
	console.log("insert_reply_contents : ", insert_reply_contents)
	
	data = {
		boardid : boardid,
		contents : insert_reply_contents,
		writer_uid: writer_uid	
	};
	
	 $.ajax({
	        url: "<c:url value='/reply/insert.do'/>",
	        method: "POST",
	        contentType: "application/json; charset=UTF-8",
	        data: JSON.stringify(data),
	        success: function (json) {
	            // 새로운 댓글을 화면에 추가하는 부분
	            const replyItemContainer = document.querySelector(".reply-item-container");

	            const replyItem = document.createElement("div");
	            replyItem.classList.add("reply-item");

	            const pElement = document.createElement("p");
	            
	            const rReplynum = document.createElement("span");
	            rReplynum.classList.add("reply-num");
	            rReplynum.innerText = json.inserted_reply.replyid +"번 "; // 새로운 댓글 내용 추가
	            pElement.appendChild(rReplynum);

	            const rContents = document.createElement("span");
	            rContents.classList.add("reply-contents");
	            rContents.innerText = insert_reply_contents; // 새로운 댓글 내용 추가
	            pElement.appendChild(rContents);

	            const rWriterUid = document.createElement("span");
	            rWriterUid.classList.add("reply-writer-uid");
	            console.log("작성자 : ",json.inserted_reply.writer_uid)
	            rWriterUid.innerText = " 작성자: " + json.inserted_reply.writer_uid;
	            pElement.appendChild(rWriterUid);

	            const rRegDate = document.createElement("span");
	            rRegDate.classList.add("reply-reg-date");
	            rRegDate.innerText = " 작성일: " + json.inserted_reply.reg_date;
	            pElement.appendChild(rRegDate);

	            replyItem.appendChild(pElement);

	            // 새로운 댓글을 목록의 맨 위에 추가
	            replyItemContainer.insertBefore(replyItem, replyItemContainer.firstChild);

	          /*   // 현재 댓글 목록이 10개 이상이라면 가장 아래의 댓글 삭제
	            if (replyItemContainer.children.length > 10) {
	                replyItemContainer.removeChild(replyItemContainer.lastChild);
	            } */
	        },
	        error: function (error) {
	            console.error("Error:", error);
	        }
	    });
	}
	  

// 게시물 상세보기 닫으면 하면 댓글 삭제하기
function removeAllReplies() {
    // 댓글 목록을 감싸는 부모 요소를 선택
    const replyItemContainer = document.querySelector(".reply-item-container");

    // 모든 댓글 요소들을 선택
    const replyItems = replyItemContainer.querySelectorAll(".reply-item");

    // 각 댓글 요소를 순회하면서 삭제
    replyItems.forEach(function(replyItem) {
        replyItemContainer.removeChild(replyItem);
    });
}


function morereply() {
    // 댓글 목록을 감싸는 부모 요소를 선택
    const replyItemContainer = document.querySelector(".reply-item-container");

    // 댓글리스트 갯수 가져오기
    const numberOfReplyNums = $(".reply-num").length;
    const startnum = numberOfReplyNums + 1;
    const endnum = numberOfReplyNums + 10;
    const boardid = $("#boardid2").text();

    const data = {
        boardid: boardid,
        startnum: startnum,
        endnum: endnum
    };
    
    console.log("총 댓글 갯수 : ",numberOfReplyNums);
    console.log("스타트 번호 : ",startnum );
    console.log("끝 row : ",endnum );

    $.ajax({
        url: "<c:url value='/reply/replylist.do'/>",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(data),
        success: function (data) {
            console.log("data 어캐생겼는지좀 보자 :", data)
            const replyListArray = Object.values(data.replyList)
            for (let i = 0; i < replyListArray.length; i++) {
                const reply = replyListArray[i];
                
                //댓글 추가 함수 
                innertext_reply(reply);
            }

        },
        error: function (error) {
            console.error("Error:", error);
        }
    });
}





//댓글 출력하는 함수
//함수인자로  replyDTO넣어줘야함
function innertext_reply(reply){
	
const replyItemContainer = document.querySelector(".reply-item-container");
	
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

</script>
