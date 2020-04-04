$(function () {

    $.post("/LibrarySystem_SL/admin/bookManageController_getAllBookTypes.action", function (data) {

        // alert(data.name);
        $.each(data, function (i, val) {
            // alert(p[i].name);
            $("#bookTypeId").append("<option value=" + data[i].id + ">" + data[i].name + "</option>");

            if (bookType == data[i].id) {
                // $("#readerType").option.selected = true;
                $("#bookTypeId option[value='"+data[i].id+"']").attr("selected","selected");
            }

        });


    }, "json");







});