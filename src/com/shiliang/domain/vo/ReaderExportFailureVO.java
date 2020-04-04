package com.shiliang.domain.vo;

/**
 * @author SL
 * @create 2020-03-20 11:47
 * @description 对导入错误读者数据的信息的封装
 */
public class ReaderExportFailureVO {

    private String paperNO;//读者证件号码（用于登陆）
    private String readerName;//读者名称
    private String readerType;//读者类型
    private String phone;//读者联系方式
    private String email;//读者邮箱
    private String errorMsg;//导入错误的信息


    public ReaderExportFailureVO() {
    }

    public ReaderExportFailureVO(String paperNO, String readerName, String readerType, String phone, String email, String errorMsg) {
        this.paperNO = paperNO;
        this.readerName = readerName;
        this.readerType = readerType;
        this.phone = phone;
        this.email = email;
        this.errorMsg = errorMsg;
    }


    //方便创建对象
    public static ReaderExportFailureVO createReaderExportFailureVO(String paperNO, String readerName,
                                                                    String readerType, String phone,
                                                                    String email, String errorMsg) {
        return new ReaderExportFailureVO(paperNO, readerName, readerType, phone, email, errorMsg);
    }

    public String getPaperNO() {
        return paperNO;
    }

    public void setPaperNO(String paperNO) {
        this.paperNO = paperNO;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
