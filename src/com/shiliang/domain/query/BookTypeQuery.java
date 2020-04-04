package com.shiliang.domain.query;

import com.shiliang.utils.DataUtils;

/**
 * @author SL
 * @create 2020-03-11 18:09
 * @description 条件查询封装
 */
public class BookTypeQuery {
    private String currentPage;
    private String typeName;

    public BookTypeQuery() {
    }

    public Integer getCurrentPage() {
        return DataUtils.getPageCode(currentPage);
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
