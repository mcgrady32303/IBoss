



$(function() {
	$("#addItem").click(function() {
		$($("#oneItem").html()).appendTo("#itemList");
	});

	$("#itemList").on("click", ".btn-danger", function() {
		$(this).parents("tr").remove();
	});
	
	//点击弹框用户列表中的用户后
	$(".costumer-modal-lg").on("click", ".btn-link", function(){
		alert($(this).parents("tr").find("td:eq(1)").text());
		$("#custumer").val($(this).parents("tr").find("td:eq(1)").text());
		$("#customerId").val($(this).find("input").val());
		$(".costumer-modal-lg").modal("hide");
	});
	
	//点击选择客户名称输入框
	$("#custumer").on("click",  function(){		
		$.getJSON("/getAllCustomers", function(json){
			$("#custmerList tr:gt(0)").remove();
			$("#oneCustomer").tmpl(json).appendTo("#custmerList");
			$(".costumer-modal-lg").css("z-index",$(".sale-modal-lg").attr("z-index")+"1").modal("show");
			
		});
		
		
	});
	
	
});