$(function () {
    $("#add_BookNum").click(function () {
        if (!validAddBookNum()) {
            return;
        }

        ajax({
            method: "POST",
            url: "/LibrarySystem_SL/admin/bookManageController_addBookNum.action",
            params: "id=" + $.trim( $("#addBookNumId").val()) + "&addNum="+$.trim( $("#addBookNum").val()),
            callback: function (data) {
                if (data == 1) {
                    $("#addNumModal").modal("hide");
                    showInfo("新增成功");
                } else {
                    showInfo("新增失败");
                    $("#addNumModal").modal("hide");
                }

            }

        });


    });
    $('#modal_info').on('hide.bs.modal', function () {//提示模糊框隐藏时候触发
        location.reload();  	//刷新当前页面
    });

});

function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");
}

function addBookNum(id, isbn) {

    $("#addBookNumId").val(id);
    $("#addBookNumISBN").val(isbn);

    $("#addNumModal").modal("show");


}


function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal('show');
}
function validAddBookNum() {
    var flag = true;
    var num = $.trim($("#addBookNum").val());
    if (num == "") {
        $('#addBookNum').parent().addClass("has-error");
        $('#addBookNum').next().text("请输入新增图书数量");
        $("#addBookNum").next().show();
        flag = false;
    } else if (num <= 0 || num != parseInt(num)) {
        $('#addBookNum').parent().addClass("has-error");
        $('#addBookNum').next().text("图书数量必须为正整数");
        $("#addBookNum").next().show();
        flag = false;
    } else {
        $('#addBookNum').parent().removeClass("has-error");
        $('#addBookNum').next().text("");
        $("#addBookNum").next().hide();
    }
    return flag;
}
