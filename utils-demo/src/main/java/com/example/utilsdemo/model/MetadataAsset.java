package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author zhaolei
 * Create: 2019/7/23 09:42
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MetadataAsset implements Serializable {

    public static final String METADATA_ASSET_TYPE_TITLE = "Title";
    public static final String METADATA_ASSET_TYPE_CATEGORY_PATH = "CategoryPath";
    public static final String METADATA_ASSET_TYPE_COPYRIGHT = "Copyright";

    public MetadataAsset() {
    }

    @XmlAttribute(name = "groupAssetID")
    private String groupAssetID;

    @XmlAttribute(name = "groupProviderID")
    private String groupProviderID = "SITV";

    @XmlAttribute(name = "product")
    private String product = "VOD";

    @XmlAttribute(name = "type")
    private String type;

    @XmlElement(name = "vod:Title", required = true)
    private MetadataAsset_Title metadataAsset_title;

    @XmlElement(name = "vod:CategoryPath", required = true)
    private MetadataAsset_CategoryPath metadataAsset_categoryPath;

    @XmlElement(name = "vod:Copyright", required = true)
    private MetadataAsset_Copyright metadataAsset_copyright;
}
