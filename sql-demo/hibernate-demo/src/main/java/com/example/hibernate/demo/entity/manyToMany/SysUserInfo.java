package com.example.hibernate.demo.entity.manyToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author zhaolei
 * Create: 2019/11/19 11:39
 * Modified By:
 * Description:
 */
@Entity
@Table(name = "sys_user_info")
@DynamicUpdate
@DynamicInsert
public class SysUserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "user_type")
    private String userType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //ManyToMany不能使用懒加载
    @JoinTable(
            name = "sys_user_role_rel",
            joinColumns = {@JoinColumn(name = "sys_user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<RoleInfo> roleInfos;

    public SysUserInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Set<RoleInfo> getRoleInfos() {
        return roleInfos;
    }

    public void setRoleInfos(Set<RoleInfo> roleInfos) {
        this.roleInfos = roleInfos;
    }

    @Override
    public String toString() {
        return "SysUserInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
