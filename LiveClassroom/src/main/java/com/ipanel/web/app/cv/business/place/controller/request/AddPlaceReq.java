package com.ipanel.web.app.cv.business.place.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author zhaolei
 * Create: 2018/9/21 16:19
 * Modified By:
 * Description:
 */
@Data
public class AddPlaceReq {
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
