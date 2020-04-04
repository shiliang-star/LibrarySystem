$(function () {
    $("#btn_add").click(function () {
        $("#addModal").modal("show");
        });
    $("#addBookType").click(function () {

        if (!validAddBookType()) {
            return;
        }
        var postData = "name=" + $.trim($("#addBookTypeName").val());
        // alert("hahaha");
        $.post("/LibrarySystem_SL/admin/bookTypeManageController_addBookType.action", postData, function (data) {
            // alert(data);
            if (data == 1) {
                $("#addModal").modal("hide");
                showInfo("添加成功")
            } else if (data == -1) {
                $("#addModal").modal("hide");
                showInfo("添加失败")
            }else if (data == -2) {
                $("#addModal").modal("hide");
                showInfo("图书分类名称已存在，请重新输入")
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

function validAddBookType() {
    var flag = true;

    var reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
    var bookType = $.trim($("#addBookTypeName").val());
    if (bookType == "") {
        $('#addBookTypeName').parent().addClass("has-error");
        $('#addBookTypeName').next().text("请输入图书分类名称");
        $("#addBookTypeName").next().show();
        flag = false;
    } else if (!reg.test(bookType)) {
        $('#addName').parent().addClass("has-error");
        $('#addName').next().text("图书分类名称必须为中文");
        $("#addName").next().show();
        flag = false;
    } else {
        $('#addBookTypeName').parent().removeClass("has-error");
        $('#addBookTypeName').next().text("");
        $("#addBookTypeName").next().hide();
    }
    return flag;
}