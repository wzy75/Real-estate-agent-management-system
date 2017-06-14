package com.example.wangzeyu.wzyzhongjie.bean;

/**
 * Created by wangzeyu on 2017/6/12.
 */

public class Home {
    private String ID;
    private String imageID;
    private String useID;
    public Home(String id, String ID, String imageID){
        this.useID = id;
        this.ID = ID;
        this.imageID = imageID;
    }
    public String getID(){
        return ID;
    }
    public String getUseIDID(){
        return useID;
    }
    public String getImageID(){
        return imageID;
    }
}
