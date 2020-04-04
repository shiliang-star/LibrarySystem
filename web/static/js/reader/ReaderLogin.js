$(function () {
    $("#login_submit").click(function () {
        // alert("hahahah");

        if (!validLogin()) {
            return;
        }

        var postdata = "paperNo=" + $.trim($("#username").val()) + "&password=" + $.trim($("#password").val());

        $.post("/LibrarySystem_SL/readerLoginController_login.action", postdata, function (data) {
            // alert(data);

            if (data == 1) {
                window.location.href = "/LibrarySystem_SL/reader/index.jsp";
            }else if (data == -1) {
                showinfo("用户名不存在！")
            }else if (data == -2) {
                showinfo("密码不正确！");
            }
        });

    });

});


function showinfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");
}



function validLogin() {
    var flag = true;
    var username = $.trim($("#username").val());
    if (username == "") {
        $('#username').parent().addClass("has-error");
        $('#username').next().text("请输入账号");
        $("#username").next().show();
        flag = false;
    } else if (username.length < 2 || username.length > 15) {
        $("#username").parent().addClass("has-error");
        $("#username").next().text("账号长度必须在2~15之间");
        $("#username").next().show();
        flag = false;
    } else {
        $('#username').parent().removeClass("has-error");
        $('#username').next().text("");
        $("#username").next().hide();
    }
    var password = $.trim($("#password").val());
    if (password == "") {
        $('#password').parent().addClass("has-error");
        $('#password').next().text("请输入密码");
        $("#password").next().show();
        flag = false;
    } else if (password.length < 3 || password.length > 15) {
        $("#password").parent().addClass("has-error");
        $("#password").next().text("密码长度必须在3~15之间");
        $("#password").next().show();
        flag = false;
    } else {
        $('#password').parent().removeClass("has-error");
        $('#password').next().text("");
        $("#password").next().hide();
    }
    return flag;
}