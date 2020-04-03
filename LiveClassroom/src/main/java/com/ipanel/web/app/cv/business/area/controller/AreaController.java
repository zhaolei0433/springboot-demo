package com.ipanel.web.app.cv.business.area.controller;

import com.ipanel.web.app.cv.annotation.SysLog;
import com.ipanel.web.app.cv.business.area.controller.request.AddAreaReq;
import com.ipanel.web.app.cv.business.area.controller.request.AddReq;
import com.ipanel.web.app.cv.business.area.controller.request.UpdateAreaReq;
import com.ipanel.web.app.cv.business.area.controller.response.AreaTreeEL;
import com.ipanel.web.app.cv.business.area.controller.response.AreaTreeVO;
import com.ipanel.web.app.cv.business.area.service.IAreaService;
import com.ipanel.web.app.cv.entity.Area;
import com.ipanel.web.app.cv.global.SystemDefines;
import com.ipanel.web.app.cv.global.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:46
 * Modified By:
 * Description:
 */
@Api(tags = SystemDefines.AREA_PC_API)
@RestController
@RequestMapping(value = "area")
@CrossOrigin
public class AreaController {
    private static Logger logger = LoggerFactory.getLogger(AreaController.class);

    private IAreaService areaService;

    @Autowired
    public AreaController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @SysLog(operationType = SystemDefines.SYSLOG_ADD, operationName = "添加区域/单位")
    @ApiOperation(value = "添加区域/单位")
    @ApiImplicitParam(name = "req", value = "区域参数", dataType = "AddAreaReq", required = true, paramType = "body")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<Area> addArea(@Valid @RequestBody AddAreaReq req) throws Exception {
        return new Result<>(areaService.addArea(req));
    }


    @SysLog(operationType = SystemDefines.SYSLOG_QUERY, operationName = "查询区域树（当前区域以及子区域）")
    @ApiOperation(value = "查询区域树（当前区域以及子区域）")
    @ApiImplicitParam(name = "areaId", value = "区域/单位id", dataType = "int", required = true, paramType = "path")
    @RequestMapping(value = "tree/{areaId}", method = RequestMethod.GET)
    public Result<AreaTreeVO> queryAreaTreeGrid(@PathVariable("areaId") Integer areaId) throws Exception {
        if (org.springframework.util.StringUtils.isEmpty(areaId)) {
            areaId = 1;
        }
        return new Result<>(areaService.queryAreaTreeGrid(areaId));
    }

    @SysLog(operationType = SystemDefines.SYSLOG_QUERY, operationName = "查询区域树（所有子区域）")
    @ApiOperation(value = "查询区域树（所有子区域）")
    @RequestMapping(value = "tree", method = RequestMethod.GET)
    public Result<AreaTreeEL> queryAreaTreeGrid() throws Exception {
        return new Result<>(areaService.queryAreaAllTreeGrid());
    }

    @SysLog(operationType = SystemDefines.SYSLOG_QUERY, operationName = "修改区域/单位")
    @ApiOperation(value = "修改区域/单位")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "req", value = "区域/社区信息", dataType = "UpdateAreaReq", required = true, paramType = "body"),
            @ApiImplicitParam(name = "areaId", value = "区域/社区id", dataType = "int", required = true, paramType = "path")
    })
    @RequestMapping(value = "{areaId}", method = RequestMethod.PUT)
    public Result<Boolean> updateArea(@PathVariable("areaId") Integer areaId, @RequestBody UpdateAreaReq req) throws Exception {
        return new Result<>(areaService.updateArea(areaId, req));
    }

    @SysLog(operationType = SystemDefines.SYSLOG_DELETE, operationName = "删除区域")
    @ApiOperation(value = "删除区域")
    @ApiImplicitParam(name = "areaId", value = "区域/社区id", dataType = "int", required = true, paramType = "path")
    @RequestMapping(value = "{areaId}", method = RequestMethod.DELETE)
    public Result<Boolean> deleteArea(@PathVariable("areaId") Integer areaId) throws Exception {
        return new Result<>(areaService.deleteArea(areaId));
    }

    @ApiOperation(value = "测试")
    @RequestMapping(value = "{areaId}", method = RequestMethod.POST)
    public Result<Boolean> test(@PathVariable("areaId") Integer areaId, @RequestBody List<AddReq> req) throws Exception {
        logger.info(req.toString());
        logger.info("areaId={}", areaId);
        return new Result<>(true);
    }

    @ApiOperation(value = "测试path")
    @ApiImplicitParam(name = "id", value = "FTP服务器id", dataType = "int", required = true, paramType = "path")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result<Boolean> testPath(@PathVariable("id") Integer id) throws Exception {
        logger.info("id ={}", id);
        return new Result<>(true);
    }

    @ApiOperation(value = "测试query")
    @ApiImplicitParam(name = "id", value = "FTP服务器id", dataType = "int", required = true, paramType = "query")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<Boolean> testQuery(@RequestParam("id")Integer id) throws Exception{
        logger.info("id={}", id);
        return new Result<>(true);
    }
}
