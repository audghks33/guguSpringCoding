<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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

      <div class="panel-heading">Board Modi Page</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

		<form role="form" action="/board/modify" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<input type ='hidden' name="pageNum" value='<c:out value="${cri.pageNum }"/>'>
		<input type ='hidden' name="amount" value='<c:out value="${cri.amount }"/>'>
		<!-- 검색 후 찾아온 게시글을 변경할때 해당 검색내용 저장 -->
		<input type="hidden" name ="type" value='<c:out value="${cri.type}"/>' >
		<input type="hidden" name ="keyword" value='<c:out value="${cri.keyword}"/>' >
		<!--  controller로  -->
		<!-- hidden 추가 후 controller modify post -->
		
		<div class="form-group">
          <label>Bno</label> 
          <input class="form-control" name='bno'
           value= '<c:out value="${board.bno }" />' readonly="readonly" >
        </div>
        <div class="form-group">
          <label>Title</label> <input class="form-control" name='title'
          value= '<c:out value="${board.title }" />'  >
        </div>

        <div class="form-group">
          <label>Text area</label>
          <textarea class="form-control" rows="3" name='content'
             ><c:out value="${board.content}" /> </textarea>
        </div>

        <div class="form-group">
          <label>Writer</label> <input class="form-control" name='writer'
          value= '<c:out value="${board.writer }" />' readonly="readonly" >
        </div>
        
        <div class="form-group">
          <label>RegDate</label> 
          <input class="form-control" name='regDate'
          value='<fmt:formatDate pattern= "yyyy/MM/dd"  value="${board.regdate}" />' readonly="readonly" >
        </div>
        
    
         <div class="form-group">
          <label>update Date</label> 
          <input class="form-control" name='updateDate'
  		  value='<fmt:formatDate pattern= "yyyy/MM/dd"  value="${board.updateDate}" />' readonly="readonly" >
        </div>
        
        
        <sec:authentication property="principal" var="pinfo" />
        <sec:authorize access="isAuthenticated()">
        <c:if test="${pinfo.username eq board.writer}">
        <button type="submit" data-oper='modify'  class="btn btn-default" 
       	>변경 </button>
       	<button type="submit" data-oper='remove'  class="btn btn-danger" 
       	>삭제</button>
       	</c:if>
       	</sec:authorize>
          
        <button type="submit" data-oper='list' class="btn btn-info"
        >List Button</button>

		</form>
      </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>


<div class='bigPictureWrapper'>
	<div class='bigPicture'>
	
	</div>
</div>


<style>
.uploadResult{
	width:100%;
	background-color : gray;
}

.uploadResult ul{
	display: flex;
	flex-flow :row;
	justify-content:center;
	align-items:center;
}

.uploadResult ul li {
	list-style : none;
	padding: 10px;
	align-content: center;
	text-align: center;
}

.uploadResult ul li img{
	width: 20px;
}

.uploadReulst ul li span {
	color: white;
}

