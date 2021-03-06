var itemListObj;
toastr.options.positionClass = 'toast-top-center';// 提示框位置

// 预先加载item列表
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
			toastr.success('删除成功');
			orderHtml.remove();
		},
		error : function() {
			toastr.error('删除出错');
		}
	});
}

// 点击编辑某订单
function editOrderById(id) {
	$.getJSON("/sale/listOrderById/" + id,
			function(order) {

				clearModalData();

				// 订单主题信息
				$("#orderId").val(id);
				$("#actionType").val("edit");
				$("#customerId").val(order.customerId);
				$("#orderDate").val(order.date);
				$("#customer").val(order.customerName);
				$("#totalPay").val(order.totalPay);
				$("#receiptNo").val(order.receiptNo);
				$("#debt").val(order.debt);

				if (order.paymentStatus == 0) {
					$("#hasPayed").prop("checked", "true");
				} else if (order.paymentStatus == 1) {
					$("#notPayed").prop("checked", "true");
				} else {
					$("#partPayed").prop("checked", "true");
					$("#debt").prop("disabled", false);
				}

				var itemsFromOrder = order.orderList;
				var numOfItems = itemsFromOrder.length;
				for ( var i = 0; i < numOfItems; i++) {
					var oneTableHtml = $("#oneItem").tmpl(itemListObj);
					oneTableHtml.find("input:eq(0)").val(
							itemsFromOrder[i].itemId); // 隐藏item id
					oneTableHtml.find("input:eq(1)").val(itemsFromOrder[i].id); // 隐藏order
					// detail
					// id
					oneTableHtml.find("td:eq(1) input").val(
							itemsFromOrder[i].num);// 数量
					oneTableHtml.find("td:eq(2) input").val(
							itemsFromOrder[i].price); // price
					// 将选项默认值赋值
					oneTableHtml.find("td div button").html(
							itemsFromOrder[i].itemName
									+ "<span class='caret'></span>");
					// 这一行tr增加到列表中
					oneTableHtml.appendTo("#itemList");
				}

				$(".sale-modal-lg").modal("show");
			});
}

// 点击录入订单操作
function addOrderWhenClick() {
	$("#recordOrderBtn").on("click", function() {
		clearModalData();
		$("#actionType").val("add");
		$(".sale-modal-lg").modal("show");
	});
}

function listOrderByDate(date) {
	$.getJSON("/sale/listOrderByDate/" + date, function(json) {
		if (json == "") {
			toastr.info(date + '当天无数据');
		}
		// 先清理之前数据
		$("#orderQueryList tr:gt(0)").remove();
		$("#oneOrder").tmpl(json).appendTo("#orderQueryList");
		// 整理显示序号
		orderTableIndex();
	});
}

function listCurOrderList() {
	var today = new Date();
	var year = today.getFullYear();
	var month = 1 + parseInt(today.getMonth());
	if (month < 10) {
		month = '0' + month;
	}
	var day = today.getDate();
	if (day < 10) {
		day = '0' + day
	}
	listOrderByDate(year + "-" + month + "-" + day);
}

// 查询订单总入口
function queryOrder() {
	var date = $("#queryDate").val();
	var customerId = $("#queryCustomerId").val();
	var data;

	if (date == "" && customerId == "-1") {
		toastr.error('请选择日期或者客户!');
		return;
	}

	if (customerId == "-1") {
		listOrderByDate(date);
	} else if (date == "") {
		$.getJSON("/sale/listOrderByCustomerId/" + customerId, function(json) {
			actionAfterSuccess(json);
		});
	} else {
		$.getJSON("/sale/listOrderByCustomerByDate/" + customerId + "/" + date,
				function(json) {
					actionAfterSuccess(json);
				});
	}

}

// 查询成功后,统一处理
function actionAfterSuccess(data) {
	if (data == "") {
		toastr.info('查询无数据');
	}
	$("#orderQueryList tr:gt(0)").remove();
	$("#oneOrder").tmpl(data).appendTo("#orderQueryList");
	// 整理显示序号
	orderTableIndex();
	// 最后清理
	$("#queryCustomerId").val("-1");
	$("#queryCustomer").val("");
}

