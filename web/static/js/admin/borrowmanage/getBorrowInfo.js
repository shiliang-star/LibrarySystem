function getBorrowInfoById(id) {

    var postData = "id=" + id;
    $.post("/LibrarySystem_SL/admin/borrowManageController_getBorrowInfoById.action", postData, function (data) {
        $("#borrowId").val(data.borrowId);
        $("#ISBN").val(data.ISBN);
        $("#bookName").val(data.bookName);
        $("#bookType").val(data.bookType);
        $("#paperNO").val(data.paperNO);
        $("#readerName").val(data.readerName);
        $("#readerType").val(data.readerType);
        $("#overday").val(data.overday);
        $("#admin").val(data.adminName);
        if (data.state == 0) {
            $("#state").val("未归还");
        }else if (data.state == 1) {
            $("#state").val("逾期未还");
        }else if (data.state == 2) {
            $("#state").val("已归还");
        }else if (data.state == 3) {
            $("#state").val("续借未归还");
        }else if (data.state == 4) {
            $("#state").val("续借逾期未归还");
        }else if (data.state == 5) {
            $("#state").val("续借已归还");
        }


    }, "json");

}