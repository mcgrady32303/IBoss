//保存订单
function saveOrder() {
	var date = $("#orderDate").val();
	var custumerId = $("#custumer").val();	
	var totalPrice = $("#totalPrice").val();
	var isPayed = $("input[name='isPayed']:checked").val();
	var orderDetail=[];
	var itemList = $("#itemList tr:gt(0)");
	var size = itemList.size();
	var order = {};
	
	order["date"] = date;
	order["customerId"] =  custumerId;
	order["totalPay"] =  totalPrice;
	order["isPayed"] =  isPayed;
	
	if(size == 0) {
		alert("订单详情为空！");
		alert(JSON.stringify(order));
		return;
	}
	
	//TODO 后续需要增加检查
	itemList.each(function(){
		var item = {};
		item["id"] = $(this).find("input:hidden").val();
		item["num"] = $(this).find("td>input").eq(0).val();
		item["price"] =  $(this).find("td>input").eq(1).val();
		
		orderDetail.push(item);
	});
	
	order["orderDetail"] = orderDetail;
	
	alert(JSON.stringify(order));
	
	
}



$(function() {
	$("#addItem").click(function() {
		$.getJSON("/repos/list4Sale", function(json) {
			$("#oneItem").tmpl(json).appendTo("#itemList");
		});
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
	
	$("#itemList").on("click", ".btn-danger", function() {
		$(this).parents("tr").remove();
	});
	
	$("#itemList").on("click", ".dropdown-menu li", function(){		
		var itemId = $(this).parent().find("input").val();		
		$(this).parents("td").find("button.dropdown-toggle").html($(this).find("a").text() + "<span class='caret'></span>");
		$(this).parents("tr").find("input:eq(0)").val(itemId);
	});
	
	//保存订单详情
	$("#saveBtn").on("click", function(){
		saveOrder();
	});
	
	
});