package com.ipanel.web.app.cv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2018/9/21 14:34
 * Modified By:
 * Description:
 */
@Entity
@Table(name = "place")
@DynamicUpdate()
@DynamicInsert()
public class Place implements Serializable {
    //placeType
    public static final Integer TYPE_ROOM = 0;//会议室
    public static final Integer TYPE_USR = 1;//用户
    //userType
    public static final Integer USER_STAFF = 0;//公司职员
    public static final Integer USER_ADMIN = 1;//公司管理员

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "place_master")
    private String placeMaster;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "place_type")
    private Integer placeType;

    @Column(name = "email")
    private String email;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Area company;

    public Place() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceMaster() {
        return placeMaster;
    }

    public void setPlaceMaster(String placeMaster) {
        this.placeMaster = placeMaster;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Area getCompany() {
        return company;
    }

    public void setCompany(Area company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", placeName='" + placeName + '\'' +
                ", placeMaster='" + placeMaster + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", placeType=" + placeType +
                ", email='" + email + '\'' +
                '}';
    }
}
