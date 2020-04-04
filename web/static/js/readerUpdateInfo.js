$(function () {
    $("#reader_updateInfo").click(function () {
        // alert("hahahha");

        var postdata = "phone="+$.trim($("#phone").val())
            +"&email="+$.trim($("#email").val());
        $.post("/LibrarySystem_SL/readerInfoController_updateReaderInfo.action", postdata, function (data) {

            // alert(data);
            if (data == 1) {
                $("#updateinfo").modal("hide");
                showInfo("更新成功");
            }else if (data == -1) {
                $("#updateinfo").modal("hide");
                showInfo("更新失败");

            }

        });
    });
    //监听模态框的关闭
    $("#modal_info").on("hide.bs.modal", function () {
        window.location.reload();//刷新页面
    });

});

function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");

}
