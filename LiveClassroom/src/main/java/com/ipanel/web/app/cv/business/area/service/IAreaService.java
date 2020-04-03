package com.ipanel.web.app.cv.business.area.service;

import com.ipanel.web.app.cv.business.area.controller.request.AddAreaReq;
import com.ipanel.web.app.cv.business.area.controller.request.UpdateAreaReq;
import com.ipanel.web.app.cv.business.area.controller.response.AreaTreeEL;
import com.ipanel.web.app.cv.business.area.controller.response.AreaTreeVO;
import com.ipanel.web.app.cv.entity.Area;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:49
 * Modified By:
 * Description:
 */
public interface IAreaService {
    /**
     * 添加区域
     *
     * @param req 添加字段
     * @return Boolean boolean
     * @throws Exception e
     */
    Area addArea(AddAreaReq req) throws Exception;

    /**
     * 根据区域查询区域树（当前区域以及所有子区域）
     *
     * @param areaId
     * @return
     * @throws Exception
     */
    AreaTreeVO queryAreaTreeGrid(Integer areaId) throws Exception;

    /**
     * 根据区域查询区域树
     *
     * @return
     * @throws Exception
     */
    AreaTreeEL queryAreaAllTreeGrid() throws Exception;

    /**
     * 更新区域
     *
     * @param areaId
     * @param req
     * @return
     */
    Boolean updateArea(Integer areaId, UpdateAreaReq req) throws Exception;

    /**
     * 删除区域
     *
     * @param areaId
     * @return
     * @throws Exception
     */
    Boolean deleteArea(Integer areaId) throws Exception;
}
