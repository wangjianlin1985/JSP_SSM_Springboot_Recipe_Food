package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class CookComment {
    /*评论id*/
    private Integer commentId;
    public Integer getCommentId(){
        return commentId;
    }
    public void setCommentId(Integer commentId){
        this.commentId = commentId;
    }

    /*被评菜谱*/
    private Cooking cookingObj;
    public Cooking getCookingObj() {
        return cookingObj;
    }
    public void setCookingObj(Cooking cookingObj) {
        this.cookingObj = cookingObj;
    }

    /*评论内容*/
    @NotEmpty(message="评论内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*评论用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*评论时间*/
    private String commentTime;
    public String getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCookComment=new JSONObject(); 
		jsonCookComment.accumulate("commentId", this.getCommentId());
		jsonCookComment.accumulate("cookingObj", this.getCookingObj().getCookingName());
		jsonCookComment.accumulate("cookingObjPri", this.getCookingObj().getCookingId());
		jsonCookComment.accumulate("content", this.getContent());
		jsonCookComment.accumulate("userObj", this.getUserObj().getName());
		jsonCookComment.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonCookComment.accumulate("commentTime", this.getCommentTime());
		return jsonCookComment;
    }}