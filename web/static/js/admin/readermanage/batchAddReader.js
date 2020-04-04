$(function () {
    $("#batchAdd").click(function () {

        var postData = "fileName=" + $.trim($("#excel").val());
        $.post("/LibrarySystem_SL/admin/readerManageController_batchAddReader.action", postData, function (data) {
            // alert(data.msg);
            if (data.code == 1) {
                showInfo2(data.msg);
            }else if (data.code == 2) {
                showInfo2(data.msg + ",数据已导出：<a href='/LibrarySystem_SL/fileDownloadController_fileDownload.action?fileType=3&fileName=" + data.filePath + "' >点击下载</a>");

            } else {
                showInfo(data.msg);
            }

        },"json");



    });

});


function showInfo2(msg) {
    $("#div_info").html(msg);
    $("#modal_info").modal("show");
}
