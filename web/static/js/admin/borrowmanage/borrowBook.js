$(function () {
    $("#btn_borrow").click(function () {
        var postData = "paperNo=" + $.trim($("#borrowReaderPaperNO").val()) + "&ISBN=" +
            $.trim($("#borrowBookISBN").val()) + "&password=" + $.trim($("#pwd").val());
        $.post("/LibrarySystem_SL/admin/borrowManageController_borrowBook.action", postData, function (data) {
            if (data.code == 1) {
                showInfo(data.msg);
            } else if (data.code == 2) {
                showInfo(data.msg);
            } else if (data.code == -4) {
                showInfo(data.msg);
            } else if (data.code == -1) {
                showInfo(data.msg);
            } else if (data.code == -2) {
                showInfo(data.msg);
            } else if (data.code == -3) {
                showInfo(data.msg);
            }else if (data.code == -5) {
                showInfo(data.msg);
            }else if (data.code == -6) {
                showInfo(data.msg);
            }

        }, "json");

    });

});


function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal('show');
}
