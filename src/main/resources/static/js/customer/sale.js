
	
	













$(function() {
	$("#addItem").click(function() {
		$($("#oneItem").html()).appendTo("#itemList");
	});

	$("#itemList").on("click", ".btn-danger", function() {
		$(this).parents("tr").remove();
	});
});