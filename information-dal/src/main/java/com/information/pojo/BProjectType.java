package com.information.pojo;

import java.util.List;

public class BProjectType {
    private Integer id;

    private String typeName;

    private Integer parentId;

    private String img;

    private List<BProjectType> childList;

    public List<BProjectType> getChildList() {
        return childList;
    }

    public void setChildList(List<BProjectType> childList) {
        this.childList = childList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }
}