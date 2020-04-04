function deleteBook(id) {
    var postData = "id=" + id;
    $.post("/LibrarySystem_SL/admin/bookManageController_deleteBook.action", postData, function (data) {
        if (data == 1) {
            showInfo("删除成功");
        } else {
            showInfo("删除失败");
        }

    });

}