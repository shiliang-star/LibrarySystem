function deleteBookType(id) {
        // alert("hahaha");

    $.post("/LibrarySystem_SL/admin/bookTypeManageController_deleteBookType.action", "id=" + id, function (data) {
        if (data == 1) {
            showInfo("删除成功")
        }else if (data == -1) {
            showInfo("删除失败")
        }

    });
    $("#modal_info").on("hide.bs.modal", function () {
        window.location.reload();

    });

};

function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");

}

