$(function () {
    $("#updateReader").click(function () {
        if (!validUpdateReader()) {
            return;
        }

        var postData ="id="+$.trim($("#updateReaderID").val())+"&name="+$.trim($("#updateName").val())+"&phone="+$.trim($("#updatePhone").val())+
            "&email="+$.trim($("#updateEmail").val())+"&readerTypeId="+$.trim($("#updateReaderType").val());

        $.post("/LibrarySystem_SL/admin/readerManageController_updateReader.action", postData, function (data) {
            if (data == 1) {
                $("#updateModal").modal("hide");
                showInfo("修改成功");
            } else {
                $("#updateModal").modal("hide");
                showInfo("修改失败");
            }

        });
    });
    $('#modal_info').on('hide.bs.modal', function () {//提示模糊框隐藏时候触发
        location.reload();  	//刷新当前页面
    });

});

function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");
}

function updateReader(id) {

    var postData = "id=" + id;

    $("#updateReaderType").find("option").not(":first").remove();
    $.post("/LibrarySystem_SL/admin/readerManageController_getReaderInfo.action", postData, function (datas) {

        $("#updateReaderID").val(datas.id);
        $("#updatePaperNO").val(datas.paperNO);
        $("#updateName").val(datas.readerName);
        $("#updatePhone").val(datas.phone);
        $("#updateEmail").val(datas.email);


        $.post("/LibrarySystem_SL/admin/readerManageController_getAllReaderTypes.action", function (data) {
            $.each(data, function (i, val) {
                // alert(p[i].name);
                if (datas.readerType == data[i].name) {

                    $("#updateReaderType").append("<option value=" + data[i].id + " selected>" + data[i].name + "</option>");
                } else {

                    $("#updateReaderType").append("<option value=" + data[i].id + ">" + data[i].name + "</option>");
                }

            });


        }, "json");
    }, "json");


}

function validUpdateReader() {
    var flag = true;
    var paperNO = $.trim($("#updatePaperNO").val());
    if (paperNO == "") {
        $('#updatePaperNO').parent().addClass("has-error");
        $('#updatePaperNO').next().text("请输入读者证件号");
        $("#updatePaperNO").next().show();
        flag = false;
    } else {
        $('#updatePaperNO').parent().removeClass("has-error");
        $('#updatePaperNO').next().text("");
        $("#updatePaperNO").next().hide();
    }
    var reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
    var name = $.trim($("#updateName").val());
    if (name == "") {
        $('#updateName').parent().addClass("has-error");
        $('#updateName').next().text("请输入真实姓名");
        $("#updateName").next().show();
        flag = false;
    } else if (!reg.test(name)) {
        $('#updateName').parent().addClass("has-error");
        $('#updateName').next().text("真实姓名必须为中文");
        $("#updateName").next().show();
        flag = false;
    } else {
        $('#updateName').parent().removeClass("has-error");
        $('#updateName').next().text("");
        $("#updateName").next().hide();
    }

    var phone = $.trim($("#updatePhone").val());
    if (phone == "") {
        $('#updatePhone').parent().addClass("has-error");
        $('#updatePhone').next().text("请输入联系号码");
        $("#updatePhone").next().show();
        flag = false;
    } else if (!(/^1[34578]\d{9}$/.test(phone))) {
        //电话号码格式的校验
        $('#updatePhone').parent().addClass("has-error");
        $('#updatePhone').next().text("手机号码有误");
        $("#updatePhone").next().show();
        return false;
    } else {
        $('#updatePhone').parent().removeClass("has-error");
        $('#updatePhone').next().text("");
        $("#updatePhone").next().hide();
    }

    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    var email = $.trim($("#updateEmail").val());
    if (email == "") {
        $('#updateEmail').parent().addClass("has-error");
        $('#updateEmail').next().text("请输入邮箱");
        $("#updateEmail").next().show();
        flag = false;
    } else if (!reg.test(email)) {
        //邮箱格式的校验
        $('#updateEmail').parent().addClass("has-error");
        $('#updateEmail').next().text("邮箱格式有误");
        $("#updateEmail").next().show();
        return false;
    } else {
        $('#updateEmail').parent().removeClass("has-error");
        $('#updateEmail').next().text("");
        $("#updateEmail").next().hide();
    }
    var readerType = $.trim($("#updateReaderType").val());
    if (readerType == -1) {
        $('#updateReaderType').parent().addClass("has-error");
        $('#updateReaderType').next().text("请选择读者类型");
        $("#updateReaderType").next().show();
        flag = false;
    } else {
        $('#updateReaderType').parent().removeClass("has-error");
        $('#updateReaderType').next().text("");
        $("#updateReaderType").next().hide();
    }
    return flag;
}


