package com.ipanel.web.app.cv.business.area.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/19 16:09
 * Modified By:
 * Description:
 */
@Data
public class AddAreaReq {

    @ApiModelProperty(value = "父区域id", required = true)
    @NotNull(message = "parentId不能为空")
    private Integer parentId;

    @ApiModelProperty(value = "区域名称", required = true)
    private String areaName;

    @ApiModelProperty(value = "是否为社区", required = true)
    private Integer isCompany;

}
