package com.ipanel.web.app.cv.business.area.controller.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/10/11 17:21
 * Modified By:
 * Description:
 */
@Data
public class AreaTreeEL implements Serializable {
    private Integer id;
    private String label;
    private List<AreaTreeEL> children;

    public AreaTreeEL() {
    }

    public AreaTreeEL(Integer id, String label, List<AreaTreeEL> children) {
        this.id = id;
        this.label = label;
        this.children = children;
    }
}
