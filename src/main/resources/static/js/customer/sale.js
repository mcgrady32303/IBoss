var itemListObj;
//预先加载item列表
function loadItemList() {
	$.getJSON("/repos/list4Sale", function(json) {
		itemListObj = json;
	});
}

function deleteOrderById(id, orderHtml) {
	$.ajax({
		url : "/sale/deleteOrderById/" + id,
		type : "post",
		processData : false,
		contentType : 'application/json;charset=utf-8',
		success : function(data) {
			alert("删除成功");
			orderHtml.remove();
		},
		error : function() {
			alert('保存出错');
		}
	});
}

//点击编辑某订单
function editOrderById(id) {
	$.getJSON("/sale/listOrderById/" + id, function(order) {
		// TODO 弹框,赋值,考虑兼容add模式
		clearModalData();
		
		alert("order string:" + JSON.stringify(order));
		
		//订单主题信息
		$("#orderId").val(id);
		$("#actionType").val("edit");
		$("#customerId").val(order.customerId);
		$("#orderDate").val(order.date);
		$("#customer").val(order.customerName);
		$("#totalPay").val(order.totalPay);
		if(order.payed == true) {
			$("#hasPayed").val("checked");
		} else {
			$("#notPayed").val("checked");
		}
		
		var itemsFromOrder = order.orderList;
		alert("item list:" + itemsFromOrder);
		alert("item list:" + JSON.stringify(itemsFromOrder));
		var numOfItems = itemsFromOrder.length;
		for(var i = 0; i < numOfItems; i++) {
			var oneTableHtml = $("#oneItem").tmpl(itemListObj);
			oneTableHtml.find("input:eq(0)").val(itemsFromOrder[i].itemId); //隐藏item id
			oneTableHtml.find("input:eq(1)").val(itemsFromOrder[i].id); //隐藏order detail id
			oneTableHtml.find("td:eq(1) input").val(itemsFromOrder[i].num);// 数量
			oneTableHtml.find("td:eq(2) input").val(itemsFromOrder[i].price);  // price
			//将选项默认值赋值
			oneTableHtml.find("td div button").html(itemsFromOrder[i].itemName + "<span class='caret'></span>");
			//这一行tr增加到列表中
			oneTableHtml.appendTo("#itemList");
		}
		
		$(".sale-modal-lg").modal("show");
	});
}

//点击录入订单操作
function addOrderWhenClick() {	
	$("#recordOrderBtn").on("click", function() {
		clearModalData();
		$("#actionType").val("add");
		$(".sale-modal-lg").modal("show");
	});
}

function listOrderByDate(date) {
	$.getJSON("/sale/listOrderByDate/" + date, function(json) {
		//先清理之前数据
		$("#orderQueryList tr:gt(0)").remove();
		$("#oneOrder").tmpl(json).appendTo("#orderQueryList");
	});
	
	//整理显示序号
	orderTableIndex();
}

function listCurOrderList() {
	var today = new Date();
	alert(today.getFullYear() + "-" + today.getMonth() + 1 + "-"
			+ today.getDate());
	listOrderByDate(today.getFullYear() + "-" + today.getMonth() + 1 + "-"
			+ today.getDate());
}

//查询订单总入口
function queryOrder() {
	var date = $("#queryDate").val();
	var customerId = $("#queryCustomerId").val();
	var data;
	
	if(date == "" && customerId=="-1") {
		alert("请选择日期或者客户");
		return;
	}
	
	if(customerId == "-1") {
		$.getJSON("/sale/listOrderByDate/" + date, function(json) {
			actionAfterSuccess(json);
		});		
	} else if(date == "") {
		$.getJSON("/sale/listOrderByCustomerId/" + customerId, function(json) {
			actionAfterSuccess(json);
		});	
	} else {
		$.getJSON("/sale/listOrderByCustomerByDate/" + customerId+"/"+date, function(json) {
			actionAfterSuccess(json);
		});	
	}
	
}

//查询成功后,统一处理
function actionAfterSuccess(data) {
	$("#orderQueryList tr:gt(0)").remove();
	$("#oneOrder").tmpl(data).appendTo("#orderQueryList");	
	//整理显示序号
	orderTableIndex();
	//最后清理
	$("#queryCustomerId").val("-1");
}

// 保存订单
function saveOrder() {
	var date = $("#orderDate").val();
	var customerName = $("#customer").val();
	var customerId = $("#customerId").val();
	var totalPay = $("#totalPay").val();
	var isPayed = $("input[name='isPayed']:checked").val();
	var orderList = [];
	var itemList = $("#itemList tr:gt(0)");
	var size = itemList.size();
	var order = {};
	var orderId = $("#orderId").val();

	order["id"] = orderId;
	order["date"] = date;
	order["customerId"] = customerId;
	order["totalPay"] = totalPay;
	order["isPayed"] = isPayed;

	if (size == 0) {
		alert("订单详情为空！");
		alert(JSON.stringify(order));
		return;
	}

	// TODO 后续需要增加检查
	itemList.each(function() {
		var orderDetail = {};
		var itemId = $(this).find("input:eq(0)").val();
		var orderDetailId = $(this).find("input:eq(1)").val();
		orderDetail["id"] = orderDetailId;
		orderDetail["itemId"] = itemId;
		orderDetail["num"] = $(this).find("td>input").eq(0).val();
		orderDetail["price"] = $(this).find("td>input").eq(1).val();

		orderList.push(orderDetail);
	});

	order["orderList"] = orderList;

	alert(JSON.stringify(order));

	$.ajax({
		url : "/saveOrder",
		data : JSON.stringify(order),
		type : "post",
		processData : false,
		contentType : 'application/json;charset=utf-8',
		success : function(data) {
			alert("保存成功");
		},
		error : function() {
			alert('保存出错');
		}
	});
}

