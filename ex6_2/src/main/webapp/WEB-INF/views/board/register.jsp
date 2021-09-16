<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@include file="../includes/header.jsp"%>

<style type="text/css">

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
}

.uploadResult ul li img{
	width: 20px;
}

.bigPictureWrapper{
	position: absolute;
	display:none;
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
    <h1 class="page-header">Board Register</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">Board Register</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

        <form role="form" action="/board/register" method="post">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
          
          <div class="form-group">
            <label>Title</label> <input class="form-control" name='title'>
          </div>

          <div class="form-group">
            <label>Text area</label>
            <textarea class="form-control" rows="3" name='content'></textarea>
          </div>

          <div class="form-group">
          <!-- principal test는 customLogin에서 아이디 로그인 후 board/list에서 새 글 쓰기로 확인 -->
            <label>Writer</label> <input class="form-control" name='writer' value='<sec:authentication property="principal.username" />' readonly="readonly">
          </div>
          <button type="submit" class="btn btn-default">Submit
            Button</button>
          <button type="reset" class="btn btn-default">Reset Button</button>
        </form>

      </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->

<!-- 업로드 부분 -->
<div class="row">
	<div class = "col-lg-12">
		<div class="panel panel-default" >
		
			<div class="panel-heading">파일 업로드</div>
			<div class="form-group uploadDiv">
				<input type="file" name ="uploadFile" multiple>
			</div>
			<!-- /.form-group uploadDiv -->
			
			<div class="uploadResult">
				<ul>
				</ul>
			</div>
			<!-- /.uploadResult -->
			
		</div>
		<!-- /.panel panel-dafault -->
	</div> 
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<!-- /.업로드 부분 -->

<script type="text/javascript">
	$(document).ready(function(e){
		
		var formObj = $("form[role='form']");
		
		$("button[type='submit']").on("click", function(e){
			
			
			if(!$("input[name=title]").val()){
				$("input[name=title]").focus();
				
				alert("제목 채워주세요.");
				
				
				return false;
			}
			if(!$("textarea[name=content]").val()){
				$("textarea[name=content]").focus();
				
				alert("내용 채워주세요.");
				
				return false;
			}
			if(!$("input[name=writer]").val()){
				$("input[name=writer]").focus();
				
				alert("작성자 채워주세요.");
				
				return false;
			}
			
			
			e.preventDefault();
			
			console.log("제출 클릭");
			//<form>이용해서 업로드 파일 보낼 hideen데이터
			
			var str = "";
			
			$(".uploadResult ul li").each(function(i,obj){
				
				var jobj=$(obj);
				
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
			//Boardcontroller, Boardservice
		});
		
		var csrfHeaderName = "${_csrf.headerName}";
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
				beforeSend:function(xhr){
					xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
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
	
	$(".uploadResult").on("click","button", function(e){
		console.log("delete file");
		
		var targetFile = $(this).data("file");
		var type = $(this).data("type");
		
		var targetLi = $(this).closest("li");
		
		$.ajax({
			url:'/deleteFile',
			data: {fileName: targetFile, type:type},
			beforeSend: function(xhr){
				xhr.setRequestHeader(csrfHeaderName,csrfTokenValue);
			},
			dataType:'text',
			type:'POST',
			success: function(result){
				alert(result);
				targetLi.remove();
			}
			/* 이후 게시물 등록 <form> 태그로 이루어지기 떄문에
			hidden 태그로 생성해서 처리
			data-uuid,data-fileName,data-type 추가 */
			
		});
	});
		
	
		
	});
</script>

<%@include file="../includes/footer.jsp"%>
