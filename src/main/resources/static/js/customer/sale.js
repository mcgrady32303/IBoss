//保存订单
function saveOrder() {
	var date = $("#orderDate").val();
	var custumer = $("#custumer").val();	
	var totalPrice = $("#totalPrice").val();
	var isPayed = $("input[name='isPayed']:checked").val();
	var orderDetail;
}



$(function() {
	$("#addItem").click(function() {
//		$($("#oneItem").html()).appendTo("#itemList");
		$.getJSON("/repos/list", function(json) {
			$("#oneItem").tmpl(json).appendTo("#itemList");
		});
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
	
	//选择下拉列表后，将id保存到隐藏框
//	$("#itemList ul li").on("change", function(){
//	$("#itemList ul li").change(function(){
//		var itemId = $(this).find("input").val();
//		alert("点击商品id：" + itemId);
//		$(this).parents("div").find("button").html($(this).find("a").text()+" <span class='caret'></span>");
//		$(this).parents("tr").find("input:eq(0)").val(itemId);
//	});
	
	$("#itemList .dropdown-menu li").on("click",function() {
		alert("捕捉到了！");
		var itemId = $(this).parent().find("input").val();
		alert("点击商品id：" + itemId);
		$(this).parents("div").find("button").html($(this).find("a").text()+" <span class='caret'></span>");
		$(this).parents("tr").find("input:eq(0)").val(itemId);
	});
	
	//保存订单详情
	$("#saveBtn").on("click", function(){
		
	});
	
	
});