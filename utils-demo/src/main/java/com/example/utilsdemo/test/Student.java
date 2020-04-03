package com.example.utilsdemo.test;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zhaolei
 * Create: 2019/7/22 15:37
 * Modified By:
 * Description:
 */
@XmlRootElement(namespace = "http://www.cablelabs.com/VODSchema/adi")
@Data
public class Student {
    private String id;
    private String name;
    private Integer age;


    public Student() {
    }

    public Student(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}