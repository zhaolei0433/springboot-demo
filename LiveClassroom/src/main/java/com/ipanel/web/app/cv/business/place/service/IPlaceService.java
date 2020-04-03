package com.ipanel.web.app.cv.business.place.service;

import com.ipanel.web.app.cv.entity.Place;
import com.ipanel.web.app.cv.business.place.controller.request.AddPlaceReq;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/21 16:14
 * Modified By:
 * Description:
 */
public interface IPlaceService {
    /**
     * 添加会场
     *
     * @param req
     * @return
     */
    Boolean addPlace(AddPlaceReq req) throws Exception;

    /**
     * 查询区域会场
     *
     * @param areaId
     * @return
     */
    List<Place> queryPlace(Integer areaId) throws Exception;

}
