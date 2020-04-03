package com.ipanel.web.app.cv.entity;

/**
 * @author zhaolei
 */
public enum AreaTypeEnum {
    TYPE_AREA("区域", 0),
    TYPE_COMMUNITY("单位", 1);

    private String name;
    private Integer index;


    AreaTypeEnum(final String name, final Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(final Integer index) {
        String name = "";
        for (AreaTypeEnum c : AreaTypeEnum.values()) {
            if (c.getIndex().equals(index)) {
                name = c.name;
                break;
            }
        }
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }
}
