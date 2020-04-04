$(function () {
    $("#addAdmin").click(function () {
        if (!validAddAdmin()) {
            return;
        }
        // alert("haha");
        var postData = "username="+$.trim($("#addUsername").val())+"&name="+$.trim($("#addName").val())+
            "&phone="+$.trim($("#addPhone").val());
        $.post("/LibrarySystem_SL/admin/adminManageController_addAdmin.action", postData, function (data) {

            if (data == 1) {
                showInfo("添加成功");
            }else if (data == -2) {
                showInfo("管理员用户名重复");
            } else {
                showInfo("添加失败")
            }

        });
    });
    $('#modal_info').on('hide.bs.modal', function () {//提示模糊框隐藏时候触发
        location.reload();  	//刷新当前页面
    });

});

function validAddAdmin() {
    var flag = true;
    var username = $.trim($("#addUsername").val());
    if (username == "") {
        $('#addUsername').parent().addClass("has-error");
        $('#addUsername').next().text("请输入用户名");
        $("#addUsername").next().show();
        flag = false;
    } else if (username.length < 2 || username.length > 15) {
        $("#addUsername").parent().addClass("has-error");
        $("#addUsername").next().text("用户名长度必须在2~15之间");
        $("#addUsername").next().show();
        flag = false;
    } else {
        $('#addUsername').parent().removeClass("has-error");
        $('#addUsername').next().text("");
        $("#addUsername").next().hide();
    }
    var reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
    var name = $.trim($("#addName").val());
    if (name == "") {
        $('#addName').parent().addClass("has-error");
        $('#addName').next().text("请输入真实姓名");
        $("#addName").next().show();
        flag = false;
    } else if (!reg.test(name)) {
        $('#addName').parent().addClass("has-error");
        $('#addName').next().text("真实姓名必须为中文");
        $("#addName").next().show();
        flag = false;
    } else {
        $('#addName').parent().removeClass("has-error");
        $('#addName').next().text("");
        $("#addName").next().hide();
    }

    var phone = $.trim($("#addPhone").val());
    if (phone == "") {
        $('#addPhone').parent().addClass("has-error");
        $('#addPhone').next().text("请输入联系号码");
        $("#addPhone").next().show();
        flag = false;
    } else if (!(/^1[34578]\d{9}$/.test(phone))) {
        //电话号码格式的校验
        $('#addPhone').parent().addClass("has-error");
        $('#addPhone').next().text("手机号码有误");
        $("#addPhone").next().show();
        return false;
    } else {
        $('#addPhone').parent().removeClass("has-error");
        $('#addPhone').next().text("");
        $("#addPhone").next().hide();
    }
    return flag;
}

function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal('show');
}

