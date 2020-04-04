function backBook(id) {
    var postData = "borrowId=" + id;

    $.post("/LibrarySystem_SL/admin/backManageController_backBook.action", postData, function (data) {

        if (data == -1) {

            showInfo("该图书已归还");
        } else if (data==1){

            showInfo("归还成功");
        }else if (data == 2) {
            showInfo("归还成功，请缴纳逾期罚金");
        } else {

            showInfo("还书失败");
        }


    });

}

$(function () {
    $('#modal_info').on('hide.bs.modal', function () {//提示模糊框隐藏时候触发
        location.reload();  	//刷新当前页面
    });

});


function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal('show');
}

