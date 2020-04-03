package com.example.utilsdemo.test;

import com.example.utilsdemo.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaolei
 * Create: 2019/7/22 15:35
 * Modified By:
 * Description:
 */
public class text {

    public static void javaToxml(ADI2 stu) throws Exception {
        // 获取JAXB的上下文环境，需要传入具体的 Java bean -> 这里使用Student
        JAXBContext context = JAXBContext.newInstance(ADI2.class);
        // 创建 Marshaller 实例
        Marshaller marshaller = context.createMarshaller();
        // 设置转换参数 -> 这里举例是告诉序列化器是否格式化输出
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        // 构建输出环境 -> 这里使用标准输出，输出到控制台Console
        PrintStream out = System.out;
        // 将所需对象序列化 -> 该方法没有返回值
        marshaller.marshal(stu, out);
    }

    public static void main(String[] args) throws Exception {
        //Student stu = new Student("001","Tom",22);
        ADI2 adi2 = new ADI2();
        //GroupAsset
        GroupAsset opengroupAsset = new GroupAsset();
        GroupAsset_VODRelease groupAssetVodRelease = new GroupAsset_VODRelease();
        groupAssetVodRelease.setAssetID("shtest108115618XP");
        groupAssetVodRelease.setSerialNo("");
        AssetLifetime assetLifetime = new AssetLifetime();
        assetLifetime.setEndDateTime("2025-06-15");
        assetLifetime.setStartDateTime("2015-06-15");
        groupAssetVodRelease.setAssetLifetime(assetLifetime);
        opengroupAsset.setGroupAsset_VodRelease(groupAssetVodRelease);
        adi2.setOpenGroupAsset(opengroupAsset);
        //MetadataAsset
        List<MetadataAsset> metadataAssets = new ArrayList<>();

        MetadataAsset metadataAsset_1 = new MetadataAsset();
        metadataAsset_1.setGroupAssetID("shtest108115618XP");
        metadataAsset_1.setType(MetadataAsset.METADATA_ASSET_TYPE_TITLE);
        MetadataAsset_Title metadataAsset_title = new MetadataAsset_Title();
        metadataAsset_title.setAssetLifetime(assetLifetime);
        metadataAsset_title.setAssetID("shtest108115618XP");
        metadataAsset_title.setTitleFull("上海市政协开展“旧区改造推进情况及主要问题”专题视察");
        metadataAsset_title.setSummaryShort("新民晚报讯（通讯员刘叶记者江跃中）今天，市政协赴黄浦区开展“旧区改造推进情况及主要问题”专题视察。市政协副主席赵雯参加。记者从视察活动中获悉，在“十三五”后两年以及“十四五”初期，黄浦区将重点聚焦老城厢地区，全力以赴推进各项具体工作，使居民居住条件得到根本性的改善。到“十四五”期末，黄浦区将基本完成成片二级以下旧里改造任务。今年黄浦区的旧改目标是征收8000户、收尾5个项目。截至目前，全区完成...");
        metadataAsset_1.setMetadataAsset_title(metadataAsset_title);
        metadataAssets.add(metadataAsset_1);

        MetadataAsset metadataAsset_2 = new MetadataAsset();
        metadataAsset_2.setGroupAssetID("shtest108115618XP");
        metadataAsset_2.setType(MetadataAsset.METADATA_ASSET_TYPE_CATEGORY_PATH);
        MetadataAsset_CategoryPath metadataAsset_categoryPath = new MetadataAsset_CategoryPath();
        metadataAsset_2.setMetadataAsset_categoryPath(metadataAsset_categoryPath);
        metadataAssets.add(metadataAsset_2);

        MetadataAsset metadataAsset_3 = new MetadataAsset();
        metadataAsset_3.setGroupAssetID("shtest108115618XP");
        metadataAsset_3.setType(MetadataAsset.METADATA_ASSET_TYPE_COPYRIGHT);
        MetadataAsset_Copyright metadataAsset_copyright = new MetadataAsset_Copyright();
        metadataAsset_3.setMetadataAsset_copyright(metadataAsset_copyright);
        metadataAssets.add(metadataAsset_3);

        adi2.setMetadataAssets(metadataAssets);

        //ContentAsset
        ContentAsset contentAsset = new ContentAsset();
        contentAsset.setFileName("上海市政协开展“旧区改造推进情况及主要问题”专题视察");
        contentAsset.setFileSize("853908972");
        contentAsset.setMD5CheckSum("24e6f306fdb3b678cbd97682eab5307b");
        ContentAsset_Video contentAsset_video = new ContentAsset_Video();
        contentAsset_video.setAssetID("shtest108115618XV");
        contentAsset_video.setEncodingProfile("H264");
        contentAsset_video.setFileName("上海市政协开展“旧区改造推进情况及主要问题”专题视察");
        contentAsset_video.setFileSize("853908972");
        contentAsset_video.setGroupProviderID("SITV");
        contentAsset_video.setMD5CheckSum("24e6f306fdb3b678cbd97682eab5307b");
        contentAsset_video.setProviderID("SITV");
        contentAsset_video.setTransferContentURL("ftp://shtest:shtest@10.27.168.6/1231.ts");
        contentAsset_video.setDuration("80");
        contentAsset_video.setBitrate("8000");
        contentAsset_video.setNumberofframes("1");
        contentAsset_video.setFrameHeight("720");
        contentAsset_video.setFrameWidth("1080");
        contentAsset_video.setUsage("1");
        contentAsset.setContentAsset_video(contentAsset_video);

        adi2.setContentAsset(contentAsset);

        //AssociateContent
        AssociateContent associateContent = new AssociateContent();
        associateContent.setAssetID("shtest108115618XP");
        associateContent.setEffectiveDate("");
        associateContent.setGroupAssetID("shtest108115618XP");

        adi2.setAssociateContent(associateContent);


        javaToxml(adi2);
    }
}
