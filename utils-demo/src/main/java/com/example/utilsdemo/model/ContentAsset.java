package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author zhaolei
 * Create: 2019/7/29 19:09
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ContentAsset {
    public ContentAsset() {
    }

    /**
     * 实体文件的名称
     */
    @XmlAttribute(name = "fileName")
    private String fileName;

    /**
     * 实体文件的的大小,单位byte
     */
    @XmlAttribute(name = "fileSize")
    private String fileSize;

    /**
     * 实体文件MD5值
     */
    @XmlAttribute(name = "mD5CheckSum")
    private String mD5CheckSum;

    /**
     * 默认值N
     */
    @XmlAttribute(name = "metadataOnly")
    private String metadataOnly = "N";

    /**
     * ContentAsset的类型
     */
    @XmlAttribute(name = "type")
    private String type = "Video";

    /**
     * 子内容原始子内容id
     */
    @XmlAttribute(name = "Original_Asset_ID")
    private String originalAssetID;


    @XmlElement(name = "vod:Video", required = true)
    private ContentAsset_Video contentAsset_video;

}
