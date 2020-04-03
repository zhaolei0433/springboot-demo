package com.ipanel.web.app.cv.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by summer on 2017/5/5.
 */
@Entity
@Table(name = "user")
@DynamicUpdate()
@DynamicInsert()
public class User implements Serializable {
        private static final long serialVersionUID = -3258839839160856613L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "user_name")
        private String userName;

        @Column(name = "pass_word")
        private String passWord;

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

        public String getPassWord() {
                return passWord;
        }

        public void setPassWord(String passWord) {
                this.passWord = passWord;
        }

        @Override
        public String toString() {
                return "User{" +
                        "id=" + id +
                        ", userName='" + userName + '\'' +
                        ", passWord='" + passWord + '\'' +
                        '}';
        }
}
