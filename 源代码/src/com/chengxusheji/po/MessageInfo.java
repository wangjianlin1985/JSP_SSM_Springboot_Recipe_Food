package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageInfo {
    /*消息id*/
    private Integer messageId;
    public Integer getMessageId(){
        return messageId;
    }
    public void setMessageId(Integer messageId){
        this.messageId = messageId;
    }

    /*消息内容*/
    @NotEmpty(message="消息内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*发送人*/
    private UserInfo sender;
    public UserInfo getSender() {
        return sender;
    }
    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    /*接收人*/
    private UserInfo reciever;
    public UserInfo getReciever() {
        return reciever;
    }
    public void setReciever(UserInfo reciever) {
        this.reciever = reciever;
    }

    /*发送时间*/
    @NotEmpty(message="发送时间不能为空")
    private String sendTime;
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /*阅读状态*/
    @NotEmpty(message="阅读状态不能为空")
    private String readState;
    public String getReadState() {
        return readState;
    }
    public void setReadState(String readState) {
        this.readState = readState;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonMessageInfo=new JSONObject(); 
		jsonMessageInfo.accumulate("messageId", this.getMessageId());
		jsonMessageInfo.accumulate("content", this.getContent());
		jsonMessageInfo.accumulate("sender", this.getSender().getName());
		jsonMessageInfo.accumulate("senderPri", this.getSender().getUser_name());
		jsonMessageInfo.accumulate("reciever", this.getReciever().getName());
		jsonMessageInfo.accumulate("recieverPri", this.getReciever().getUser_name());
		jsonMessageInfo.accumulate("sendTime", this.getSendTime().length()>19?this.getSendTime().substring(0,19):this.getSendTime());
		jsonMessageInfo.accumulate("readState", this.getReadState());
		return jsonMessageInfo;
    }}