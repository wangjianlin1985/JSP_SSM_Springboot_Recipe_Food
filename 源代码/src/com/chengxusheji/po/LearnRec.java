package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class LearnRec {
    /*记录id*/
    private Integer recId;
    public Integer getRecId(){
        return recId;
    }
    public void setRecId(Integer recId){
        this.recId = recId;
    }

    /*学会菜谱*/
    private Cooking cookingObj;
    public Cooking getCookingObj() {
        return cookingObj;
    }
    public void setCookingObj(Cooking cookingObj) {
        this.cookingObj = cookingObj;
    }

    /*学习用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*获得积分*/
    @NotNull(message="必须输入获得积分")
    private Float jifen;
    public Float getJifen() {
        return jifen;
    }
    public void setJifen(Float jifen) {
        this.jifen = jifen;
    }

    /*学会时间*/
    @NotEmpty(message="学会时间不能为空")
    private String learnTime;
    public String getLearnTime() {
        return learnTime;
    }
    public void setLearnTime(String learnTime) {
        this.learnTime = learnTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonLearnRec=new JSONObject(); 
		jsonLearnRec.accumulate("recId", this.getRecId());
		jsonLearnRec.accumulate("cookingObj", this.getCookingObj().getCookingName());
		jsonLearnRec.accumulate("cookingObjPri", this.getCookingObj().getCookingId());
		jsonLearnRec.accumulate("userObj", this.getUserObj().getName());
		jsonLearnRec.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonLearnRec.accumulate("jifen", this.getJifen());
		jsonLearnRec.accumulate("learnTime", this.getLearnTime().length()>19?this.getLearnTime().substring(0,19):this.getLearnTime());
		return jsonLearnRec;
    }}