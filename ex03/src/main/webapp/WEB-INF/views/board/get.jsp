<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>


<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Register</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">Board Read Page</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

		<div class="form-group">
          <label>Bno</label> <input class="form-control" name='bno'
           value= '<c:out value="${board.bno }" />' readonly="readonly" >
        </div>
        <div class="form-group">
          <label>Title</label> <input class="form-control" name='title'
          value= '<c:out value="${board.title }" />' readonly="readonly" >
        </div>

        <div class="form-group">
          <label>Text area</label>
          <textarea class="form-control" rows="3" name='content'
            readonly="readonly" ><c:out value="${board.content}" /> </textarea>
        </div>

        <div class="form-group">
          <label>Writer</label> <input class="form-control" name='writer'
          value= '<c:out value="${board.writer }" />' readonly="readonly" >
        </div>
        <button data-oper='modify' class="btn btn-default" 
        <%-- 단순 링크에서 추후 다양한 상황처리릉 위한 form 이동
        onclick="location.href='/board/modify?bno=<c:out value="${board.bno}" />'" --%>
        >변경 Button</button>
        <button data-oper='list' class="btn btn-info"
        <%--  단순 링크에서 추후 다양한 상황처리릉 위한 form 이동
        onclick="location.href='/board/list'" --%> 
        > List Button</button>
        
        <form id="operForm"action="/board/modify" method="get">
        	<input type="hidden" id="bno" name="bno" value='
        	<c:out value="${board.bno}"/>'>
        	<input type="hidden" name='pageNum' value='<c:out value="${cri.pageNum}" />' >
        	<input type="hidden" name='amount' value='<c:out value="${cri.amount}" />' >
        	<!-- 검색해서 해당 게시글을 골랐다가 리스트로 돌아갈때 확인내용  -->
        	<input type="hidden" name='keyword' value='<c:out value="${cri.keyword}" />' >
        	<input type="hidden" name='type' value='<c:out value="${cri.type}" />' >
        	<!-- 검색해서 해당게시글을 골랐다가 리스트로 돌아갈때 확인내용 -> modify로 -->
        	<!-- modify로 -->
        	
        	
        </form>

      </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->

<div class='row'>
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class ="fa fa-comments fa-fw"></i> Reply
				<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'> 댓글쓰기</button>
			</div>
			
<!-- modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby = "myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class= "modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel"> 댓글 창</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>댓글</label>
						<input class="form-control" name ='reply' value='새 댓글쓰시오'>
					</div>
					<div class="form-group">
						<label>작성자</label>
						<input class="form-control" name='replyer'>
					</div>
					
					<div class="form-group">
						<label>작성일자</label>
						<input class="form-control" name='replyDate'>
					</div>
					
				</div>
				<div class="modal-footer">
					<button id='modalModBtn' type="button" class="btn btn-warning">변경</button>
					<button id='modalRemoveBtn' type='button' class='btn btn-danger'>삭제</button>
					<button id='modalRegisterBtn' type="button" class="btn btn-primary">등록</button>
					<button id='modalCloseBtn' type='button' class='btn btn-default'>닫기</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
<!-- /.modal -->
			
			<div class="panel-body">
				<ul class="chat">
					<!-- start reply -->
					<li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">user00</strong>
								<small class="pull-right text-muted">2018-01-01 13:13</small>
							</div>
						<p> Goodjob!</p>
						</div>
					</li>
					<!--  end reply -->
				</ul>
				<!-- /.end ul -->
			</div>
			<!-- /.chat-panel -->
			
			<div class="panel-footer"></div>
		</div>
		<!-- /.panel panel-default -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->


<script type="text/javascript" src="/resources/js/reply.js">
</script>
	
