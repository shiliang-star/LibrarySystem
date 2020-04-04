$(function () {


    // alert(readerType);

    $.post("/LibrarySystem_SL/admin/readerManageController_getAllReaderTypes.action", function (data) {
        $.each(data, function (i, val) {
            // alert(p[i].name);

            $("#readerType").append("<option value=" + data[i].id + ">" + data[i].name + "</option>");

            if (readerType == data[i].id) {
                // $("#readerType").option.selected = true;
                $("#readerType option[value='"+data[i].id+"']").attr("selected","selected");
            }

        });


    }, "json");

});