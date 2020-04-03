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
public class MetadataAsset_Title extends MetadataAsset_type {

    public MetadataAsset_Title() {
    }

    /**
     * 标题,资产名称
     */
    @XmlElement(name = "vod:TitleFull", required = true)
    private String titleFull;

    /**
     * 元数据类型： News
     */
    @XmlElement(name = "vod:ShowType", required = true)
    private String showType = "News";

    /**
     * 看点
     */
    @XmlElement(name = "vod:SummaryShort", required = true)
    private String summaryShort;

    /**
     * 广告内容标识
     * 0：非广告内容; 1：广告内容
     */
    @XmlElement(name = "vod:IsAdvertise", required = true)
    private String isAdvertise = "0";

    /**
     * 状态标志
     * 0:失效  1:生效
     */
    @XmlElement(name = "vod:Status", required = true)
    private String status = "1";

}
