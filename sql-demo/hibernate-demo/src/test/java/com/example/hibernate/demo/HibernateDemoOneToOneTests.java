package com.example.hibernate.demo;

import com.example.hibernate.demo.dao.oneToOne.IUserDetailsRepository;
import com.example.hibernate.demo.dao.oneToOne.IUserRepository;
import com.example.hibernate.demo.entity.one.UserDetailsInfo;
import com.example.hibernate.demo.entity.one.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhaolei
 * Create: 2019/4/19 11:36
 * Modified By:
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateDemoOneToOneTests {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IUserDetailsRepository userDetailsRepository;

    /**
     * 添加时，UserInfo中关联了UserDetailsInfo，添加UserInfo时设置了UserInfo的UserDetailsInfo属性，
     * UserDetailsInfo也会被添加到相应的表中
     */
    @Test
    public void save() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("XiaoZho");
        userInfo.setPassword("XiaoZhao");
        userInfo.setPhone("12345678910");
        userInfo.setMailBox("12345678910@qq.com");
        UserDetailsInfo userDetailsInfo = new UserDetailsInfo();
        userDetailsInfo.setName("小赵");
        userDetailsInfo.setAge(19);
        userDetailsInfo.setGender(UserDetailsInfo.USER_DETAILS_GENDER_MAN);
        userInfo.setUserDetailsInfo(userDetailsInfo);
        System.out.println(userRepository.save(userInfo));
    }

    /**
     * 删除时当关联关系，UserInfo中关联了UserDetailsInfo，那么删除UserInfo时会自动删除UserDetailsInfo，
     * 但是删除UserDetailsInfo时不会会自动删除UserInfo
     */
    @Test
    public void delete() {
        userRepository.deleteById(2);
    }

    /**
     * 查询简单查询所有
     */
    @Test
    public void find1() {
        List<UserInfo> userInfos = userRepository.findAll();
        userInfos.forEach(System.out::println);
    }

    /**
     * 简单查询所有+字段排序
     */
    @Test
    public void find2() {
        /*//简单单标排序
        List<UserInfo> userInfos = userRepository.findAll(new Sort(Sort.Direction.DESC, "id")); //ASC：升序；DESC：降序*/
        //级联表排序
        /*List<UserInfo> userInfos = userRepository.findAll(new Sort(Sort.Direction.ASC, "userDetailsInfo.age"));
        userInfos.forEach(System.out::println);*/
    }

    /**
     * 分页简单查询
     */
    @Test
    public void find3() {
        PageRequest pageRequest = PageRequest.of(1, 2);
        Page<UserInfo> userInfos = userRepository.findAll(pageRequest);
        userInfos.forEach(System.out::println);
    }

    /**
     * 分页简单查询+字段排序(首先排序再分页）
     */
    @Test
    public void find4() {
        /*PageRequest pageRequest = PageRequest.of(1, 2, new Sort(Sort.Direction.DESC, "userDetailsInfo.age")); //原理同上，先排序后分页
        Page<UserInfo> userInfos = userRepository.findAll(pageRequest);
        userInfos.forEach(System.out::println);*/
    }

    /**
     * Repository简单模糊查询
     */
    @Test
    public void find5() {
        List<UserInfo> userInfos = userRepository.findUserNameIsLikeBySql("%X%"); //sql语句查询
        // List<UserInfo> userInfos = userRepository.findByUserNameIsLike("%X%"); //jap 简单模糊查询
        userInfos.forEach(System.out::println);
    }

    /**
     * Repository简单模糊分页查询
     */
    @Test
    public void find5_1() {
        /*PageRequest pageRequest = PageRequest.of(0, 2, new Sort(Sort.Direction.ASC, "userDetailsInfo.age"));
        Page<UserInfo> userInfos = userRepository.findByUserNameIsLike("X", pageRequest);
        userInfos.forEach(System.out::println);*/
    }

    /**
     * Specification的简单模糊查询
     */
    @Test
    public void find7() {
        Specification<UserInfo> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //predicates.add(cb.between(root.get("price"),10.00,19.00)); //价格条件区间
            predicates.add(cb.like(root.get("userName"), "%" + "X" + "%"));
            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        List<UserInfo> userInfos = userRepository.findAll(specification);
        userInfos.forEach(System.out::println);
    }

    /**
     * Specification的简单模糊查询
     */
    @Test
    public void find7_1() {
        /*Specification<UserInfo> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //predicates.add(cb.between(root.get("price"),10.00,19.00)); //价格条件区间
            predicates.add(cb.like(root.get("userName"), "%" + "X" + "%"));
            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        PageRequest pageRequest = PageRequest.of(0, 1, new Sort(Sort.Direction.DESC, "userDetailsInfo.age"));
        Page<UserInfo> userInfos = userRepository.findAll(specification, pageRequest);
        userInfos.forEach(System.out::println);*/
    }

    /**
     * Specification的多条件模糊查询
     */
    @Test
    public void find8() {
        /*Specification<UserInfo> specification = (root, query, cb) -> {
            List<String> searchFieldNames = new ArrayList<>();
            searchFieldNames.add("userName");

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.between(root.get("age"), 20, 21)); //价格条件区间
            predicates.add(cb.isNotNull(root.get("id")));

            List<Predicate> searchLs = new ArrayList<>();
            if (!StringUtils.isEmpty("天") && searchFieldNames.size() > 0) {
                for (String names : searchFieldNames) {
                    if (!StringUtils.isEmpty(names)) {
                        searchLs.add(cb.like(root.get(names), "%" + "天" + "%"));
                    }
                }
            }
            Predicate searchPredicate = searchLs.isEmpty() ? null : cb.or(searchLs.toArray(new Predicate[searchLs.size()]));
            if (null != searchPredicate) {
                predicates.add(searchPredicate);
            }

            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        PageRequest pageRequest = PageRequest.of(0, 1, new Sort(Sort.Direction.DESC, "userDetailsInfo.age"));
        Page<UserInfo> userInfos = userRepository.findAll(specification, pageRequest);
        userInfos.forEach(System.out::println);*/
    }
}
