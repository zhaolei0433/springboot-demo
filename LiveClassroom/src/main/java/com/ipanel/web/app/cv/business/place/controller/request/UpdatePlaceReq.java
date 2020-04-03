package com.ipanel.web.app.cv.business.place.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaolei
 * Create: 2018/10/16 09:25
 * Modified By:
 * Description:
 */
@Data
public class UpdatePlaceReq {
    @ApiModelProperty(value = "会场名称", required = true)
    private String placeName;
    @ApiModelProperty(value = "机顶盒地址：", required = true)
    private String placeMaster;
    @ApiModelProperty(value = "电话号码", required = true)
    private String phoneNum;
    @ApiModelProperty(value = "会场类型", required = true)
    private Integer placeType;
    @ApiModelProperty(value = "区域id", required = true)
    private Integer areaId;
}
