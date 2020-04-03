package com.example.neo4jdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @author zhaolei
 * Create: 2020/1/22 11:13
 * Modified By:
 * Description: 用户节点信息
 */
@Data
@Builder(toBuilder = true)
@NodeEntity
public class UserInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String userName;
}
