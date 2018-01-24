function initCustomerList() {
	$.getJSON("/getAllCustomers", function(json) {
		$("#customer-tmpl").tmpl(json).appendTo("#customerTable");
		var size = $("#customerTable tr").size() - 1;
		for(var i = 0; i < size; i++ ) {
			 $("#customerTable tr").eq(i+1).find("th").text(i+1);
		}
	});
}

//新增及修改客户资料
function ajax_submit() {

	var formData = new FormData($("#customerInfo")[0]);

	$.ajax({
		url : "/saveCustomer",
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
	initCustomerList();
	
	$("[data-toggle='tooltip']").tooltip();
	
		
	//新增客户，点击弹框
	$("#btnAddCustomer").on("click", function(){
		$("#originImage").hide();
		$("#customerName").val("");
		$("#tel").val("");
		$("#customerName").attr("placeHolder","客户名称");
		$("#tel").attr("placeHolder","电话");
		$("#sampleImage").attr("required", true);
		$('.customer-modal-lg').modal("show");		
	});

	//点击编辑时的操作
	$("#customerTable").on("click", ".btn-primary",function() {
		$("#actionType").val("edit");
		$("#originImage img").attr("src",$(this).parents("tr").find("img").attr("src"));
		$("#originImage").show();
		$("#customerId").val($(this).find("input").val());		
		$("#sampleImage").attr("required", false);
		alert($(this).toString() + $(this).parents("td").prev().prev().text());
		$("#customerName").val($(this).parents("td").prev().prev().text());
		$("#tel").val($(this).parents("td").prev().text());
		$('.customer-modal-lg').modal("show");
		// 提示不修改图片可以不再上传
	});
});