package com.example.mqdemo2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2020/1/7 16:11
 * Modified By:
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
}
