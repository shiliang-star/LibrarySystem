$(function () {
    $("#batchAdd").click(function () {
        // alert("jhaja");

        ajax({
            method:"POST",
            url:"/LibrarySystem_SL/admin/bookManageController_batchAddBook.action",
            params:"fileName="+$.trim($("#excel").val()),
            type:"json",
            callback: function (data) {
                // alert(data.msg);
                if (data.code == 1) {
                    showInfo(data.msg);
                }else if (data.code == 2) {
                    showInfo(data.msg + ",数据已导出：<a href='/LibrarySystem_SL/fileDownloadController_fileDownload.action?fileType=3&fileName=" + data.filePath + "' >点击下载</a>");

                } else {
                    showInfo(data.msg);
                }
            }

        });


    });

});


function showInfo(msg) {
    $("#div_info").html(msg);
    $("#modal_info").modal("show");
}
