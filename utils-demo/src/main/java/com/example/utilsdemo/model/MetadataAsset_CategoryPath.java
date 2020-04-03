package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author zhaolei
 * Create: 2019/7/23 09:58
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MetadataAsset_CategoryPath extends MetadataAsset_type {

    public MetadataAsset_CategoryPath() {
    }

    /**
     * 一级分类(多个一级分类以”|”分隔)
     */
    @XmlElement(name = "vod:Classification", required = true)
    private String classification = "视频新闻";

    /**
     * 内容分类(按二级分类所属一级分类顺序对应分组，多个分组之间以“|”分隔，同属于一个一级分类的二级分类之间以英文逗号分隔)
     */
    @XmlElement(name = "vod:Category", required = true)
    private String category = "新闻";
}
