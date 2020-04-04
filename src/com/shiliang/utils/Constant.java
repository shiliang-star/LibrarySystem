package com.shiliang.utils;

/**
 * 存储一些常量信息
 */
public class Constant {

    //导出书籍信息excel存储的路径
    public static String exportBook = "D:/download/export/book/";
    public static String exportReader = "D:/download/export/reader/";

    //批量导入时，错误的数据存储地址
    public static String exportError = "D:/download/exportError/";

    //模板文件存放目录
    public static String template = "D:/download/template/";

    //文件上传的路径
    public static String uploadPath = "D:/download/upload/";


    //excel格式：https://blog.csdn.net/xiaoranzhizhu/article/details/70473734
    public static String xls = "application/vnd.ms-excel";
    public static String xlsx = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    /**
     * 获取导入的大小
     *
     *      * 根据内存的大小进行计算
     */

    public static int getimportsize(int size){

        if (size >= 10000000) {
            return 100000;
        }
        if (size >= 1000000) {
            return 10000;
        }
        if (size >= 100000) {
            return 10000;
        }
        if (size >= 10000) {
            return 1000;
        }
        if (size >= 1000) {
            return 100;
        }
        if (size >= 100) {
            return 10;
        }
        return 1;

    }

}
