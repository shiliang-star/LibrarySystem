function exportBook() {


    $.post("/LibrarySystem_SL/admin/bookManageController_exportBook.action", function (data) {
        showInfos("数据已导出：<a href='/LibrarySystem_SL/fileDownloadController_fileDownload.action?fileType=1&fileName=" + data + "'  >点击下载</a>");
    });



}


function showInfos(msg) {
    $("#div_info").html(msg);
    $("#modal_info").modal("show");
}