<script>
$(document).ready(function(){
	
	var bnoValue = '<c:out value="${board.bno}"/>';
	var replyUL = $(".chat");
	
	showList(1);
	
	function showList(page){
		replyService.getList({bno:bnoValue,page:page||1 }, function(replyCnt,list){
			///////// 페이징을 위한 카운트를 포함한 추가부분
			console.log("show list " +page);
			
			replyService.getList({bno:bnoValue,page: page|| 1 },
			function(replyCnt,list) {
					
				console.log("replyCnt: " + replyCnt);
				console.log("list:  " +list);
				console.log(list);
				
				if(page== -1){
					pageNum = Math.ceil(replyCnt/10.0);
					showList(pageNum);
					return;
				}
				
			})
			///////// 페이징을 위한 카운트를 포함한 추가부분
			
			
			var str ="";
			if(list ==null || list.length == 0){
				replyUL.html("");
				
				return;
			}
			for(var i = 0 , len = list.length || 0; i < len; i++){
				str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>";
				str += "<div><div class='header'><strong class='primary-font'>"+list[i].replyer+
				"</strong>";
				str += "  <small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
				str += "  <p>"+list[i].reply+"</p></div></li>";
			}//p.419
			
			replyUL.html(str);
			
			//페이징을 위한 카운트 포함한 추가부분
			showReplyPage(replyCnt);
			
			});
	}
	
	var modal =$(".modal");
	var modalInputReply = modal.find("input[name='reply']");
	var modalInputReplyer = modal.find("input[name='replyer']");
	var modalInputReplyDate = modal.find("input[name='replyDate']");
	
	var modalModBtn = $("#modalModBtn");
	var modalRemoveBtn = $("#modalRemoveBtn");
	var modalRegisterBtn = $("#modalRegisterBtn");
	
	$("#modalCloseBtn").on("click", function(e){
		
		modal.modal('hide');
	});
	
	$("#addReplyBtn").on("click", function(e){
		
		modal.find("input").val("");
		modalInputReplyDate.closest("div").hide();
		modal.find("button[id !='modalCloseBtn']").hide();
		
		 modalRegisterBtn.show();
		
		$(".modal").modal("show");
	});
	
	modalRegisterBtn.on("click", function(e){
		
		var reply = {
				reply:modalInputReply.val(),
				replyer:modalInputReplyer.val(),
				bno:bnoValue
		};
		
		replyService.add(reply, function(result){
			
			alert(result);
			
			modal.find("input").val("");
			modal.modal("hide");
			
			//showList(1); 댓글 페이징을 위한 카운트 추가 이후 
			showList(-1);
		});
	});
	
	$(".chat").on("click", "li",function(e){
		
		var rno = $(this).data("rno");
		
		replyService.get(rno,function(reply){
			
			modalInputReply.val(reply.reply);
			modalInputReplyer.val(reply.replyer);
			modalInputReplyDate.val(replyService.displayTime(reply.replyDate))
			.attr("readonly","readonly");
			modal.data("rno", reply.rno);
			
			modal.find("button[id != 'modalCloseBtn']").hide();
			modalModBtn.show();
			modalRemoveBtn.show();
			
			$(".modal").modal("show");
		});
	});
	
	modalModBtn.on("click", function(e){
		
		var reply = {rno:modal.data("rno"), reply:modalInputReply.val()};
		
		replyService.update(reply, function(result){
			
			alert(result);
			modal.modal("hide");
			showList(1);
		});
	});
	
	modalRemoveBtn.on("click", function(e){
		
		var rno = modal.data("rno");
		
		replyService.remove(rno, function(result){
			
			alert(result);
			modal.modal("hide");
			showList(1);
			
		});
		// 이후 정렬로 시간을 낭비하지 않는  인덱스를 생성해서 조회용으로 쓴다.
		//create index idx_reply on tbl_reply(bno desc, rno asc);
	});
	
	var pageNum =1;
	var replyPageFooter = $(".panel-footer");
	
	function showReplyPage(replyCnt){
		
		var endNum = Math.ceil(pageNum / 10.0) * 10;
		var startNum = endNum -9;
		
		var prev = startNum != 1;
		var next =false;
		
		if(endNum * 10 >= replyCnt){
			endNum = Math.ceil(replyCnt/10.0);
		}
		
		if(endNum * 10 < replyCnt){
			next = true;
		}
		
		var str = "<ul class='pagination pull-right'>";
		
		if(prev){
			str+= "<li class ='page-item'><a class='page-link' href='"+(startNum-1)+"'>Previous</a></li>";
		}
		
		for(var i  = startNum ; i <= endNum; i++){
			
			var active = pageNum == i ? "active":"";
			
			str+="<li class='page-item "+active+" '><a class='page-link'href='"+i+"'>"+i+"</a></li>"; 
		}
		
		if(next){
			str+="<li class='page-item'><a class='page-link' href='"+(endNum+1)+"'>next</a></li>";
			
		}
		
		str+= "</ul></div>";
		console.log(str);
		replyPageFooter.html(str);
	}
	//댓글 페이지 누를시 해당 댓글페이지 보이도록
	replyPageFooter.on("click", "li a", function(e){
		
		e.preventDefault();
		console.log("page click");
		
		var targetPageNum = $(this).attr("href");
		
		console.log("targetPageNum:  "+targetPageNum);
		
		pageNum = targetPageNum;
		
		showList(pageNum);
	});
	
	modalModBtn.on("click", function(e){
		
		var reply={rno:modal.data("rno"), reply:modalInputReply.val()};

		replyService.update(reply, function(result){
			
			alert(result);
			modal.modal("hide");
			showList(pageNum);
		});
	});
	
	modalRemoveBtn.on("click", function(e){
		
		var rno = modal.data("rno");
		
		replyService.remove(rno, function(result){
			
			alert(result);
			modal.modal("hide");
			showList(pageNum);
		});
	});
	
});
</script>

<script type="text/javascript" >
	$(document).ready(function(){
		/* console.log(replyService); */
		console.log("==================");
		console.log("JS TEST");
		
		var bnoValue ='<c:out value="${board.bno}"/>';
		
		/* replyService.add(
			{reply:"JS TEST", replyer:"tester",bno:bnoValue}
			,
			function(result){
				alert("Result: " + result);
			}
			/replies/pages/게시물번호/페이지번호로 먼저 대강적인 모습 확인 가능 
		) */
		
/* 		replyService.getList({bno:bnoValue,page:1},function(list){
			
			for(var i =0 , len = list.length||0; i< len; i++ ){
				console.log(list[i]);
			}
			
		});
		 */
/* 		replyService.remove(14,function(count){
			console.log(count);
			
			if(count=== "success"){
				alert("제거됨");
			}
		},function(err){
			alert("에러");
			
		}); */
		
/* 		replyService.update({
			rno: 12,
			bno: bnoValue,
			reply: "변경된 리플..?"
		}, function(result){
			alert("변경완료");
		});
					
		
		replyService.get(12,function(data){
			console.log(data);
		});
		 */
		//이후 이벤트 감지하고 ajax 호출
	});
</script>

<script type="text/javascript">
	$(document).ready(function(){
		
		var operForm = $("#operForm");
		
		$("button[data-oper='modify']").on("click", function(e){
			operForm.attr("action", "/board/modify").submit();
		});
		
		$("button[data-oper='list']").on("click", function(e){
			operForm.find('#bno').remove();
			operForm.attr("action", "/board/list").submit();
			operForm.submit();
			//리스트로 돌아 갔을때 bno정보를 없앰
			//11강 끝
		});
		
	});
	
</script>

<%@include file="../includes/footer.jsp"%>
