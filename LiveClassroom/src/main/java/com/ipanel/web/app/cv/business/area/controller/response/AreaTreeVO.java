package com.ipanel.web.app.cv.business.area.controller.response;

import com.ipanel.web.app.cv.entity.Area;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/20 14:23
 * Modified By:
 * Description:
 */
@Data
public class AreaTreeVO implements Serializable {

    @ApiModelProperty(value = "区域id")
    private Integer id;

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "区域级别")
    private Integer areaLevel;

    @ApiModelProperty(value = "区域类型1单位0区域")
    private Integer isCompany;

    @ApiModelProperty(value = "子区域")
    private List<Area> childrenArea;

    public AreaTreeVO() {
    }

    public AreaTreeVO(Area area, List<Area> childrenArea) {
        this.id = area.getId();
        this.areaName = area.getAreaName();
        this.areaLevel = area.getAreaLevel();
        this.isCompany = area.getIsCompany();
        this.childrenArea = childrenArea;
    }
}
