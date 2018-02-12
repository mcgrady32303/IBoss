toastr.options.positionClass = 'toast-top-center';// 提示框位置

function initCustomerList() {
	$.getJSON("/getAllCustomers", function(json) {
		$("#customer-tmpl").tmpl(json).appendTo("#customerTable");
		var size = $("#customerTable tr").size() - 1;
		for ( var i = 0; i < size; i++) {
			$("#customerTable tr").eq(i + 1).find("th").text(i + 1);
		}
	});
}

// 新增及修改客户资料
function ajax_submit() {

	var formData = new FormData($("#customerInfo")[0]);

	$.ajax({
		url : "/saveCustomer",
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
				if (data != null) {
					toastr.success('保存成功!');
					setTimeout(location.reload(), 2000);
				} else {
					toastr.error('失败，请重试');
				}
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
	initCustomerList();

	$("[data-toggle='tooltip']").tooltip();

	// 新增客户，点击弹框
	$("#btnAddCustomer").on("click", function() {
		$("#originImage").hide();
		$("#customerName").val("");
		$("#tel").val("");
		$("#msg").val("");
		$("#customerName").attr("placeHolder", "客户名称");
		$("#tel").attr("placeHolder", "电话");
		$("#msg").attr("placeHolder", "备注");
		//$("#sampleImage").attr("required", true);
		$('.customer-modal-lg').modal("show");
	});

	// 点击编辑时的操作
	$("#customerTable").on("click",	".btn-primary",	function() {
				var originIndex = $(this).parents("tr").find("img").attr("src");
				$("#actionType").val("edit");
				$("#originImage img").attr("src",
						originIndex);
				$("#originImageIndex").val(originIndex.substring(1));
				$("#originImage").show();
				$("#customerId").val($(this).find("input").val());
				$("#sampleImage").attr("required", false);
				$("#customerName").val(
						$(this).parents("tr").find("td:eq(1)").text());
				$("#tel").val($(this).parents("tr").find("td:eq(2)").text());
				$("#msg").val($(this).parents("tr").find("td:eq(3)").text());
				$('.customer-modal-lg').modal("show");
				// 提示不修改图片可以不再上传
	});

	// 点击删除某一个客户
	$("#customerTable").on("click", ".btn-danger", function() {
		var toDel = $(this).parents("tr");
		var customerId = $(this).parents("td").find("input").val();

		// alert("待删除id:" + customerId);

		$.ajax({
			url : "/deleteCustomer/" + customerId,
			// data : "customerId="+customerId,
			type : "get",
			processData : false,
			contentType : "application/x-www-form-urlencoded",
			success : function(data) {
				if (data == "success") {
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

	// 点击某一个客户的详情
	$("#customerTable").on("click", ".btn-link", function() {
		//先清理
		$("#customerDetailTable tr:gt(0)").remove();
		
		var customerId = $(this).parents("td").find("input").val();
		$.getJSON("/sale/getCustomerDetail/" + customerId, function(json) {
			$("#customer-detail-tmpl").tmpl(json).appendTo("#customerDetailTable");
		});
		$('.detail-modal-lg').modal("show");
	});
	
	//点击图片放大查看
	$("#customerTable").on("click", "img", function(){
		$("#targetImg").attr("src", $(this).attr("src"));
		$('.image-modal-lg').modal("show");
	});
});