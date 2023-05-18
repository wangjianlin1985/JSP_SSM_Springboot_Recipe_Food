package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.CookComment;

public interface CookCommentMapper {
	/*添加评论信息*/
	public void addCookComment(CookComment cookComment) throws Exception;

	/*按照查询条件分页查询评论记录*/
	public ArrayList<CookComment> queryCookComment(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有评论记录*/
	public ArrayList<CookComment> queryCookCommentList(@Param("where") String where) throws Exception;

	/*按照查询条件的评论记录数*/
	public int queryCookCommentCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条评论记录*/
	public CookComment getCookComment(int commentId) throws Exception;

	/*更新评论记录*/
	public void updateCookComment(CookComment cookComment) throws Exception;

	/*删除评论记录*/
	public void deleteCookComment(int commentId) throws Exception;

}
