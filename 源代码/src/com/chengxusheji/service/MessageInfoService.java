package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.MessageInfo;

import com.chengxusheji.mapper.MessageInfoMapper;
@Service
public class MessageInfoService {

	@Resource MessageInfoMapper messageInfoMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加用户消息记录*/
    public void addMessageInfo(MessageInfo messageInfo) throws Exception {
    	messageInfoMapper.addMessageInfo(messageInfo);
    }

    /*按照查询条件分页查询用户消息记录*/
    public ArrayList<MessageInfo> queryMessageInfo(String content,UserInfo sender,UserInfo reciever,String sendTime,String readState,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!content.equals("")) where = where + " and t_messageInfo.content like '%" + content + "%'";
    	if(null != sender &&  sender.getUser_name() != null  && !sender.getUser_name().equals(""))  where += " and t_messageInfo.sender='" + sender.getUser_name() + "'";
    	if(null != reciever &&  reciever.getUser_name() != null  && !reciever.getUser_name().equals(""))  where += " and t_messageInfo.reciever='" + reciever.getUser_name() + "'";
    	if(!sendTime.equals("")) where = where + " and t_messageInfo.sendTime like '%" + sendTime + "%'";
    	if(!readState.equals("")) where = where + " and t_messageInfo.readState like '%" + readState + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return messageInfoMapper.queryMessageInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<MessageInfo> queryMessageInfo(String content,UserInfo sender,UserInfo reciever,String sendTime,String readState) throws Exception  { 
     	String where = "where 1=1";
    	if(!content.equals("")) where = where + " and t_messageInfo.content like '%" + content + "%'";
    	if(null != sender &&  sender.getUser_name() != null && !sender.getUser_name().equals(""))  where += " and t_messageInfo.sender='" + sender.getUser_name() + "'";
    	if(null != reciever &&  reciever.getUser_name() != null && !reciever.getUser_name().equals(""))  where += " and t_messageInfo.reciever='" + reciever.getUser_name() + "'";
    	if(!sendTime.equals("")) where = where + " and t_messageInfo.sendTime like '%" + sendTime + "%'";
    	if(!readState.equals("")) where = where + " and t_messageInfo.readState like '%" + readState + "%'";
    	return messageInfoMapper.queryMessageInfoList(where);
    }

    /*查询所有用户消息记录*/
    public ArrayList<MessageInfo> queryAllMessageInfo()  throws Exception {
        return messageInfoMapper.queryMessageInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String content,UserInfo sender,UserInfo reciever,String sendTime,String readState) throws Exception {
     	String where = "where 1=1";
    	if(!content.equals("")) where = where + " and t_messageInfo.content like '%" + content + "%'";
    	if(null != sender &&  sender.getUser_name() != null && !sender.getUser_name().equals(""))  where += " and t_messageInfo.sender='" + sender.getUser_name() + "'";
    	if(null != reciever &&  reciever.getUser_name() != null && !reciever.getUser_name().equals(""))  where += " and t_messageInfo.reciever='" + reciever.getUser_name() + "'";
    	if(!sendTime.equals("")) where = where + " and t_messageInfo.sendTime like '%" + sendTime + "%'";
    	if(!readState.equals("")) where = where + " and t_messageInfo.readState like '%" + readState + "%'";
        recordNumber = messageInfoMapper.queryMessageInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取用户消息记录*/
    public MessageInfo getMessageInfo(int messageId) throws Exception  {
        MessageInfo messageInfo = messageInfoMapper.getMessageInfo(messageId);
        return messageInfo;
    }

    /*更新用户消息记录*/
    public void updateMessageInfo(MessageInfo messageInfo) throws Exception {
        messageInfoMapper.updateMessageInfo(messageInfo);
    }

    /*删除一条用户消息记录*/
    public void deleteMessageInfo (int messageId) throws Exception {
        messageInfoMapper.deleteMessageInfo(messageId);
    }

    /*删除多条用户消息信息*/
    public int deleteMessageInfos (String messageIds) throws Exception {
    	String _messageIds[] = messageIds.split(",");
    	for(String _messageId: _messageIds) {
    		messageInfoMapper.deleteMessageInfo(Integer.parseInt(_messageId));
    	}
    	return _messageIds.length;
    }
}