// 保存订单
function saveOrder() {
	var date = $("#orderDate").val();
	var autoCalc = $("#autoCalc").prop("checked");
	var customerName = $("#customer").val();
	var customerId = $("#customerId").val();
	var totalPay = $("#totalPay").val();
	var receiptNo = $("#receiptNo").val();
	var paymentStatus;
	var orderList = [];
	var itemList = $("#itemList tr:gt(0)");
	var size = itemList.size();
	var order = {};
	var orderId = $("#orderId").val();
	var ptnInt = new RegExp("^[0-9]*$");
	var ptnDouble = new RegExp("^[0-9]+[.]{1}[0-9]+$");

	order["id"] = orderId;
	order["date"] = date;
	order["receiptNo"] = receiptNo;
	order["customerId"] = customerId;
	// order["totalPay"] = totalPay;

	order["actionType"] = $("#actionType").val();

	if (date == "") {
		toastr.error("请填写日期！");
		return;
	}
	if (customerName == "") {
		toastr.error("请选择客户！");
		return;
	}

	if (size == 0) {
		toastr.error('订单详情为空,保存失败!');
		return;
	}

	var checked = true;
	var calTotalPrice = 0;
	// 对订单每个材料详情逐一处理
	itemList.each(function() {
		var orderDetail = {};
		var itemId = $(this).find("input:eq(0)").val();
		var orderDetailId = $(this).find("input:eq(1)").val();
		var tmpNum = $(this).find("td>input").eq(0).val();
		var tmpPrice = $(this).find("td>input").eq(1).val();
		orderDetail["id"] = orderDetailId;
		orderDetail["itemId"] = itemId;
		orderDetail["num"] = tmpNum;
		orderDetail["price"] = tmpPrice;

		if (!ptnInt.test(tmpNum) || itemId == "0") {
			checked = false;
			return;
		}

		if (!ptnInt.test(tmpPrice) && !ptnDouble.test(tmpPrice)) {
			checked = false;
			return;
		}

		calTotalPrice += tmpNum * tmpPrice;

		orderList.push(orderDetail);
	});

	if (!checked) {
		toastr.error("材料输入存在错误，请修正！");
		return;
	}

	// 自动计算
	if (autoCalc) {
		order["totalPay"] = calTotalPrice;
		$("#totalPay").val(calTotalPrice);
	} else { // 否则检查填入数字是否符合格式，并不检查 总价==明细之和？

		if (!ptnInt.test(totalPay) && !ptnDouble.test(totalPay)) {
			toastr.error("总金额请输入数字！");
			return;
		}

		order["totalPay"] = totalPay;
	}

	// 根据支付状态，写入欠款
	if ($("#hasPayed").prop("checked")) {
		paymentStatus = 0;
		order["debt"] = 0;
	} else if ($("#notPayed").prop("checked")) {
		paymentStatus = 1;
		order["debt"] = totalPay;
		$("#debt").val(totalPay);
	} else {
		paymentStatus = 2;
		var debt = $("#debt").val();
		if (!ptnInt.test(debt) && !ptnDouble.test(debt)) {
			toastr.error("未付金额请输入数字！");
			return;
		}
		order["debt"] = $("#debt").val();
	}

	order["paymentStatus"] = paymentStatus;

	order["orderList"] = orderList;

	$.ajax({
		url : "/sale/saveOrder",
		data : JSON.stringify(order),
		type : "post",
		processData : false,
		contentType : 'application/json;charset=utf-8',
		success : function(data) {
			toastr.info('保存成功!');
			setTimeout(location.reload(), 2000);
			$(".sale-modal-lg").modal("hide");
			clearModalData();
		},
		error : function() {
			toastr.error('保存失败!');
			setTimeout(location.reload(), 2000);
			$(".sale-modal-lg").modal("hide");
			clearModalData();
		}
	});
}

// 清除录单弹框数据
function clearModalData() {
	$("#orderDate").val("");
	$("#customer").val("");
	$("#totalPay").val("");
	$("#itemList tr:gt(0)").remove();
	$("#debt").prop("disabled", true);

	// 擦除hidden元素中的值
	$("#customerId").val("0");
	$("#queryCustomer").val("");
	$("#orderId").val("0");
	$("#actionType").val("add");
}