.bigPictureWrapper{
	position: absolute;
	diplay:none;
	justify-content: center;
	align-items: center;
	top:0%;
	height:100%;
	background-color: gray;
	z-index:100;
	background:rgba(255,255,255,0.5);
}
.bigPicture{
	position: relative;
	display:flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img{
	width:600px;
}

</style>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Files</div>
			  <div class="panel-body">
			<div class="form-group uploadDiv">
            	<input type="file" name='uploadFile' multiple="multiple">
       		</div>
			
			<div class="uploadResult">
				<ul>
				</ul>
			</div>
			<!--  -->
			</div>
		</div>
		<!-- /.panel panel-default -->
	</div>
	<!-- /. col-lg-12 -->
</div>
<!-- /.row -->

<script >
	$(document).ready(function(){
		(function(){
			/* console.log(replyService); */
			console.log("==================");
			console.log("JS TEST");
			
			var bno ='<c:out value="${board.bno}"/>';
			
			//업로드 이미지 보이게 하기
			$.getJSON("/board/getAttachList", {bno: bno}, function(arr){
				
				console.log("arr"+ arr);
				
				var str ="";
				
				$(arr).each(function(i, attach){
						
						  if(attach.fileType){
					            var fileCallPath =  encodeURIComponent( attach.uploadPath+ "/s_"+attach.uuid +"_"+attach.fileName);
					            
					            str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' "
					            str +=" data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
					            str += "<span> "+ attach.fileName+"</span>";
					            str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image' "
					            str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
					            str += "<img src='/display?fileName="+fileCallPath+"'>";
					            str += "</div>";
					            str +"</li>";
					          }else{
					              
					            str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' "
					            str += "data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
					            str += "<span> "+ attach.fileName+"</span><br/>";
					            str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' "
					            str += " class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
					            str += "<img src='/resources/img/attach.png'></a>";
					            str += "</div>";
					            str +"</li>";
					          }
					       });
				
				$(".uploadResult ul").html(str);
				
			});//end getJson
		
		})();
	});
</script>

<script type="text/javascript">
$(document).ready(function() {
	

	  var formObj = $("form");

	  $('button').on("click", function(e){
	    
	    e.preventDefault(); 
	    
	    var operation = $(this).data("oper");
	    
	    console.log(operation);
	    
	    if(operation === 'remove'){
	      formObj.attr("action", "/board/remove");
	      
	    }else if(operation === 'list'){
	      //move to list
	      formObj.attr("action", "/board/list").attr("method","get");
	      
	      var pageNumTag = $("input[name='pageNum']").clone();
	      var amountTag = $("input[name='amount']").clone();
	      var keywordTag = $("input[name='keyword']").clone();
	      var typeTag = $("input[name='type']").clone();      
	      
	      formObj.empty();
	      
	      formObj.append(pageNumTag);
	      formObj.append(amountTag);
	      formObj.append(keywordTag);
	      formObj.append(typeTag);	       
	      
	    }else if(operation === 'modify'){
	    	
	    	console.log("sumbit clicked");
	    	
	    	var str = "";
	    	
	    	$(".uploadResult ul li").each(function(i, obj){
	    		
	    		var jobj= $(obj);
	    		
	    		//console.log HTML과 같은 트리에서 요소를 인쇄합니다
				//console.dir JSON과 같은 트리에서 요소를 인쇄합니다.
	    		console.dir(jobj);
	    		
	    		str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
				console.log("히든 1: " +str);
				str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
				console.log("히든 2: " +str);
				str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
				console.log("히든 3: " +str);
				str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
				console.log("히든 4: " +str);
	    	});
	    	formObj.append(str).submit();
	    }//BoardServiceImpl
	    
	    formObj.submit();
	  });
	  
	  //첨부파일 삭제 버튼
	  $(".uploadResult").on("click", "button", function(e){
		  
		  console.log("첨부파일 삭제");
		  
		  if(confirm("이 파일을 삭제하실래요? ")){
			  var targetLi = $(this).closest("li");
			  targetLi.remove();
		  }
		  
	  });
	  
	  //파일 종류/사이즈 확인
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|avi)$");
		var maxSize = 5242880;
		
		function checkExtension(fileName, fileSize){
			
			if(fileSize >= maxSize){
				alert("파일 사이즈 초과");
				return false;
			}
			
			if(regex.test(fileName)){
				
				alert("해당 종류의 파일은 업로드 할 수 없습니다.");
				return false;
			}
			return true;
		}
		
		//파일업로드 버튼을 눌렀을 때
		var csrfHeaderName="${_csrf.headerName}";
		var csrfTokenValue="${_csrf.token}";
		
		$("input[type='file']").change(function(e){
			
			var formData= new FormData();
			
			var inputFile = $("input[name='uploadFile']");
			
			var files = inputFile[0].files;
			
			for(var i = 0; i< files.length; i++){
				console.log(files);
				if(!checkExtension(files[i].name, files[i].size) ){
					return false;
				}
				formData.append("uploadFile", files[i]);
			}
			
			$.ajax({
				url: 'uploadAjaxAction',
				processData:false,
				contentType: false,
				beforeSend: function(xhr){
					xhr.setRequestHeader(csrfHeaderName,csrfTokenValue);
				},
				data: formData,
				type:'POST',
				dataType: 'json',
				success: function(result){
					console.log(result);
					showUploadResult(result); //업로드 결과 처리 함수
				}
				
			});
		});
		
		//파일 등록 했을 떄
			function showUploadResult(uploadResultArr){
				
				if(!uploadResultArr || uploadResultArr.length == 0){return;}
				
				var uploadUL = $(".uploadResult ul");
				var str ="";
				
				$(uploadResultArr).each(function(i,obj){
				console.log(obj.image);
					
					if(obj.image){
					/*	alert("이미지임"); */
						var  fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
						// 한거번에 str 다 집어 넣는 거랑 다르게 그나마 보기 편한듯?
						//str += "<li><div>"; 
						//<form> 를 추가해서 등록에 대한 데이터베이스 처리
						str += "<li data-path='"+obj.uploadPath+"'";
						str += " data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'";
						str += "><div>";
						//<form> 를 추가해서 등록에 대한 데이터베이스 처리
						console.log("콘솔 1  " + str );
						
						str += "<span>" + obj.fileName+"</span>";
						str += "<button type ='button' data-file=\'"+fileCallPath+"\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/display?fileName="+fileCallPath+"'>";
						str += "</div>";
						str += "</li>";
						
					}else {
					/* 	alert("딴거임"); */
						var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
						
						var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
						
						/* str += "<li><div>" */
						//<form> 를 추가해서 등록에 대한 데이터베이스 처리
						console.log("콘솔 2  " + str );
						str += "<li ";
						str += "data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' ><div>";
						//<form> 를 추가해서 등록에 대한 데이터베이스 처리 이후 BoardVO input hidden 처리
						str +="<span>" +obj.fileName+"</span>";
						str +="<button type= 'button' data-file=\'"+fileCallPath+"\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str +="<img src='/resources/img/attach.png'></a>";
						str +="</div>";
						str +="</li>";
								
					}
						console.log(str);
				});
				
				uploadUL.append(str);
			}
		
	});

</script>

<%@include file="../includes/footer.jsp"%>
