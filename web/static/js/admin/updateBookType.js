function updateBookType(id) {
    $("#updateModal").modal("show");

    var postData = "id="+id;
    // alert(id);

    $.post("/LibrarySystem_SL/admin/bookTypeManageController_getBookTypeById.action", postData, function (data) {
        // alert(data.name);
        $("#updateBookTypeId").val(data.id);
        $("#updateBookTypeName").val(data.name);

    },"json");

}

$(function () {
    $("#updateBookType").click(function () {

        // alert("hahah");
        if (!validUpdateBookType()) {
            return;
        }

        var postData = "id=" + $.trim($("#updateBookTypeId").val()) + "&name=" + $.trim($("#updateBookTypeName").val());
        // alert("hahaha");
        $.post("/LibrarySystem_SL/admin/bookTypeManageController_updateBookTypeInfo.action", postData, function (data) {
            // alert(data);
            if (data == 1) {
                $("#updateModal").modal("hide");
                showInfo("修改成功")
            } else if (data == -1) {
                showInfo("修改失败")
            }

        });
    });
    $("#modal_info").on("hide.bs.modal", function () {
        window.location.reload();

    });

});

function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");

}



function validUpdateBookType() {
    var flag = true;
    var reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
    var bookType = $.trim($("#updateBookTypeName").val());
    if (bookType == "") {
        $("#updateBookTypeName").parent().addClass("has-error");
        $("#updateBookTypeName").next().text("请输入图书分类名称");
        $("#updateBookTypeName").next().show();
        flag = false;
    } else if (!reg.test(bookType)) {
        $("#updateBookTypeName").parent().addClass("has-error");
        $("#updateBookTypeName").next().text("图书分类名称必须为中文");
        $("#updateBookTypeName").next().show();
        flag = false;
    } else {
        $("#updateBookTypeName").parent().removeClass("has-error");
        $("#updateBookTypeName").next().text("");
        $("#updateBookTypeName").next().hide();
    }
    return flag;
}