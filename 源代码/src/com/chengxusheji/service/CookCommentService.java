package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Cooking;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.CookComment;

import com.chengxusheji.mapper.CookCommentMapper;
@Service
public class CookCommentService {

	@Resource CookCommentMapper cookCommentMapper;
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

    /*添加评论记录*/
    public void addCookComment(CookComment cookComment) throws Exception {
    	cookCommentMapper.addCookComment(cookComment);
    }

    /*按照查询条件分页查询评论记录*/
    public ArrayList<CookComment> queryCookComment(Cooking cookingObj,UserInfo userObj,String commentTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_cookComment.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_cookComment.userObj='" + userObj.getUser_name() + "'";
    	if(!commentTime.equals("")) where = where + " and t_cookComment.commentTime like '%" + commentTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return cookCommentMapper.queryCookComment(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<CookComment> queryCookComment(Cooking cookingObj,UserInfo userObj,String commentTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_cookComment.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_cookComment.userObj='" + userObj.getUser_name() + "'";
    	if(!commentTime.equals("")) where = where + " and t_cookComment.commentTime like '%" + commentTime + "%'";
    	return cookCommentMapper.queryCookCommentList(where);
    }

    /*查询所有评论记录*/
    public ArrayList<CookComment> queryAllCookComment()  throws Exception {
        return cookCommentMapper.queryCookCommentList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Cooking cookingObj,UserInfo userObj,String commentTime) throws Exception {
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_cookComment.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_cookComment.userObj='" + userObj.getUser_name() + "'";
    	if(!commentTime.equals("")) where = where + " and t_cookComment.commentTime like '%" + commentTime + "%'";
        recordNumber = cookCommentMapper.queryCookCommentCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取评论记录*/
    public CookComment getCookComment(int commentId) throws Exception  {
        CookComment cookComment = cookCommentMapper.getCookComment(commentId);
        return cookComment;
    }

    /*更新评论记录*/
    public void updateCookComment(CookComment cookComment) throws Exception {
        cookCommentMapper.updateCookComment(cookComment);
    }

    /*删除一条评论记录*/
    public void deleteCookComment (int commentId) throws Exception {
        cookCommentMapper.deleteCookComment(commentId);
    }

    /*删除多条评论信息*/
    public int deleteCookComments (String commentIds) throws Exception {
    	String _commentIds[] = commentIds.split(",");
    	for(String _commentId: _commentIds) {
    		cookCommentMapper.deleteCookComment(Integer.parseInt(_commentId));
    	}
    	return _commentIds.length;
    }
}
