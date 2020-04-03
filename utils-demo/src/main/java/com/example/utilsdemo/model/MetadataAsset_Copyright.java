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
public class MetadataAsset_Copyright extends MetadataAsset_type {

    public MetadataAsset_Copyright() {
    }

    /**
     * 版权类型 0-无  1-有
     */
    @XmlElement(name = "vod:LicenseType", required = true)
    private String licenseType = "1";

    /**
     * 版权类型 0-无  1-有
     */
    @XmlElement(name = "vod:OriginalLicenseCompany", required = true)
    private String originalLicenseCompany = "东方网";

}
