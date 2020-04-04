$(function () {
    $("#btn_add").click(function () {

        $("#addBookType").find("option").not(":first").remove();
        $.post("/LibrarySystem_SL/admin/bookManageController_getAllBookTypes.action", function (data) {

            // alert(data.name);
            $.each(data, function (i, val) {
                // alert(p[i].name);
                $("#addBookType").append("<option value=" + data[i].id + ">" + data[i].name + "</option>")

            });

            $("#addModal").modal("show");


        }, "json");
    });


    $("#addBook").click(function () {

        if (!validAddBook()) {
            return;
        }
        let postData = "isbn="+$.trim($("#addISBN").val())+"&name="+$.trim($("#addBookName").val())+"&bookTypeId="+
            $.trim($("#addBookType").val())+"&author="+$.trim($("#addAuthor").val())+"&press="+$.trim($("#addPress").val())+
            "&num="+$.trim($("#addNum").val())+"&price="+$.trim($("#addPrice").val())+"&description="+$.trim($("#addDescription").val());

        // alert(postData);

        $.post("/LibrarySystem_SL/admin/bookManageController_addBook.action", postData, function (data) {
            if (data == 1) {
                $("#addModal").modal("hide");
                showInfo("添加成功");
            }else if (data == -1) {
                $("#addModal").modal("hide");
                showInfo("添加失败");
            }else if (data == -2) {
                $("#addModal").modal("hide");
                showInfo("图书ISBN重复，请重新输入");
            }

        });
    });
    $("#modal_info").on("hide.bs.modal", function () {
        window.location.reload();

    });

});


function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal("show");

}


function validAddBook() {
    var flag = true;
    var ISBN = $.trim($("#addISBN").val());
    if (ISBN == "") {
        $('#addISBN').parent().addClass("has-error");
        $('#addISBN').next().text("请输入图书ISBN码");
        $("#addISBN").next().show();
        flag = false;
    } else {
        $('#addISBN').parent().removeClass("has-error");
        $('#addISBN').next().text("");
        $("#addISBN").next().hide();
    }

    var bookName = $.trim($("#addBookName").val());
    if (bookName == "") {
        $('#addBookName').parent().addClass("has-error");
        $('#addBookName').next().text("请输入图书名称");
        $("#addBookName").next().show();
        flag = false;
    } else {
        $('#addBookName').parent().removeClass("has-error");
        $('#addBookName').next().text("");
        $("#addBookName").next().hide();
    }


    var bookType = $.trim($("#addBookType").val());
    if (bookType == -1) {
        $('#addBookType').parent().addClass("has-error");
        $('#addBookType').next().text("请选择图书分类");
        $("#addBookType").next().show();
        flag = false;
    } else {
        $('#addBookType').parent().removeClass("has-error");
        $('#addBookType').next().text("");
        $("#addBookType").next().hide();
    }

    var author = $.trim($("#addAuthor").val());
    if (author == "") {
        $('#addAuthor').parent().addClass("has-error");
        $('#addAuthor').next().text("请输入作者名称");
        $("#addAuthor").next().show();
        flag = false;
    } else {
        $('#addAuthor').parent().removeClass("has-error");
        $('#addAuthor').next().text("");
        $("#addAuthor").next().hide();
    }


    var press = $.trim($("#addPress").val());
    if (press == "") {
        $('#addPress').parent().addClass("has-error");
        $('#addPress').next().text("请输入出版社名称");
        $("#addPress").next().show();
        flag = false;
    } else {
        $('#addPress').parent().removeClass("has-error");
        $('#addPress').next().text("");
        $("#addPress").next().hide();
    }

    var num = $.trim($("#addNum").val());
    if (num == "") {
        $('#addNum').parent().addClass("has-error");
        $('#addNum').next().text("请输入总数量");
        $("#addNum").next().show();
        flag = false;
    } else if (num <= 0 || num != parseInt(num)) {
        $('#addNum').parent().addClass("has-error");
        $('#addNum').next().text("数量必须为正整数");
        $("#addNum").next().show();
        flag = false;
    } else {
        $('#addNum').parent().removeClass("has-error");
        $('#addNum').next().text("");
        $("#addNum").next().hide();
    }


    var price = $.trim($("#addPrice").val());
    if (price == "") {
        $('#addPrice').parent().addClass("has-error");
        $('#addPrice').next().text("请输入总数量");
        $("#addPrice").next().show();
        flag = false;
    } else if (price <= 0 || price != parseInt(price)) {
        $('#addPrice').parent().addClass("has-error");
        $('#addPrice').next().text("数量必须为正整数");
        $("#addPrice").next().show();
        flag = false;
    } else {
        $('#addPrice').parent().removeClass("has-error");
        $('#addPrice').next().text("");
        $("#addPrice").next().hide();
    }
    return flag;
}

