package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.MessageInfo;

public interface MessageInfoMapper {
	/*添加用户消息信息*/
	public void addMessageInfo(MessageInfo messageInfo) throws Exception;

	/*按照查询条件分页查询用户消息记录*/
	public ArrayList<MessageInfo> queryMessageInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有用户消息记录*/
	public ArrayList<MessageInfo> queryMessageInfoList(@Param("where") String where) throws Exception;

	/*按照查询条件的用户消息记录数*/
	public int queryMessageInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条用户消息记录*/
	public MessageInfo getMessageInfo(int messageId) throws Exception;

	/*更新用户消息记录*/
	public void updateMessageInfo(MessageInfo messageInfo) throws Exception;

	/*删除用户消息记录*/
	public void deleteMessageInfo(int messageId) throws Exception;

}
