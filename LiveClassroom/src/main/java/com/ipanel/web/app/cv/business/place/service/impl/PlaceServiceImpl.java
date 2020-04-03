package com.ipanel.web.app.cv.business.place.service.impl;

import com.ipanel.web.app.cv.business.area.dao.IAreaRepository;
import com.ipanel.web.app.cv.entity.Area;
import com.ipanel.web.app.cv.entity.Place;
import com.ipanel.web.app.cv.business.place.controller.request.AddPlaceReq;
import com.ipanel.web.app.cv.business.place.dao.IPlaceRepository;
import com.ipanel.web.app.cv.business.place.service.IPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhaolei
 * Create: 2018/9/21 16:14
 * Modified By:
 * Description:
 */
@Service
public class PlaceServiceImpl implements IPlaceService {
    private static Logger logger = LoggerFactory.getLogger(PlaceServiceImpl.class);

    private IPlaceRepository placeRepository;
    private IAreaRepository areaRepository;

    public PlaceServiceImpl(IPlaceRepository placeRepository, IAreaRepository areaRepository) {
        this.placeRepository = placeRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public Boolean addPlace(AddPlaceReq req) throws Exception {
        Optional<Area> optionalArea = areaRepository.findById(req.getAreaId());
        if (!optionalArea.isPresent()) {
            throw new Exception("区域不存在，无法添加会场");
        }
        if (optionalArea.get().getIsCompany() == 1) {
            throw new Exception("单位不可添加会场");
        }
        if (optionalArea.get().getParentArea() == null) {
            throw new Exception("区域不在单位下，不可添加会场");
        }
        if (optionalArea.get().getParentArea().getIsCompany() != 1) {
            throw new Exception("区域不在单位下，不可添加会场");
        }
        if (placeRepository.existsByPlaceMasterOrPlaceName(req.getPlaceMaster(), req.getPlaceName())) {
            throw new Exception("会场Mac或会场名已存在");
        }
        Place place = new Place();
        place.setPlaceName(req.getPlaceName());
        place.setPlaceMaster(req.getPlaceMaster());
        place.setPhoneNum(req.getPhoneNum());
        place.setPlaceType(req.getPlaceType());
        place.setArea(optionalArea.get());
        logger.info("optionalArea.get() == " + optionalArea.get().toString());
        place.setCompany(optionalArea.get().getParentArea());
        placeRepository.save(place);

        return true;
    }

    List<Area> areaList = new ArrayList<>();

    @Override
    public List<Place> queryPlace(Integer areaId) throws Exception {
        Optional<Area> optionalArea = areaRepository.findById(areaId);
        if (!optionalArea.isPresent()) {
            throw new Exception("区域不存在");
        }
        List<Place> places = new ArrayList<>();
        List<Area> areas = getChildrenArea(optionalArea.get());
        areas.forEach(area -> places.addAll(area.getPlaces()));
        areaList.clear();
        return places;
    }

    //递归获取底层区域
    List<Area> getChildrenArea(Area area) {
        if (area.getChildrenAreas().isEmpty()) {
            areaList.add(area);
            return areaList;
        }
        List<Area> childrenAreas = area.getChildrenAreas().stream().sorted(Comparator.comparing(Area::getId)).collect(Collectors.toList());
        for (Area childrenArea : childrenAreas) {
            if (childrenArea.getChildrenAreas().isEmpty()) {
                areaList.add(childrenArea);
            } else {
                getChildrenArea(childrenArea);
            }
        }
        return areaList;
    }
}
