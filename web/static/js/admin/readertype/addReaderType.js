$(function () {

    $("#addType").blur(function () {
        var postData = "name=" + $.trim($("#addType").val());

        // console.log(postData);
        $.post("/LibrarySystem_SL/admin/readerTypeManageController_getReaderTypeByName.action", postData, function (data) {
            if (data == -1) {
             //存在该读者类型名称
                $('#addType').parent().addClass("has-error");
                $('#addType').next().text("读者类型名称已存在");
                $("#addType").next().show();
                $("#addReaderType").attr("disabled", "true");

            } else {
                //不存在该读者类型名称
                $('#addType').parent().removeClass("has-error");
                $('#addType').next().text("");
                $("#addType").next().hide();
                $("#addReaderType").removeAttr("disabled");

            }
        });


    });




    $("#addReaderType").click(function () {
        if (!validAddReaderType()) {
            return;
        }

        var postData = "name="+$.trim($("#addType").val())+"&maxNum="+$.trim($("#addMaxNum").val())+
            "&bday="+$.trim($("#addBday").val())+ "&penalty="+$.trim($("#addPenalty").val())+
            "&renewDays="+$.trim($("#addrenewDays").val());

        $.post("/LibrarySystem_SL/admin/readerTypeManageController_addReaderType.action", postData, function (data) {
            if (data == 1) {
                $("#addModal").modal("hide");
                showInfo("添加成功");
            } else {
                $("#addModal").modal("hide");
                showInfo("添加失败")
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

function validAddReaderType() {
    var flag = true;
    var typeName = $.trim($("#readerTypeName").val());
    if (typeName == "") {
        $('#addType').parent().addClass("has-error");
        $('#addType').next().text("请输入读者类型名称");
        $("#addType").next().show();
        flag = false;
    } else if (!reg.test(typeName)) {
        $('#addType').parent().addClass("has-error");
        $('#addType').next().text("读者类型名称必须为中文");
        $("#addType").next().show();
        flag = false;
    } else {
        $('#addType').parent().removeClass("has-error");
        $('#addType').next().text("");
        $("#addType").next().hide();
    }
    var maxNum = $.trim($("#addMaxNum").val());
    if (maxNum == "") {
        $('#addMaxNum').parent().addClass("has-error");
        $('#addMaxNum').next().text("请输入最大借阅数量");
        $("#addMaxNum").next().show();
        flag = false;
    } else if (maxNum <= 0 || maxNum != parseInt(maxNum)) {
        $('#addMaxNum').parent().addClass("has-error");
        $('#addMaxNum').next().text("最大借阅数量必须为正整数");
        $("#addMaxNum").next().show();
        flag = false;
    } else {
        $('#addMaxNum').parent().removeClass("has-error");
        $('#addMaxNum').next().text("");
        $("#addMaxNum").next().hide();
    }
    var bday = $.trim($("#addBday").val());
    if (bday == "") {
        $('#addBday').parent().addClass("has-error");
        $('#addBday').next().text("请输入最大借阅天数");
        $("#addBday").next().show();
        flag = false;
    } else if (bday <= 0 || bday != parseInt(bday)) {
        $('#addBday').parent().addClass("has-error");
        $('#addBday').next().text("最大借阅天数必须为正整数");
        $("#addBday").next().show();
        flag = false;
    } else {
        $('#addBday').parent().removeClass("has-error");
        $('#addBday').next().text("");
        $("#addBday").next().hide();
    }
    var penalty = $.trim($("#addPenalty").val());
    if (penalty == "") {
        $('#addPenalty').parent().addClass("has-error");
        $('#addPenalty').next().text("请输入逾期每日罚金");
        $("#addPenalty").next().show();
        flag = false;
    } else if (penalty <= 0 || penalty != parseInt(penalty)) {
        $('#addPenalty').parent().addClass("has-error");
        $('#addPenalty').next().text("逾期每日罚金必须为正整数");
        $("#addPenalty").next().show();
        flag = false;
    } else {
        $('#addPenalty').parent().removeClass("has-error");
        $('#addPenalty').next().text("");
        $("#addPenalty").next().hide();
    }
    var renewDays = $.trim($("#addrenewDays").val());
    if (renewDays == "") {
        $('#addrenewDays').parent().addClass("has-error");
        $('#addrenewDays').next().text("请输入续借天数");
        $("#addrenewDays").next().show();
        flag = false;
    } else if (renewDays <= 0 || renewDays != parseInt(renewDays)) {
        $('#addrenewDays').parent().addClass("has-error");
        $('#addrenewDays').next().text("续借天数必须为正整数");
        $("#addrenewDays").next().show();
        flag = false;
    } else {
        $('#addrenewDays').parent().removeClass("has-error");
        $('#addrenewDays').next().text("");
        $("#addrenewDays").next().hide();
    }
    return flag;
}