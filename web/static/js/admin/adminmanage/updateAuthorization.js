function power(id) {

    var postData = "id=" + id;
    $.post("/LibrarySystem_SL/admin/AuthorizationManageController_getAuthorizationById.action", postData, function (data) {
        $("#aid").val(id);
        if (data.typeSet == 1) {
            $("#typeSet").attr("checked", "checked");
        }
        if (data.bookSet == 1) {
            $("#bookSet").attr("checked", "checked");
        }
        if (data.readerSet == 1) {
            $("#readerSet").attr("checked", "checked");
        }
        if (data.borrowSet == 1) {
            $("#borrowSet").attr("checked", "checked");
        }
        if (data.backSet == 1) {
            $("#backSet").attr("checked", "checked");
        }
        if (data.forfeitSet == 1) {
            $("#forfeitSet").attr("checked", "checked");
        }
        if (data.sysSet == 1) {
            $("#sysSet").attr("checked", "checked");
        }

    }, "json");

};

function setPower() {
    //jquery获取复选框的值
    var power = [];
    $("input[name='power']:checked").each(function () {
        power.push($(this).val());
    });
    if (power.length == 0) {
        alert("您没有选择任何的内容.");
    } else {
        // alert(power);
        var postData = "id=" + $.trim($("#aid").val()) + "&power=" + power;

        $.post("/LibrarySystem_SL/admin/AuthorizationManageController_updateAuthorization.action", postData, function (data) {
            if (data == 1) {
                $("#powerModal").modal("hide");
                showInfo("设置成功");
            } else {
                $("#powerModal").modal("hide");
                showInfo("设置失败")
            }
        });
    }



};
$(function () {
    $("#modal_info").on("hide.bs.modal", function () {
        location.reload();
    });

});
