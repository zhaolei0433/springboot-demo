package com.ipanel.web.app.cv.business.place.controller;

import com.ipanel.web.app.cv.annotation.SysLog;
import com.ipanel.web.app.cv.business.area.controller.request.UpdateAreaReq;
import com.ipanel.web.app.cv.entity.Place;
import com.ipanel.web.app.cv.global.response.Result;
import com.ipanel.web.app.cv.global.SystemDefines;
import com.ipanel.web.app.cv.business.place.controller.request.AddPlaceReq;
import com.ipanel.web.app.cv.business.place.service.IPlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/21 16:08
 * Modified By:
 * Description:
 */
@Api(tags = SystemDefines.PLACE_PC_API)
@RestController
@RequestMapping(value = "place")
@CrossOrigin
public class PlaceController {

    private static Logger logger = LoggerFactory.getLogger(PlaceController.class);

    private IPlaceService placeService;

    @Autowired
    public PlaceController(IPlaceService placeService) {
        this.placeService = placeService;
    }

    @SysLog(operationType = SystemDefines.SYSLOG_ADD, operationName = "添加会场")
    @ApiOperation(value = "添加会场")
    @ApiImplicitParam(name = "req", value = "会场参数", dataType = "AddPlaceReq", required = true, paramType = "body")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<Boolean> addPlace(@RequestBody AddPlaceReq req) throws Exception {
        logger.info("req == " + req);
        return new Result<>(placeService.addPlace(req));
    }

    @SysLog(operationType = SystemDefines.SYSLOG_QUERY, operationName = "查询区域会场（当前区域以及所有子区域）")
    @ApiOperation(value = "查询区域会场（当前区域以及所有子区域）")
    @ApiImplicitParam(name = "areaId", value = "区域/单位id", dataType = "int", required = true, paramType = "path")
    @RequestMapping(value = "{areaId}", method = RequestMethod.GET)
    public Result<List<Place>> queryPlace(@PathVariable("areaId") Integer areaId) throws Exception {
        return new Result<>(placeService.queryPlace(areaId));
    }

    @SysLog(operationType = SystemDefines.SYSLOG_DELETE, operationName = "删除会场")
    @ApiOperation(value = "删除会场")
    @ApiImplicitParam(name = "placeId", value = "区域/社区id", dataType = "int", required = true, paramType = "path")
    @RequestMapping(value = "{placeId}", method = RequestMethod.DELETE)
    public Result<Boolean> deletePlace(@PathVariable("placeId") Integer placeId) throws Exception {
        return null;
    }

    @SysLog(operationType = SystemDefines.SYSLOG_UPDATE, operationName = "修改会场")
    @ApiOperation(value = "修改会场")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "req", value = "区域/社区信息", dataType = "UpdateAreaReq", required = true, paramType = "body"),
            @ApiImplicitParam(name = "placeId", value = "会场id", dataType = "int", required = true, paramType = "path")
    })
    @RequestMapping(value = "{placeId}", method = RequestMethod.PUT)
    public Result<Boolean> updatePlace(@PathVariable("placeId") Integer placeId, @RequestBody UpdateAreaReq req) throws Exception {
        return null;
    }
}
