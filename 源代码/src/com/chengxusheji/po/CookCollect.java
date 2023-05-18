package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class CookCollect {
    /*收藏id*/
    private Integer collectId;
    public Integer getCollectId(){
        return collectId;
    }
    public void setCollectId(Integer collectId){
        this.collectId = collectId;
    }

    /*收藏菜谱*/
    private Cooking cookingObj;
    public Cooking getCookingObj() {
        return cookingObj;
    }
    public void setCookingObj(Cooking cookingObj) {
        this.cookingObj = cookingObj;
    }

    /*收藏用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*收藏时间*/
    private String collectionTime;
    public String getCollectionTime() {
        return collectionTime;
    }
    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCookCollect=new JSONObject(); 
		jsonCookCollect.accumulate("collectId", this.getCollectId());
		jsonCookCollect.accumulate("cookingObj", this.getCookingObj().getCookingName());
		jsonCookCollect.accumulate("cookingObjPri", this.getCookingObj().getCookingId());
		jsonCookCollect.accumulate("userObj", this.getUserObj().getName());
		jsonCookCollect.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonCookCollect.accumulate("collectionTime", this.getCollectionTime());
		return jsonCookCollect;
    }}