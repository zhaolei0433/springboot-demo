package com.example.neo4jdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * @author zhaolei
 * Create: 2020/1/22 14:25
 * Modified By:
 * Description:用户周边商店关系（用户节点和商店节点是拥有关系）
 */
@Data
@Builder
@RelationshipEntity(type = "HAS_SURROUND_STORE")
public class SurroundRelationship {
    @Id
    @GeneratedValue
    private Long id;

    private String distance;

    //关系的一端节点是 公司节点
    @StartNode
    private UserInfo userInfo;

    //关系的另一端节点是 供应商节点
    @EndNode
    private StoreInfo storeInfo;


}
