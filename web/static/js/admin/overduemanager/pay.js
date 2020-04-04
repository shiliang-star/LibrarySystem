function pay(id) {
    var postData = "borrowId=" + id;

    $.post("/LibrarySystem_SL/admin/overDueManageController_payForfeit.action", postData, function (data) {
        if (data == 1) {
            showInfo("付款成功");
        }else if (data == -1) {
            showInfo("请先去还书再来缴纳罚金");
        }else if (data == -2) {
            showInfo("你已支付罚金，无需再次支付");
        } else {
            showInfo("付款失败");
        }

    });

}
function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal('show');
}
$(function () {
    $('#modal_info').on('hide.bs.modal', function () {//提示模糊框隐藏时候触发
        location.reload();  	//刷新当前页面
    });

});
