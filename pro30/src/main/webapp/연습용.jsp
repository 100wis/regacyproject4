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
//��۸���Ʈ ��������


// ��� ����ϴ� �Լ�
// �Լ����ڷ�  replyDTO�־������
function innertext_reply(reply){
	
	  // ����� ǥ���� ��� ����
    const replyItem = document.createElement("div");
    replyItem.classList.add("reply-item");
    
    // ��� ���� ǥ��
    const pElement = document.createElement("p");
    
    // ��� ��ȣ ǥ��
    const rReplynum = document.createElement("span");
    rReplynum.classList.add("reply-num");
    rReplynum.innerText = reply.replyid; 
    pElement.appendChild(rReplynum);

    // ��� ���� ǥ��
    const rContents = document.createElement("span");
    rContents.classList.add("reply-contents");
    rContents.innerText = " " + reply.contents;
    pElement.appendChild(rContents);
    
    // �ۼ��� ǥ��
    const rWriterUid = document.createElement("span");
    rWriterUid.classList.add("reply-writer-uid");
    rWriterUid.innerText = " �ۼ���: " + reply.writer_uid;
    pElement.appendChild(rWriterUid);
    
    // �ۼ��� ǥ��
    const rRegDate = document.createElement("span");
    rRegDate.classList.add("reply-reg-date");
    rRegDate.innerText = " �ۼ���: " + reply.reg_date;
    pElement.appendChild(rRegDate);
    
    //���� ��ư ǥ��
    const rEditLink = document.createElement("a");
    rEditLink.classList.add("reply-edit-link");
    rEditLink.href = "#"; // ���� ������ ó���� �ڹٽ�ũ��Ʈ �Լ� �Ǵ� URL�� �����ϼ���
    rEditLink.innerText = " ����";
    //������ư Ŭ���� �߻��ϴ� �Լ� �̺�Ʈ
    rEditLink.onclick = function() {
    	 //event.preventDefault();
    	 
    	 if (sessionStorage.getItem('isLogined') === null) {
                alert("�α��� �� �̿� �����մϴ�.");
                return; // �Լ� ���� �ߴ�
            }
    	 
    		//���ǿ� ����� �α��� ���� ��������
    	    var userId = '${loginmember.userid}'
    	    // ��� �ۼ����� ���̵� ��������
    	    var writer_uid = this.closest(".reply-item").querySelector(".reply-writer-uid").innerText;
    	    writer_uid = writer_uid.replace(" �ۼ���: ", "");
    	    
    	    console.log("�α��� ���̵� : ", userId);
    	    console.log("��� �ۼ��� ���̵�: ", writer_uid);
    	 
    	   // �ڱⰡ �� ��۸� ���� ����
    	    if (userId != writer_uid) {
    	        alert("�ڽ��� �Խñ۸� ������ �� �ֽ��ϴ�.");
    	        return; 
    	    }
    	   
    	   
    	    var rReplynum = this.closest(".reply-item").querySelector(".reply-num").innerText;

    	    // ���� ���̾�α� ����
    	    $("#update_reply").data("rReplynum", rReplynum).dialog("open");
    
    }; //���� ��Ŭ�� �Լ� ��
    
    //p�±׾ȿ� �־��ֱ�
    pElement.appendChild(rEditLink);
    
    
    //���� ��ưǥ��
    const rDeleteLink = document.createElement("a");
    rDeleteLink.classList.add("reply-delete-link");
    rDeleteLink.href = "#"; 
    rDeleteLink.innerText = " ����";
    // ���� ��ư Ŭ�� �� ����Ǵ� �Լ�
    rDeleteLink.onclick = function() {
        event.preventDefault(); 
        
        // �α����� ������� ���� ����
        if (sessionStorage.getItem('isLogined') === null) {
            alert("�α��� �� �̿� �����մϴ�.");
            return; // �Լ� ���� �ߴ�
        }
        
      //���ǿ� ����� �α��� ���� ��������
	    var userId = '${loginmember.userid}'
	    // ��� �ۼ����� ���̵� ��������
	    var writer_uid = this.closest(".reply-item").querySelector(".reply-writer-uid").innerText;
	    writer_uid = writer_uid.replace(" �ۼ���: ", "");
	    
	    console.log("�α��� ���̵� : ", userId);
	    console.log("��� �ۼ��� ���̵�: ", writer_uid);
	 
	   // �ڱⰡ �� ��۸� ���� ����
	    if (userId != writer_uid) {
	        alert("�ڽ��� �Խñ۸� ������ �� �ֽ��ϴ�.");
	        return; 
	    }
        
		// ���� �� ��۹�ȣ ã��
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
                    replyItem.remove(); // ��� �������� ����
                }
            },
            error: function (error) {
                console.error("Error:", error);
            }
        });
    };
    //���� ��ư p�±׾ȿ� �ֱ�
    pElement.appendChild(rDeleteLink);
    
	// div������ elenent�� �߰�
    replyItem.appendChild(pElement);
    replyItemContainer.appendChild(replyItem);
} 



	




















