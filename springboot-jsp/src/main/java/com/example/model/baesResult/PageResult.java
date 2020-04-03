package com.example.model.baesResult;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/11/14 10:48
 * Modified By:
 * Description:
 */
@Data
public class PageResult<T> extends Result<T> implements Serializable {
    private Long total;
    private Integer currentPage;
    private Integer totalPage;
}
