package com.ipanel.web.app.cv.business.area.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/19 16:09
 * Modified By:
 * Description:
 */
@Data
public class AddReq {
    @ApiModelProperty(value = "id", required = true)
    private Integer id;
    @ApiModelProperty(value = "姓名", required = true)
    private List<String> names;
}
