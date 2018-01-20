function initRepos() {
	$.getJSON("/repos/list", function(json) {
		$("#item-tmpl").tmpl(json).appendTo("#reposTable");
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
			$("#tip").html("<span style='color:blue'>正在处理...</span>");
			return true;
		},
		success : function(data) {

			if (data != null) {

				$("#tip").html("<span style='color:blueviolet'>上传成功！</span>");
				alert("保存成功!");
				setTimeout(location.reload(), 2000);
			} else {
				$("#tip").html("<span style='color:red'>失败，请重试</span>");
				alert('操作失败');
			}
		},
		error : function() {
			alert('请求出错');
		},
		complete : function() {
		}
	});

	return false; // ajax提交后，form不再自动提交
}

$(function() {
	initRepos();

	$("#reposTable").on("click", ".btn-danger", function() {
		$(this).parents("tr").remove();
	});

	$("#reposTable").on("click", ".btn-primary",function() {
		$("#actionType").val("edit");
		$("#originImage img").attr("src",$(this).parents("tr").find("img").attr("src"));
		$("#originImage").show();
		$("#itemId").val($(this).find("input").val());		
		$("#sampleImage").attr("required", "false");
		alert($(this).toString() + $(this).parents("td").prev().prev().text());
		$("#goodName").val($(this).parents("td").prev().prev().text());
		$("#initNum").val($(this).parents("td").prev().text());
		$('.storage-modal-lg').modal("show");
		// 提示不修改图片可以不再上传
	});

});