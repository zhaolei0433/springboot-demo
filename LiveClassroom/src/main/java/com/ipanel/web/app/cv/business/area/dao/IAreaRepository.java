package com.ipanel.web.app.cv.business.area.dao;

import com.ipanel.web.app.cv.entity.Area;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:32
 * Modified By:
 * Description:
 */
@Repository
public interface IAreaRepository extends CrudRepository<Area, Integer>, JpaSpecificationExecutor {

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM area WHERE parent_id= :parentId ", nativeQuery = true)
    List<Area> findByParentId(@Param("parentId") Integer parentId);


    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM area WHERE id BETWEEN :A and :B", nativeQuery = true)
    List<Area> test(@Param("A") Integer A, @Param("B") Integer B);

}
