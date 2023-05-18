package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Cooking {
    /*记录id*/
    private Integer cookingId;
    public Integer getCookingId(){
        return cookingId;
    }
    public void setCookingId(Integer cookingId){
        this.cookingId = cookingId;
    }

    /*菜谱分类*/
    private CookClass cookClassObj;
    public CookClass getCookClassObj() {
        return cookClassObj;
    }
    public void setCookClassObj(CookClass cookClassObj) {
        this.cookClassObj = cookClassObj;
    }

    /*菜谱名称*/
    @NotEmpty(message="菜谱名称不能为空")
    private String cookingName;
    public String getCookingName() {
        return cookingName;
    }
    public void setCookingName(String cookingName) {
        this.cookingName = cookingName;
    }

    /*菜谱图片*/
    private String cookingPhoto;
    public String getCookingPhoto() {
        return cookingPhoto;
    }
    public void setCookingPhoto(String cookingPhoto) {
        this.cookingPhoto = cookingPhoto;
    }

    /*菜谱功效*/
    @NotEmpty(message="菜谱功效不能为空")
    private String gongxiao;
    public String getGongxiao() {
        return gongxiao;
    }
    public void setGongxiao(String gongxiao) {
        this.gongxiao = gongxiao;
    }

    /*菜谱介绍*/
    @NotEmpty(message="菜谱介绍不能为空")
    private String cookingDesc;
    public String getCookingDesc() {
        return cookingDesc;
    }
    public void setCookingDesc(String cookingDesc) {
        this.cookingDesc = cookingDesc;
    }

    /*菜谱用料*/
    @NotEmpty(message="菜谱用料不能为空")
    private String yongliao;
    public String getYongliao() {
        return yongliao;
    }
    public void setYongliao(String yongliao) {
        this.yongliao = yongliao;
    }

    /*做法视频*/
    private String videoFile;
    public String getVideoFile() {
        return videoFile;
    }
    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCooking=new JSONObject(); 
		jsonCooking.accumulate("cookingId", this.getCookingId());
		jsonCooking.accumulate("cookClassObj", this.getCookClassObj().getClassName());
		jsonCooking.accumulate("cookClassObjPri", this.getCookClassObj().getCookClassId());
		jsonCooking.accumulate("cookingName", this.getCookingName());
		jsonCooking.accumulate("cookingPhoto", this.getCookingPhoto());
		jsonCooking.accumulate("gongxiao", this.getGongxiao());
		jsonCooking.accumulate("cookingDesc", this.getCookingDesc());
		jsonCooking.accumulate("yongliao", this.getYongliao());
		jsonCooking.accumulate("videoFile", this.getVideoFile());
		jsonCooking.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonCooking;
    }}