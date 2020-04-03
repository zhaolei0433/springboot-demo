package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/7/22 14:43
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupAsset_VODRelease implements Serializable {

    public GroupAsset_VODRelease() {
    }

    /**
     * 内容ID，全局唯一（与本规范中的所有的groupAssetID取值相同），必填
     */
    @XmlAttribute(name = "assetID")
    private String assetID;

    /**
     * 默认值Y
     */
    @XmlAttribute(name = "groupAsset")
    private String groupAsset = "Y";

    /**
     * 内容提供商的ID（CPID），必填
     */
    @XmlAttribute(name = "providerID")
    private String providerID = "SITV";

    /**
     * 内容提供商的类型（CP类型: 1 －个人用户   2 －CP   3 －集团   4 – 联通自有系统），必填
     */
    @XmlAttribute(name = "providerType")
    private String providerType = "2";

    /**
     * 每次操作的序列号（必须使用在异步操作中标识同一次操作），必填
     */
    @XmlAttribute(name = "serialNo")
    private String serialNo;

    /**
     * 更新数
     */
    @XmlAttribute(name = "updateNum")
    private String updateNum = "1";

    @XmlElement(name = "adi:AssetLifetime", required = true)
    private AssetLifetime assetLifetime;
}
