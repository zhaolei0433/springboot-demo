package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author zhaolei
 * Create: 2019/7/30 11:01
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AssociateContent {
    /**
     * 对应实体内容ContentAsset的assetID
     */
    @XmlAttribute(name = "assetID")
    private String assetID;

    /**
     * 生效日期
     */
    @XmlAttribute(name = "effectiveDate")
    private String effectiveDate;

    /**
     * 对应groupAsset中的assetID
     */
    @XmlAttribute(name = "groupAssetID")
    private String groupAssetID;

    /**
     * 同groupAsset中的providerID
     */
    @XmlAttribute(name = "groupProviderID")
    private String groupProviderID = "SITV";

    /**
     * CPID，同groupAsset中的providerID
     */
    @XmlAttribute(name = "providerID")
    private String providerID = "SITV";

    /**
     * ContentAsset的类型
     */
    @XmlAttribute(name = "type")
    private String type = "Video";
}
