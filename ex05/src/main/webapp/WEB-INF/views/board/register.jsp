<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
          <div class="form-group">
            <label>Title</label> <input class="form-control" name='title'>
          </div>

          <div class="form-group">
            <label>Text area</label>
            <textarea class="form-control" rows="3" name='content'></textarea>
          </div>

          <div class="form-group">
            <label>Writer</label> <input class="form-control" name='writer'>
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

<!-- ????????? ?????? -->
<div class="row">
	<div class = "col-lg-12">
		<div class="panel panel-default" >
		
			<div class="panel-heading">?????? ?????????</div>
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
<!-- /.????????? ?????? -->

<script type="text/javascript">
	$(document).ready(function(e){
		
		var formObj = $("form[role='form']");
		
		$("button[type='submit']").on("click", function(e){
			
			
			if(!$("input[name=title]").val()){
				$("input[name=title]").focus();
				
				alert("?????? ???????????????.");
				
				
				return false;
			}
			if(!$("textarea[name=content]").val()){
				$("textarea[name=content]").focus();
				
				alert("?????? ???????????????.");
				
				return false;
			}
			if(!$("input[name=writer]").val()){
				$("input[name=writer]").focus();
				
				alert("????????? ???????????????.");
				
				return false;
			}
			
			
			e.preventDefault();
			
			console.log("?????? ??????");
			//<form>???????????? ????????? ?????? ?????? hideen?????????
			
			var str = "";
			
			$(".uploadResult ul li").each(function(i,obj){
				
				var jobj=$(obj);
				
				str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
				console.log("?????? 1: " +str);
				str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
				console.log("?????? 2: " +str);
				str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
				console.log("?????? 3: " +str);
				str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
				console.log("?????? 4: " +str);
			});
			formObj.append(str).submit();
			//Boardcontroller, Boardservice
		});
		
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
				data: formData,
				type:'POST',
				dataType: 'json',
				success: function(result){
					console.log(result);
					showUploadResult(result); //????????? ?????? ?????? ??????
				}
				
			});
	});
	
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|avi)$");
	var maxSize = 5242880;
	
	function checkExtension(fileName, fileSize){
		
		if(fileSize >= maxSize){
			alert("?????? ????????? ??????");
			return false;
		}
		
		if(regex.test(fileName)){
			
			alert("?????? ????????? ????????? ????????? ??? ??? ????????????.");
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
			/*	alert("????????????"); */
				var  fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
				// ???????????? str ??? ?????? ?????? ?????? ????????? ????????? ?????? ??????????
				//str += "<li><div>"; 
				//<form> ??? ???????????? ????????? ?????? ?????????????????? ??????
				str += "<li data-path='"+obj.uploadPath+"'";
				str += " data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'";
				str += "><div>";
				//<form> ??? ???????????? ????????? ?????? ?????????????????? ??????
				console.log("?????? 1  " + str );
				
				str += "<span>" + obj.fileName+"</span>";
				str += "<button type ='button' data-file=\'"+fileCallPath+"\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
				str += "<img src='/display?fileName="+fileCallPath+"'>";
				str += "</div>";
				str += "</li>";
				
			}else {
			/* 	alert("?????????"); */
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
				
				var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
				
				/* str += "<li><div>" */
				//<form> ??? ???????????? ????????? ?????? ?????????????????? ??????
				console.log("?????? 2  " + str );
				str += "<li ";
				str += "data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' ><div>";
				//<form> ??? ???????????? ????????? ?????? ?????????????????? ?????? ?????? BoardVO input hidden ??????
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
			dataType:'text',
			type:'POST',
			success: function(result){
				alert(result);
				targetLi.remove();
			}
			/* ?????? ????????? ?????? <form> ????????? ??????????????? ?????????
			hidden ????????? ???????????? ??????
			data-uuid,data-fileName,data-type ?????? */
			
		});
	});
		
	
		
	});
</script>

<%@include file="../includes/footer.jsp"%>
