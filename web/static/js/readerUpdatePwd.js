$(function () {
    $("#update_readerPwd").click(function () {
        // alert("hahahaha");
        if (!validUpdateReaderPwd) {
            return;
        }
        var postdata = "oldPwd="+$.trim($("#oldPwd").val())+"&newPwd="+$.trim($("#newPwd").val())+"&confirmPwd="+$.trim($("#confirmPwd").val());
        $.post("/LibrarySystem_SL/readerInfoController_updateReaderPassword.action", postdata, function (data) {
            // alert(data);
            if (data == 1) {
                $("#updatepwd").modal("hide");
                showinfo("修改成功,即将返回登陆页面");
                $("#modal_info").on("hide.bs.modal", function () {
                    window.location.href = "readerLogin.jsp";
                });
            }else if (data == -1) {
                showinfo("原密码输入不正确");
            }else if (data == -2) {
                showinfo("确认密码输入不一致");
            }

        });
    });

});

function showinfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");
}

function validUpdateReaderPwd() {
    var flag = true;

    var oldPwd = $.trim($("#oldPwd").val());
    if (oldPwd == "") {
        $('#oldPwd').parent().addClass("has-error");
        $('#oldPwd').next().text("请输入原密码");
        $("#oldPwd").next().show();
        flag = false;
    } else {
        $('#oldPwd').parent().removeClass("has-error");
        $('#oldPwd').next().password("");
        $("#oldPwd").next().hide();
    }

    var newPwd = $.trim($("#newPwd").val());
    if (newPwd == "") {
        $('#newPwd').parent().addClass("has-error");
        $('#newPwd').next().text("请输入新密码");
        $("#newPwd").next().show();
        flag = false;
    } else {
        $('#newPwd').parent().removeClass("has-error");
        $('#newPwd').next().password("");
        $("#newPwd").next().hide();
    }


    var confirmPwd = $.trim($("#confirmPwd").val());
    if (confirmPwd == -1) {
        $('#confirmPwd').parent().addClass("has-error");
        $('#confirmPwd').next().text("请再次输入新密码");
        $("#confirmPwd").next().show();
        flag = false;
    } else {
        $('#confirmPwd').parent().removeClass("has-error");
        $('#confirmPwd').next().password("");
        $("#confirmPwd").next().hide();
    }


    return flag;
}