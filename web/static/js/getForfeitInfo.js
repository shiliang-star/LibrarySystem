function getForfeitInfoById(id) {
    var postData = "borrowId=" + id;
    $.post("/LibrarySystem_SL/admin/overDueManageController_getForfeitInfo.action", postData, function (data) {
        $("#borrowId").val(data.borrowId);
        $("#ISBN").val(data.ISBN);
        $("#bookName").val(data.bookName);
        $("#bookType").val(data.bookType);
        $("#paperNO").val(data.paperNO);
        $("#readerName").val(data.readerName);
        $("#readerType").val(data.readerType);
        $("#overday").val(data.overday);
        $("#admin").val(data.admin);
        if (data.isPay == 0) {
            $("#state").val("未缴纳");
        }else if (data.isPay == 1) {
            $("#state").val("已缴纳");
        }

    },"json");

}