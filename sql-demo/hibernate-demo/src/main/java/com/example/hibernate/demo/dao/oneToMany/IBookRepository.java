package com.example.hibernate.demo.dao.oneToMany;

import com.example.hibernate.demo.entity.oneToMany.BookInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhaolei
 * Create: 2018/9/19 15:32
 * Modified By:
 * Description:
 */
@Repository
public interface IBookRepository extends JpaRepository<BookInfo, Integer>, JpaSpecificationExecutor {
}
