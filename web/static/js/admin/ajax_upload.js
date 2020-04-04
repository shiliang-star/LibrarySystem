$(function () {
    //监听上传文件
    $(document).on("change", "#upload", function () {
        // alert("变动了！");
        //上传文件
        $("#uploadForm").ajaxSubmit({
            dataType:"json",//指定返回的类型
            success: function (data) {
                // alert("上传成功");
                if (data.code == 1) {
                    // showInfo(data.msg);
                    $("#excel").val(data.fileName);
                    // alert(data.fileName);
                } else {
                    showInfo(data.msg);
                }
            }



        });


    });

});


function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");

}

