package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author zhaolei
 * Create: 2019/7/29 17:38
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MetadataAsset_type {
    public MetadataAsset_type() {
    }

    /**
     * 资产ID
     */
    @XmlAttribute(name = "assetID")
    private String assetID;

    /**
     * 同groupAsset中的providerID
     */
    @XmlAttribute(name = "providerID")
    private String providerID = "SITV";

    /**
     * 默认值1
     */
    @XmlAttribute(name = "updateNum")
    private String updateNum = "1";

    /**
     * 原始内容提供商的ID（CPID）
     */
    @XmlAttribute(name = "originalPlatformID")
    private String originalPlatformID;

    /**
     * 原始内容id
     */
    @XmlAttribute(name = "Original_Asset_ID")
    private String Original_Asset_ID;


    @XmlElement(name = "adi:AssetLifetime", required = true)
    private AssetLifetime assetLifetime;
}
