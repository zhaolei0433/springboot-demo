package com.example.hibernate.demo.dao.oneToMany;

import com.example.hibernate.demo.entity.oneToMany.AuthorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhaolei
 * Create: 2019/2/28 18:32
 * Modified By:
 * Description:
 */


public interface IAuthorRepository extends JpaRepository<AuthorInfo, Integer>, JpaSpecificationExecutor {
}
