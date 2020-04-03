package com.ipanel.web.app.cv;

import com.ipanel.web.app.cv.business.area.dao.IAreaRepository;
import com.ipanel.web.app.cv.business.place.dao.IPlaceRepository;
import com.ipanel.web.app.cv.business.place.service.IPlaceService;
import com.ipanel.web.app.cv.entity.Area;
import com.ipanel.web.app.cv.entity.Place;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveClassroomApplicationTests {

    @Autowired
    IAreaRepository areaRepository;

    @Autowired
    IPlaceRepository placeRepository;

    @Autowired
    IPlaceService placeService;

    @Test
    public void contextLoads() throws Exception {
        /*Area area = areaRepository.findById(1).get();
        System.out.println(area);
        List<Area> childrenArea = area.getChildrenAreas().stream().sorted(Comparator.comparing(Area::getId)).collect(Collectors.toList());
        System.out.println(childrenArea);*/


        Area area = areaRepository.findById(1).get();
        List<Area> childrenArea = area.getChildrenAreas().stream().sorted(Comparator.comparing(Area::getId)).collect(Collectors.toList());
        System.out.println(childrenArea);
    }

}
