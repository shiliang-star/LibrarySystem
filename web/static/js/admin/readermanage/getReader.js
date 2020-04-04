function getReaderInfo(id) {

    var postData = "id=" + id;

    $.post("/LibrarySystem_SL/admin/readerManageController_getReaderInfo.action", postData, function (data) {
        $("#findPaperNO").val(data.paperNO);
        $("#findReaderName").val(data.readerName);
        $("#findReaderType").val(data.readerType);
        $("#findPhone").val(data.phone);
        $("#findEmail").val(data.email);
        $("#findAdmin").val(data.adminName);
    }, "json");


}