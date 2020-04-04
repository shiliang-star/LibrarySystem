$(function () {
    $("#updateBook").click(function () {
        if (!validUpdateBook()) {
            return;
        }
        // alert("hahah");
     ajax({
         method:"POST",
         url: "/LibrarySystem_SL/admin/bookManageController_updateBook.action",
         params:"id="+$.trim($("#updateBookId").val())+"&isbn="+$.trim($("#updateISBN").val())+
             "&name="+$.trim($("#updateBookName").val())+"&bookTypeId="+$.trim($("#updateBookType").val())+
             "&author="+$.trim($("#updateAuthor").val())+"&press="+$.trim($("#updatePress").val())+
             "&price="+$.trim($("#updatePrice").val())+"&description="+$.trim($("#updateDescription").val()),
         callback: function (data) {
             if (data == 1) {
                 //关闭之前的模态框
                 $("#updateModal").modal("hide");
                 showInfo("修改成功");
             } else {
                 $("#updateModal").modal("hide");
                 showInfo("修改失败")
             }

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

function updateBook(id) {
    // alert("hahaha");
    $("#updateBookType").find("option").not(":first").remove();
    ajax({
        method:"POST",
        url: "/LibrarySystem_SL/admin/bookManageController_getBookInfo.action",
        params: "id=" + id,
        type: "json",
        callback: function (data) {
            // alert(data);
            $("#updateBookId").val(data.id);
            $("#updateISBN").val(data.ISBN);
            $("#updateBookName").val(data.bookname);
            $("#updateAuthor").val(data.author);
            $("#updatePress").val(data.press);
            $("#updatePrice").val(data.price);
            $("#updateDescription").val(data.description);

            ajax({
                method:"POST",
                url: "/LibrarySystem_SL/admin/bookManageController_getAllBookTypes.action",
                type: "json",
                callback:function (data1) {
                    $.each(data1, function (i, val) {
                        if (data1[i].name == data.booktype) {
                            // alert(p[i].name);
                            $("#updateBookType").append("<option value=" + data1[i].id + " selected >" + data1[i].name + "</option>");
                        } else {
                            $("#updateBookType").append("<option value=" + data1[i].id + ">" + data1[i].name + "</option>");

                        }

                    })
                }

            })
        }}
   );
}


function validUpdateBook() {
    var flag = true;

    var ISBN = $.trim($("#updateISBN").val());
    if (ISBN == "") {
        $('#updateISBN').parent().addClass("has-error");
        $('#updateISBN').next().text("请输入图书ISBN码");
        $("#updateISBN").next().show();
        flag = false;
    } else {
        $('#updateISBN').parent().removeClass("has-error");
        $('#updateISBN').next().text("");
        $("#updateISBN").next().hide();
    }

    var bookName = $.trim($("#updateBookName").val());
    if (bookName == "") {
        $('#updateBookName').parent().addClass("has-error");
        $('#updateBookName').next().text("请输入图书名称");
        $("#updateBookName").next().show();
        flag = false;
    } else {
        $('#updateBookName').parent().removeClass("has-error");
        $('#updateBookName').next().text("");
        $("#updateBookName").next().hide();
    }


    var bookType = $.trim($("#updateBookType").val());
    if (bookType == -1) {
        $('#updateBookType').parent().addClass("has-error");
        $('#updateBookType').next().text("请选择图书分类");
        $("#updateBookType").next().show();
        flag = false;
    } else {
        $('#updateBookType').parent().removeClass("has-error");
        $('#updateBookType').next().text("");
        $("#updateBookType").next().hide();
    }

    var author = $.trim($("#updateAuthor").val());
    if (author == "") {
        $('#updateAuthor').parent().addClass("has-error");
        $('#updateAuthor').next().text("请输入作者名称");
        $("#updateAuthor").next().show();
        flag = false;
    } else {
        $('#updateAuthor').parent().removeClass("has-error");
        $('#updateAuthor').next().text("");
        $("#updateAuthor").next().hide();
    }


    var press = $.trim($("#updatePress").val());
    if (press == "") {
        $('#updatePress').parent().addClass("has-error");
        $('#updatePress').next().text("请输入出版社名称");
        $("#updatePress").next().show();
        flag = false;
    } else {
        $('#updatePress').parent().removeClass("has-error");
        $('#updatePress').next().text("");
        $("#updatePress").next().hide();
    }
    var price = $.trim($("#updatePrice").val());
    if (price == "") {
        $('#updatePrice').parent().addClass("has-error");
        $('#updatePrice').next().text("请输入总数量");
        $("#updatePrice").next().show();
        flag = false;
    } else if (price <= 0 || price != parseInt(price)) {
        $('#updatePrice').parent().addClass("has-error");
        $('#updatePrice').next().text("数量必须为正整数");
        $("#updatePrice").next().show();
        flag = false;
    } else {
        $('#updatePrice').parent().removeClass("has-error");
        $('#updatePrice').next().text("");
        $("#updatePrice").next().hide();
    }
    return flag;
}