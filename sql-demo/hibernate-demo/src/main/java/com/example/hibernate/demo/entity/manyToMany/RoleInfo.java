package com.example.hibernate.demo.entity.manyToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role_info")
@DynamicUpdate
@DynamicInsert
public class RoleInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "remark")
	private String remark;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "sys_user_role_rel",
			joinColumns = {@JoinColumn(name = "role_id")},
			inverseJoinColumns = {@JoinColumn(name = "sys_user_id")}
	)
	private Set<SysUserInfo> sysUserInfos;

	public RoleInfo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<SysUserInfo> getSysUserInfos() {
		return sysUserInfos;
	}

	public void setSysUserInfos(Set<SysUserInfo> sysUserInfos) {
		this.sysUserInfos = sysUserInfos;
	}

	@Override
	public String toString() {
		return "RoleInfo{" +
				"id=" + id +
				", name='" + name + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}
