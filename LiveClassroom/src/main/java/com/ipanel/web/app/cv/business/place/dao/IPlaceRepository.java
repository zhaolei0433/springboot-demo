package com.ipanel.web.app.cv.business.place.dao;

import com.ipanel.web.app.cv.entity.Place;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaolei
 * Create: 2018/9/21 16:10
 * Modified By:
 * Description:
 */
@Repository
public interface IPlaceRepository extends CrudRepository<Place, Integer>, JpaSpecificationExecutor {
    Boolean existsByPlaceMasterOrPlaceName(String placeMaster, String placeName);
}
