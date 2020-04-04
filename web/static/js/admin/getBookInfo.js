function getBookInfo(id) {

    ajax({
        method:"POST",
        url:"/LibrarySystem_SL/admin/bookManageController_getBookInfo.action",
        params: "id="+id,
        type:"json",
        callback: function (data) {
            // alert(data.bookname);
            $("#findISBN").val(data.ISBN);
            $("#findBookName").val(data.bookname);
            $("#findBookType").val(data.booktype);
            $("#findAuthor").val(data.author);
            $("#findPress").val(data.press);
            $("#findNum").val(data.num);
            $("#findCurrentNum").val(data.currentNum);
            $("#findPrice").val(data.price);
            $("#findAdmin").val(data.adminName);
            $("#findDescription").val(data.description);
        }
    });

}