// 整理序号
function orderTableIndex() {
	var size = $("#orderQueryList tr").size() - 1;
	for ( var i = 0; i < size; i++) {
		$("#orderQueryList tr").eq(i + 1).find("th").text(i + 1);
	}
}

$(function() {

	loadItemList();

	listCurOrderList();

	addOrderWhenClick();

	// 点击查询订单按钮
	$("#querySubmit").on("click", function() {
		queryOrder();
	});

	$("#addItem").on("click", function() {
		$("#oneItem").tmpl(itemListObj).appendTo("#itemList");
	});

	// 支付状态改变时,变化欠款编辑状态
	$(":radio").on("click", function() {
		if ($("#partPayed").prop("checked")) {
			$("#debt").prop("disabled", false);
		} else {
			$("#debt").prop("disabled", true);
		}
	});

	// 点击弹框用户列表中的用户后
	$(".costumer-modal-lg").on(
			"click",
			".btn-link",
			function() {
				var type = $(".costumer-modal-lg input:hidden").val();
				// 复用了用户弹框
				if (type == "query") {
					$("#queryCustomer").val(
							$(this).parents("tr").find("td:eq(1)").text());
					$("#queryCustomerId").val($(this).find("input").val());
				} else {
					$("#customer").val(
							$(this).parents("tr").find("td:eq(1)").text());
					$("#customerId").val($(this).find("input").val());
				}

				$(".costumer-modal-lg").modal("hide");
			});

	// 录入订单时,点击选择客户名称输入框
	$("#customer").on(
			"click",
			function() {
				$.getJSON("/getAllCustomers", function(json) {
					$("#custmerList tr:gt(0)").remove();
					$("#oneCustomer").tmpl(json).appendTo("#custmerList");
					$(".costumer-modal-lg input:hidden:eq(0)").val("add");
					$(".costumer-modal-lg").css("z-index",
							$(".sale-modal-lg").attr("z-index") + "1").modal(
							"show");
				});
			});

	// 查询订单时,点击选择客户名称输入框
	$("#queryCustomer").on("click", function() {
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

	// 删除订单中的某项商品
	$("#itemList").on("click", ".btn-danger", function() {
		// order detail id的值.如果为0,则说明新增,服务器端还未有.
		var tmpId = $(this).parents("tr").find("input:eq(1)").val();

		if (($("#actionType").val() == "edit") && (tmpId != 0)) {
			$.ajax({
				url : "/sale/deleteItemOfOrderById/" + tmpId,
				type : "post",
				processData : false,
				contentType : 'application/json;charset=utf-8',
				success : function(data) {
					toastr.success('删除成功!');
				},
				error : function() {
					toastr.error('删除出错!');
				}
			});
		}
		// 最后将html页面中的一列移除
		$(this).parents("tr").remove();
	});

	// 下拉列表选择材料名称
	$("#itemList").on(
			"click",
			".dropdown-menu li",
			function() {
				var itemId = $(this).find("input").val();
				$(this).parents("td").find("button.dropdown-toggle").html(
						$(this).find("a").text()
								+ "<span class='caret'></span>");
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
			toastr.error('选中订单id有误!');
			return;
		}
		editOrderById(toQueryId);

	});

	// 删除列表中的某个订单
	$("#orderQueryList").on("click", ".btn-danger", function() {
		var toDelId = $(this).find("input").val();
		var orderHtml = $(this).parents("tr");
		if (toDelId == null) {
			toastr.error('选中订单id有误!');
			return;
		}
		deleteOrderById(toDelId, orderHtml);
	});

	// 监听搜索内容变化事件
	$("#keyword").on("input propertychange", function() {
		$("#custmerList tr").show();
	});

	// 用户弹框,检索用户
	$("#matchCustomerBtn").on("click", function() {
		$("#custmerList tr").show();
		var input = $("#keyword").val();
		if (input == "") {
			$("#custmerList tr").show();
			return;
		}
		$("#custmerList tr").each(function() {
			var name = $(this).find("td:eq(1)").text();
			var tel = $(this).find("td:eq(2)").text();
			var msg = $(this).find("td:eq(3)").text(); 
			if (name.indexOf(input) == -1 && msg.indexOf(input) == -1 && tel.indexOf(input) == -1) {
				$(this).hide();
			}
		});
	});

});