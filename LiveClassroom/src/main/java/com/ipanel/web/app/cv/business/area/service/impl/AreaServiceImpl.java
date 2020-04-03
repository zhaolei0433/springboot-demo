package com.ipanel.web.app.cv.business.area.service.impl;

import com.ipanel.web.app.cv.business.area.controller.request.AddAreaReq;
import com.ipanel.web.app.cv.business.area.controller.request.UpdateAreaReq;
import com.ipanel.web.app.cv.business.area.controller.response.AreaTreeEL;
import com.ipanel.web.app.cv.business.area.controller.response.AreaTreeVO;
import com.ipanel.web.app.cv.business.area.dao.IAreaRepository;
import com.ipanel.web.app.cv.business.area.service.IAreaService;
import com.ipanel.web.app.cv.entity.Area;
import com.ipanel.web.app.cv.entity.AreaTypeEnum;
import com.ipanel.web.app.cv.global.Globals;
import com.ipanel.web.app.cv.global.RequestParamErrorException;
import com.ipanel.web.app.cv.task.MyTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:51
 * Modified By:
 * Description:
 */
@Service
public class AreaServiceImpl implements IAreaService {
    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    private IAreaRepository areaRepository;
    private MyTask myTask;

    @Autowired
    public AreaServiceImpl(IAreaRepository areaRepository, MyTask myTask) {
        this.areaRepository = areaRepository;
        this.myTask = myTask;
    }

    /**
     * 加事务回滚 ALTER TABLE my_test ENGINE=INNODB;
     *
     * @param req 添加字段
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Area addArea(AddAreaReq req) throws Exception {
        Globals.checkParams(req);
        Optional<Area> optionalParentArea = areaRepository.findById(req.getParentId());
        if (req.getParentId().equals(0)) {
            if (areaRepository.findByParentId(req.getParentId()).size() > 0) {
                throw new RequestParamErrorException("不可重复添加根区域");
            }
        } else {
            if (!optionalParentArea.isPresent()) {
                throw new RequestParamErrorException("父区域id无效");
            }
            if (optionalParentArea.get().getIsCompany() == 1 && req.getIsCompany() == 1) {
                throw new RequestParamErrorException("单位下面不可重复添加单位");
            }
            if (optionalParentArea.get().getParentArea() != null && optionalParentArea.get().getParentArea().getIsCompany() == 1) {
                throw new RequestParamErrorException("父区域隶属于单位，不可添加" + AreaTypeEnum.getName(req.getIsCompany()));


            }
        }
        Area area = new Area();
        area.setIsCompany(req.getIsCompany());
        area.setAreaName(req.getAreaName());
        if (optionalParentArea.isPresent()) {
            //非根区域情况
            area.setAreaLevel(optionalParentArea.get().getAreaLevel() + 1); //父区域的级数+1
            area.setParentArea(optionalParentArea.get());
            return areaRepository.save(area);
        } else {
            //根区域情况
            area.setAreaLevel(1);
            Area parentArea = new Area();
            parentArea.setId(0);
            area.setParentArea(parentArea);
            return areaRepository.save(area);
        }
    }

    @Override
    public AreaTreeVO queryAreaTreeGrid(Integer areaId) throws Exception {
      /*  myTask.sayHello("测试1");
        myTask.sayHello("测试2");*/
        Area area = areaRepository.findById(areaId).get();
        List<Area> childrenArea = area.getChildrenAreas().stream().sorted(Comparator.comparing(Area::getId)).collect(Collectors.toList());
        System.out.println(childrenArea);
        return new AreaTreeVO(area, childrenArea);
    }

    @Override
    public AreaTreeEL queryAreaAllTreeGrid() throws Exception {
        Area area = null;
        if (areaRepository.findById(1).isPresent())
            area = areaRepository.findById(1).get();
        assert area != null;
        return new AreaTreeEL(area.getId(), area.getAreaName(), getChildArea(area.getChildrenAreas().stream().sorted(Comparator.comparing(Area::getId)).collect(Collectors.toList())));
    }

    //递归逐层获取区域至底层区域
    List<AreaTreeEL> getChildArea(List<Area> childAreas) {
        List<AreaTreeEL> childAreaTreeELs = new ArrayList<>();
        for (Area childArea : childAreas) {
            AreaTreeEL areaTreeEL = new AreaTreeEL();
            areaTreeEL.setId(childArea.getId());
            areaTreeEL.setLabel(childArea.getAreaName());
            if (!childArea.getChildrenAreas().isEmpty()) {
                areaTreeEL.setChildren(getChildArea(childArea.getChildrenAreas().stream().sorted(Comparator.comparing(Area::getId)).collect(Collectors.toList())));
            }
            childAreaTreeELs.add(areaTreeEL);
        }
        return childAreaTreeELs;
    }

    @Override
    public Boolean updateArea(Integer areaId, UpdateAreaReq req) throws Exception {
        Area area = areaRepository.findById(areaId).get();
        area.setAreaName(req.getAreaName());
        areaRepository.save(area);
        return true;
    }

    @Override
    public Boolean deleteArea(Integer areaId) throws Exception {
        Optional<Area> optionalArea = areaRepository.findById(areaId);
        if (!optionalArea.isPresent()) {
            throw new Exception("不存在该区域的id号");
        }
        if (optionalArea.get().getChildrenAreas().size() > 0) {
            throw new Exception("该区域下存在子区域，不可删除");
        }
        if (optionalArea.get().getPlaces().size() > 0) {
            throw new Exception("该区域下存在会场，不可删除");
        }
        areaRepository.delete(optionalArea.get());
        return true;
    }
}
