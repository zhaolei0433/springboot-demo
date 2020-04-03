package com.ipanel.web.app.cv.business.area.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaolei
 * Create: 2018/9/20 16:16
 * Modified By:
 * Description:
 */
@Data
public class UpdateAreaReq {
    @ApiModelProperty(value = "区域名称", required = true)
    private String areaName;
}
