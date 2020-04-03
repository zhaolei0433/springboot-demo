package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhaolei
 * Create: 2019/7/22 14:13
 * Modified By:
 * Description:
 */
@Data
@XmlRootElement(name = "adi:ADI2")
@XmlType(propOrder = {"openGroupAsset", "metadataAssets", "contentAsset", "associateContent"})
public class ADI2 implements Serializable {

    public ADI2() {
    }

    private String adi = "http://www.cablelabs.com/VODSchema/adi";
    private String def = "http://www.cablelabs.com/VODSchema/default";
    private String vod = "http://www.cablelabs.com/VODSchema/vod";
    /**
     * 下面三个，三选一
     */
    private GroupAsset openGroupAsset;
    private List<MetadataAsset> metadataAssets;
    private ContentAsset contentAsset;
    private AssociateContent associateContent;

    @XmlAttribute(name = "xmlns:adi")
    public void setAdi(String adi) {
        this.adi = adi;
    }

    @XmlAttribute(name = "xmlns")
    public void setDef(String def) {
        this.def = def;
    }

    @XmlAttribute(name = "xmlns:vod")
    public void setVod(String vod) {
        this.vod = vod;
    }

    @XmlElement(name = "adi:OpenGroupAsset", required = true)
    public void setOpenGroupAsset(GroupAsset openGroupAsset) {
        this.openGroupAsset = openGroupAsset;
    }

    @XmlElement(name = "adi:AddMetadataAsset", required = true)
    public void setMetadataAssets(List<MetadataAsset> metadataAssets) {
        this.metadataAssets = metadataAssets;
    }

    @XmlElement(name = "adi:AcceptContentAsset", required = true)
    public void setContentAsset(ContentAsset contentAsset) {
        this.contentAsset = contentAsset;
    }

    @XmlElement(name = "adi:AssociateContent", required = true)
    public void setAssociateContent(AssociateContent associateContent) {
        this.associateContent = associateContent;
    }
}