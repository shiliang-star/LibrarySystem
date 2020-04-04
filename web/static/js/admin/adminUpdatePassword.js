$(function () {
    $("#update_adminPwd").click(function () {
        // alert("hahahaha");
        var postdata = "oldPwd="+$.trim($("#oldPwd").val())+"&newPwd="+$.trim($("#newPwd").val())+"&confirmPwd="+$.trim($("#confirmPwd").val());
        $.post("/LibrarySystem_SL/adminInfoController_updateAdminPassword.action", postdata, function (data) {
            // alert(data);
            if (data == 1) {
                $("#updatepwd").modal("hide");
                showinfo("修改成功,即将返回登陆页面");
                $("#modal_info").on("hide.bs.modal", function () {
                    window.location.href = "adminLogin.jsp";
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