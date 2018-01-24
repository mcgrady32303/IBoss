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
		$("#msg").val("");
		$("#customerName").attr("placeHolder","客户名称");
		$("#tel").attr("placeHolder","电话");
		$("#msg").attr("placeHolder","备注");
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
		alert($(this).toString() + $(this).parents("tr").find("td:eq(1)").text());
		$("#customerName").val($(this).parents("tr").find("td:eq(1)").text());
		$("#tel").val($(this).parents("tr").find("td:eq(2)").text());
		$("#msg").val($(this).parents("tr").find("td:eq(3)").text());
		$('.customer-modal-lg').modal("show");
		// 提示不修改图片可以不再上传
	});
	
	//点击删除某一个客户
	$("#customerTable").on("click", ".btn-danger",function() {
		var toDel = $(this).parents("tr");
		var customerId = $(this).parents("td").find("input").val();
		
		alert("待删除id:" + customerId);
		
		$.ajax({
			url : "/deleteCustomer",
			data : "customerId="+customerId,
			type : "post",
			processData : false,			
			contentType: "application/x-www-form-urlencoded",
			success : function(data) {
				toDel.remove();
			},
			error : function() {
				alert('删除出错');
			}
		});
	});
	
	//点击某一个客户的详情
	$("#customerTable").on("click", ".btn-link",function() {
		//TODO
	});
});