package com.iot.common.result;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页结果
 * @author wan
 */
@Data
public class PageResult<T> {
    // 列表数据
    private List<T> list;
    // 总记录数
    private long total;
    // 当前页码
    private int pageNum;
    // 每页大小
    private int pageSize;
    // 总页数
    private int totalPages;

    // 提供一个静态方法快速转换 Spring 的 Page 对象
    public static <T> PageResult<T> restPage(Page<T> pageInfo) {
        PageResult<T> result = new PageResult<>();
        result.setList(pageInfo.getContent());
        result.setTotal(pageInfo.getTotalElements());
        result.setPageNum(pageInfo.getNumber() + 1);
        result.setPageSize(pageInfo.getSize());
        result.setTotalPages(pageInfo.getTotalPages());
        return result;
    }
}
