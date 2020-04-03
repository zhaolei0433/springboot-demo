package com.example.ehcachecache.entity.one;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaolei
 * Create: 2019/4/19 14:51
 * Modified By:
 * Description:
 */
@Data
@Entity
@Table(name = "user_details_info")
@DynamicUpdate()
@DynamicInsert()
public class UserDetailsInfo {
    // user_gender
    public static final int USER_DETAILS_GENDER_MAN = 0; // 男性
    public static final int USER_DETAILS_GENDER_UNMAN = 1; // 女性
    public static Map<Integer, String> USER_DETAILS_GENDER_TIP_MAP;

    static {
        USER_DETAILS_GENDER_TIP_MAP = new HashMap<Integer, String>();
        USER_DETAILS_GENDER_TIP_MAP.put(USER_DETAILS_GENDER_MAN, "男");
        USER_DETAILS_GENDER_TIP_MAP.put(USER_DETAILS_GENDER_UNMAN, "女");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 相当于identity主键生成策略
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) COMMENT '用户姓名'")
    private String name;

    @Column(name = "gender", nullable = false, columnDefinition = "int(10) COMMENT '用户性别'")
    private Integer gender;

    @Column(name = "age", nullable = false, columnDefinition = "int(10) COMMENT '用户年龄'")
    private Integer age;

    @Override
    public String toString() {
        return "UserDetailsInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + USER_DETAILS_GENDER_TIP_MAP.get(gender) +
                ", age=" + age +
                '}';
    }
}
