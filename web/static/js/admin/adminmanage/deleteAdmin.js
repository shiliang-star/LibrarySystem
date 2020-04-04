function deleteAdmin(id) {

    var postData = "id=" + id;

    $.post("/LibrarySystem_SL/admin/adminManageController_deleteAdmin.action", postData, function (data) {
        if (data == 1) {
            showInfo("删除成功");
        } else {
            showInfo("删除失败")
        }

    });


};
$(function () {
    $("#modal_info").on("hide.bs.modal", function () {
        location.reload();
    });

});
