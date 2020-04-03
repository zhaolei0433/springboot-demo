package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/7/22 14:42
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupAsset implements Serializable {

    public GroupAsset() {
    }

    /**
     * 默认值VOD,必填
     */
    @XmlAttribute(name = "product")
    private String product = "VOD";

    /**
     * 默认值VODRelease，必填
     */
    @XmlAttribute(name = "type")
    private String type = "VODRelease";

    @XmlElement(name = "vod:VODRelease", required = true)
    private GroupAsset_VODRelease groupAsset_VodRelease;
}
