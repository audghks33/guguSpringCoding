<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../includes/header.jsp" %>
      
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           	Board List Page
                           	<button id="regBtn" type="button" class="btn btn-xs pull-right">새 글</button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                    	<th>번호</th>
                                    	<th>제목</th>
                                    	<th>작성자</th>
                                    	<th>작성일</th>
                                    	<th>수정일</th>
                                    </tr>
                                </thead>
                                <c:forEach items= "${list}" var="board">  
                                <tr>
                                	<td><c:out value="${board.bno }" /></td>
                                	<!-- 새 창으로 띄우고 싶으면 a 태그에 target='_blank' -->
                                	<td><a href='/board/get?bno=<c:out value="${board.bno }" />'>
                                	<c:out value="${board.title }" /></a></td>
                                	<td><c:out value="${board.writer }" /></td>
                                	<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate }"/></td>
                                	<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate }"/></td>
                                </tr>
                                </c:forEach>
                                </table>
                                
                                <!-- Modal 게시물 등록 모달창 -->
                                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
                                aria-labelledby="myModalLabel" aria-hidden="true">
                                	<div class="modal-dialog">
                                		<div class="modal-content">
                                			<div class="modal-header">
                                				<button type="button" class="close" data-dissmiss="modal" aria-hidden="true">&times;</button>
                                				<h4 class="modal-title" id="myModalLabel">Modal title</h4>
	                                		</div>
	                                		<!-- /.modal-header -->
	                                		<div class="modal-body">처리가 완료되었습니다.</div>
	                                		<div class="modal-footer">
	                                			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	                                			<button type="button" class="btn btn-primary" >Save changes</button>
	                                		</div>
                                		</div>
                                		<!--modal-content  -->
                                	</div>
                                	<!-- /.modal-dialog -->
                                </div>
                                <!-- /.modal fade -->
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

   <%@include file="../includes/footer.jsp" %>
   <!-- jQuery 라이브러리 변경 -->
	<<script type="text/javascript">
	$(document).ready(function(){
		var result = '<c:out value="${result}"/>';
		
		checkModal(result);
		// history.state 추가로 등록->목록->조회-> 뒤로가기 시 나오는 등록팝업 처리
		// history.state 를 조건에 추가
		history.replaceState({},null,null);
		
		function checkModal(result){
			if(result ==='' || history.state){
				return;
			}
			
			if(parseInt(result) > 0) {
				$(".modal-body").html("게시글" +parseInt(result)+"빈이 등록되었습니다.");
			}
			
			$("#myModal").modal("show");
		}
		
		$("#regBtn").on("click", function(){
			self.location ="/board/register";
		})
	});
	</script>
</body>

</html>
