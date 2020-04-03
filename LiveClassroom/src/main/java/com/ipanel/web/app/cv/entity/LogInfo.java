package com.ipanel.web.app.cv.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhaolei
 * createTime 2017年9月9日 下午5:47:56
 */
@Data
@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class LogInfo implements Serializable {
    private static final long serialVersionUID = 2983387968231608352L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String operationType;
    @Column
    private String operationName;
    @Column
    private String createBy;
    @Column
    private Long createDate;
    @Column
    private String method;
    @Column
    private Boolean result;

    public LogInfo(String operationType, String operationName, String createBy, Long createDate,
                   String method, Boolean result) {
        super();
        this.operationType = operationType;
        this.operationName = operationName;
        this.createBy = createBy;
        this.createDate = createDate;
        this.method = method;
        this.result = result;
    }

    public LogInfo() {
    }
}