// 清除录单弹框数据
function clearModalData() {
	$("#orderDate").val("");
	$("#customer").val("");
	$("#totalPay").val("");
	$("#itemList tr:gt(0)").remove();
	//TODO 擦除hidden元素中的值
	$("#customerId").val("0");
	$("#orderId").val("0");
	$("#actionType").val("add");
}

//整理序号
function orderTableIndex() {
	var size = $("#orderQueryList tr").size() - 1;
	for(var i = 0; i < size; i++ ) {
		 $("#orderQueryList tr").eq(i+1).find("th").text(i+1);
	}
}

$(function() {
	
	loadItemList();

	listCurOrderList();
	
	addOrderWhenClick();
	
	//点击查询订单按钮
	$("#querySubmit").on("click", function(){
		queryOrder();
	});

	$("#addItem").on("click", function() {
		$("#oneItem").tmpl(itemListObj).appendTo("#itemList");
	});

	// 点击弹框用户列表中的用户后
	$(".costumer-modal-lg").on("click", ".btn-link", function() {
		var type = $(".costumer-modal-lg input:hidden").val();
		//复用了用户弹框
		if(type=="query") {
			$("#queryCustomer").val($(this).parents("tr").find("td:eq(1)").text());
			$("#queryCustomerId").val($(this).find("input").val());
		} else {
			$("#customer").val($(this).parents("tr").find("td:eq(1)").text());
			$("#customerId").val($(this).find("input").val());
		}
		
		$(".costumer-modal-lg").modal("hide");
	});

	// 录入订单时,点击选择客户名称输入框
	$("#customer").on("click",	function() {
		$.getJSON("/getAllCustomers", function(json) {
		$("#custmerList tr:gt(0)").remove();
		$("#oneCustomer").tmpl(json).appendTo("#custmerList");
		$(".costumer-modal-lg input:hidden:eq(0)").val("add");
		$(".costumer-modal-lg").css("z-index",
		$(".sale-modal-lg").attr("z-index") + "1").modal("show");
	   });
	});
	
	// 查询订单时,点击选择客户名称输入框
	$("#queryCustomer").on("click",	function() {
		$.getJSON("/getAllCustomers", function(json) {
			$("#custmerList tr:gt(0)").remove();
			$("#oneCustomer").tmpl(json).appendTo("#custmerList");
			$(".costumer-modal-lg input:hidden:eq(0)").val("query");
			$(".costumer-modal-lg").modal("show");
		});
	});

	// 点击关闭弹框过后,清除数据
	$(".sale-modal-lg").on("hide.bs.modal", function() {
		$(this).removeData();
	});

	//删除订单中的某项商品
	$("#itemList").on("click", ".btn-danger", function() {
		//order detail id的值.如果为0,则说明新增,服务器端还未有.
		var tmpId = $(this).parents("tr").find("input:eq(1)").val();
		
		if(($("#actionType").val()=="edit") && (tmpId != 0) ) {
			$.ajax({
				url : "/sale/deleteItemOfOrderById/" + tmpId,
				type : "post",
				processData : false,
				contentType : 'application/json;charset=utf-8',
				success : function(data) {
					alert("删除成功");
				},
				error : function() {
					alert('删除出错');
				}
			});			
		}
		//最后将html页面中的一列移除
		$(this).parents("tr").remove();
	});

	
	//下拉列表选择材料名称
	$("#itemList").on("click", ".dropdown-menu li",	function() {
		var itemId = $(this).find("input").val();
		$(this).parents("td").find("button.dropdown-toggle").html(
				$(this).find("a").text() + "<span class='caret'></span>");
		$(this).parents("tr").find("input:eq(0)").val(itemId);
	});
	
	// 保存订单详情
	$("#saveBtn").on("click", function() {
		saveOrder();
	});

	// 编辑列表中的某个订单
	$("#orderQueryList").on("click", ".btn-primary", function() {
		var toQueryId = $(this).find("input").val();
		if (toQueryId == null) {
			alert("选中id无效!");
			return;
		}
		editOrderById(toQueryId);

	});

	// 删除列表中的某个订单
	$("#orderQueryList").on("click", ".btn-danger", function() {
		var toDelId = $(this).find("input").val();
		var orderHtml = $(this).parents("tr");
		if (toDelId == null) {
			alert("删除选中id无效!");
			return;
		}
		deleteOrderById(toDelId, orderHtml);
	});

});