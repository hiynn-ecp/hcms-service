package com.hiynn.cms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author 张朋
 * @date 2019/10/30 14:59
 */
@JsonIgnoreProperties(value = {"size",
        "startRow",
        "endRow",
        "prePage",
        "nextPage",
        "isFirstPage",
        "isLastPage",
        "hasPreviousPage",
        "hasNextPage",
        "navigatePages",
        "navigatepageNums",
        "navigateFirstPage",
        "navigateLastPage"})
public class PageData<T> extends com.github.pagehelper.PageInfo<T> {

    private static final long serialVersionUID = 8428152173411079713L;

    public PageData() {
    }

    /**
     * 包装Page对象
     *
     * @param target
     */
    public PageData(List<T> target) {
        super(target, 8);
    }

}
