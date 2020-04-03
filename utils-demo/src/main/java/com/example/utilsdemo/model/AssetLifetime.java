package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/7/22 14:46
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AssetLifetime implements Serializable {

    public AssetLifetime() {
    }

    @XmlAttribute(name = "endDateTime")
    private String endDateTime;

    @XmlAttribute(name = "startDateTime")
    private String startDateTime;
}
