package site.recomi.studentmanagement.other;
/*
* 社团活动中添加按钮中recyclerView列表的实体类
* */
public class CampusAssociationItem {
    private String imgSrc;             //头像地址
    private String name;            //社团名
    private String subTitle;        //子标题
    private Boolean isSelect = false;      //是否选中,默认为不选中


    public CampusAssociationItem(String imgSrc , String name ,  String subTitle ,Boolean isSelect){
        this.imgSrc = imgSrc;
        this.name = name;
        this.subTitle = subTitle;
        this.isSelect = isSelect;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public CampusAssociationItem(String imgSrc , String name , String subTitle){
        this.imgSrc = imgSrc;
        this.name = name;
        this.subTitle = subTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }
}
