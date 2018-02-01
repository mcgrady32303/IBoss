toastr.options.positionClass = 'toast-top-center';//提示框位置

function initRepos() {
	$.getJSON("/repos/list", function(json) {
		$("#item-tmpl").tmpl(json).appendTo("#reposTable");
		var size = $("#reposTable tr").size() - 1;
		for(var i = 0; i < size; i++ ) {
			 $("#reposTable tr").eq(i+1).find("th").text(i+1);
		}
	});
}

function ajax_submit() {

	var formData = new FormData($("#goodInfo")[0]);

	$.ajax({
		url : "/upload",
		data : formData,
		type : "post",
		processData : false,
		contentType : false,
		beforeSend : function() {
			toastr.success('正在处理...');
			return true;
		},
		success : function(data) {
			if (data != null) {
				toastr.success('保存成功!');
				setTimeout(location.reload(), 2000);
			} else {
				toastr.error('失败，请重试');	
			}
		},
		error : function() {
			toastr.error('失败，请重试');	
		},
		complete : function() {
		}
	});

	return false; // ajax提交后，form不再自动提交
}

$(function() {
	initRepos();
	
	$("[data-toggle='tooltip']").tooltip();

	$("#reposTable").on("click", ".btn-danger", function() {
		var toDel = $(this).parents("tr");
		var itemId = $(this).parents("td").find("input").val();
		
		$.ajax({
			url : "/deleteItem/"+itemId,
//			data : "itemId="+itemId,
			type : "GET",
			processData : false,			
			contentType: "application/x-www-form-urlencoded",
			success : function(data) {
				if(data == "success") {
					toDel.remove();
					toastr.success('删除成功');	
				} else {
					toastr.error(data);	
				}
			},
			error : function() {
				toastr.error('删除出错');	
			}
		});
	});
		
	$("#btnAddItem").on("click", function(){
		$("#originImage").hide();
		$("#goodName").val("");
		$("#initNum").val("");
		$("#goodName").attr("placeHolder","商品名称");
		$("#initNum").attr("placeHolder","初始数量");
		$("#sampleImage").attr("required", true);
		$('.storage-modal-lg').modal("show");
		
	});

	//点击编辑时的操作
	$("#reposTable").on("click", ".btn-primary",function() {
		var originIndex = $(this).parents("tr").find("img").attr("src");
		$("#actionType").val("edit");
		$("#originImage img").attr("src", originIndex);
		$("#originImageIndex").val(originIndex.substring(8));
		$("#originImage").show();
		$("#itemId").val($(this).find("input").val());		
		$("#sampleImage").attr("required", false);
		$("#goodName").val($(this).parents("td").prev().prev().text());
		$("#initNum").val($(this).parents("td").prev().text());
		$('.storage-modal-lg').modal("show");
		// 提示不修改图片可以不再上传
	});
	
	//点击入庫时的操作
	$("#reposTable").on("click", ".btn-default",function() {
		$("#itemNameForIncrease").text($(this).parents("td").prev().prev().text());
		$("#hiddenItemId").val($(this).find("input").val());
		$('.itemIncrease-modal-lg').modal("show");
	});
	
	//確定入庫
	$("#increaseBtn").on("click", function(){
		var itemId = $("#hiddenItemId").val();
		var addedNum = $("#addedNum").val();
	
		$.ajax({
			url : "/repos/increaseItem/"+itemId+"/"+addedNum,
//			data : "itemId="+itemId,
			type : "GET",
			processData : false,			
			contentType: "application/x-www-form-urlencoded",
			success : function(data) {
				if(data == "success") {
					toastr.success('入庫成功!');
				} else {
					toastr.error(data);	
				}
				setTimeout(location.reload(), 2000);
				$('.itemIncrease-modal-lg').modal("hide");
			},
			error : function() {
				toastr.error('入庫出错');
				setTimeout(location.reload(), 2000);
				$('.itemIncrease-modal-lg').modal("hide");
			}
		});
	});

});