function getreplyList(boardid,startnum,endnum) {
		//�±װ�������
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
		    	  console.log("data ��ĳ��������� ���� :",data)
		    	  const replyListArray = Object.values(data.replyList)
		    	  for (let i = 0; i < replyListArray.length; i++) {
		    	        const reply = replyListArray[i];
		    	        
		    	     // ����� ǥ���� ��� ����
		    	        const replyItem = document.createElement("div");
		    	        replyItem.classList.add("reply-item");
		    	        
		    	        // ��� ���� ǥ��
		    	        const pElement = document.createElement("p");
		    	        
		    	        const rReplynum = document.createElement("span");
			            rReplynum.classList.add("reply-num");
			            rReplynum.innerText = reply.replyid; 
			            pElement.appendChild(rReplynum);

		    	        
		    	        const rContents = document.createElement("span");
		    	        rContents.classList.add("reply-contents");
		    	        rContents.innerText = " " + reply.contents;
		    	        pElement.appendChild(rContents);
		    	        
		    	        // �ۼ��� ǥ��
		    	        const rWriterUid = document.createElement("span");
		    	        rWriterUid.classList.add("reply-writer-uid");
		    	        rWriterUid.innerText = " �ۼ���: " + reply.writer_uid;
		    	        pElement.appendChild(rWriterUid);
		    	        
		    	        // �ۼ��� ǥ��
		    	        const rRegDate = document.createElement("span");
		    	        rRegDate.classList.add("reply-reg-date");
		    	        rRegDate.innerText = " �ۼ���: " + reply.reg_date;
		    	        pElement.appendChild(rRegDate);
		    	        
		    	        //����
		    	        const rEditLink = document.createElement("a");
		    	        rEditLink.classList.add("reply-edit-link");
		    	        rEditLink.href = "#"; // ���� ������ ó���� �ڹٽ�ũ��Ʈ �Լ� �Ǵ� URL�� �����ϼ���
		    	        rEditLink.innerText = " ����";
		    	        rEditLink.onclick = function() {
		    	        	 //event.preventDefault();
		    	        	 
		    	        	 if (sessionStorage.getItem('isLogined') === null) {
			    	                alert("�α��� �� �̿� �����մϴ�.");
			    	                return; // �Լ� ���� �ߴ�
			    	            }
		    	        	 
		    	        	//���ǰ� ��������
		    	        	    var userId = '${loginmember.userid}'
		    	        	    var writer_uid = this.closest(".reply-item").querySelector(".reply-writer-uid").innerText;
		    	        	    writer_uid = writer_uid.replace(" �ۼ���: ", "");
		    	        	    console.log("userId : ", userId);
		    	        	    console.log("writer_uid : ", writer_uid);
		    	        	 
		    	        	   // �ڱⰡ �� �Խñ۸� ���� ����
		    	        	    if (userId != writer_uid) {
		    	        	        alert("�ڽ��� �Խñ۸� ������ �� �ֽ��ϴ�.");
		    	        	        return; // �Լ� ���� �ߴ�
		    	        	    }
		    	        	   
		    	        	   
		    	        	    var rReplynum = this.closest(".reply-item").querySelector(".reply-num").innerText;

		    	        	    var replyItem = {
		    	        	        "replyid": rReplynum, // replyid�� �ؽ�Ʈ ������ �Ҵ�
		    	        	        "contents": reply.contents
		    	        	    };
		    	        
		    	        	   
		    	        	   
		    	        	    // ���� ���̾�α� ����
		    	        	    $("#update_reply").data("replyItem", replyItem).dialog("open");
		    	        
		    	        }; //���� ��Ŭ�� �Լ� ��
		    	        pElement.appendChild(rEditLink);
		    	        
		    	        
		    	        //����
		    	        const rDeleteLink = document.createElement("a");
		    	        rDeleteLink.classList.add("reply-delete-link");
		    	        rDeleteLink.href = "#"; // ���� ������ ó���� �ڹٽ�ũ��Ʈ �Լ� �Ǵ� URL�� �����ϼ���
		    	        rDeleteLink.innerText = " ����";
		    	        rDeleteLink.onclick = function() {
		    	            event.preventDefault(); // �⺻ ��ũ ������ �ߴ��մϴ�.
		    	            
		    	            
		    	            
		    	            if (sessionStorage.getItem('isLogined') === null) {
		    	                alert("�α��� �� �̿� �����մϴ�.");
		    	                return; // �Լ� ���� �ߴ�
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
		    	                        replyItem.remove(); // ��� �������� ����
		    	                    }
		    	                },
		    	                error: function (error) {
		    	                    console.error("Error:", error);
		    	                }
		    	            });
		    	        };
		    	        pElement.appendChild(rDeleteLink);
		    	        
		    	        replyItem.appendChild(pElement);
		    	        
		    	        // ����� ��Ͽ� �߰�
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