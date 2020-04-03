package com.example.utilsdemo.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author zhaolei
 * Create: 2019/7/29 19:20
 * Modified By:
 * Description:
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ContentAsset_Video {
    /**
     * 资产ID
     */
    @XmlAttribute(name = "assetID")
    private String assetID;

    /**
     * 编码参数,非必填
     */
    @XmlAttribute(name = "encodingProfile")
    private String encodingProfile;

    /**
     * 实体文件名称
     */
    @XmlAttribute(name = "fileName")
    private String fileName;

    /**
     * 实体文件大小
     */
    @XmlAttribute(name = "fileSize")
    private String fileSize;

    /**
     * 同groupAsset中的providerID
     */
    @XmlAttribute(name = "groupProviderID")
    private String groupProviderID;

    /**
     * 实体文件MD5值
     */
    @XmlAttribute(name = "mD5CheckSum")
    private String mD5CheckSum;

    /**
     * 同groupAsset中的providerID
     */
    @XmlAttribute(name = "providerID")
    private String providerID;

    /**
     * 实体文件的存放地址
     * 说明当删除文件时此地址可为空，或者填写删除文件前真实合法地址.
     * 例如：：ftp:// sihuatech:sihuatech @192.168.2.211:21/data/bian3.ts。
     * 不允许类似：ftp://1.1.1.1等随意书写地址
     */
    @XmlAttribute(name = "transferContentURL")
    private String transferContentURL;

    /**
     * 默认值1
     */
    @XmlAttribute(name = "updateNum")
    private String updateNum = "1";

    /**
     * 高清标识
     * 高清：0 ；标清：1；2流畅;3超高清
     */
    @XmlAttribute(name = "HDFlag")
    private String HDFlag = "1";

    @XmlElement(name = "adi:AssetLifetime", required = true)
    private AssetLifetime assetLifetime;

    /**
     * 时长
     */
    @XmlElement(name = "vod:Duration", required = true)
    private String duration;

    /**
     * 码率（单位bps）
     */
    @XmlElement(name = "vod:Bitrate", required = true)
    private String Bitrate;

    /**
     * 帧数
     */
    @XmlElement(name = "vod:Numberofframes", required = true)
    private String numberofframes;

    /**
     * 帧高
     */
    @XmlElement(name = "vod:FrameHeight", required = true)
    private String frameHeight;

    /**
     * 帧宽
     */
    @XmlElement(name = "vod:FrameWidth", required = true)
    private String FrameWidth;

    /**
     * 帧率
     */
    @XmlElement(name = "vod:FrameRate", required = true)
    private String FrameRate;

    /**
     * 文件类型
     * 1：视频,2：音频,3：图片, 程序=4,BREW=5,应用=6, 文本=7
     */
    @XmlElement(name = "vod:FrameRate", required = true)
    private String FileType = "1";

    /**
     * 文件类型
     * 正片=1,宣传片=3,片花=4,花絮=5,海报=6,主程序=8,宣传画=9,其他=7
     */
    @XmlElement(name = "vod:Usage", required = true)
    private String Usage = "1";

    /**
     * 1、wap视频;2、宽带视频;3、IPTV视频;4、IPONE视频;5、Android视频;6、WAP和Android视频;7、Android和宽带视频;8、视频炫铃9、IVVR视频;10、互联网电视视频;11、STB;12、OTT
     */
    @XmlElement(name = "vod:ServiceType", required = true)
    private String ServiceType = "3";

    /**
     *  媒体类型:
     * （枚举说明中，空格前面是对应的MimeType，空格后面是对应的文件后缀）
     */
    @XmlElement(name = "vod:MimeType", required = true)
    private String MimeType = "43－video/mp4";

}
