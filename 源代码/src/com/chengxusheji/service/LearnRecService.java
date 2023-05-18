package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Cooking;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.LearnRec;

import com.chengxusheji.mapper.LearnRecMapper;
@Service
public class LearnRecService {

	@Resource LearnRecMapper learnRecMapper;
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

    /*添加学习记录记录*/
    public void addLearnRec(LearnRec learnRec) throws Exception {
    	learnRecMapper.addLearnRec(learnRec);
    }

    /*按照查询条件分页查询学习记录记录*/
    public ArrayList<LearnRec> queryLearnRec(Cooking cookingObj,UserInfo userObj,String learnTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_learnRec.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_learnRec.userObj='" + userObj.getUser_name() + "'";
    	if(!learnTime.equals("")) where = where + " and t_learnRec.learnTime like '%" + learnTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return learnRecMapper.queryLearnRec(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<LearnRec> queryLearnRec(Cooking cookingObj,UserInfo userObj,String learnTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_learnRec.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_learnRec.userObj='" + userObj.getUser_name() + "'";
    	if(!learnTime.equals("")) where = where + " and t_learnRec.learnTime like '%" + learnTime + "%'";
    	return learnRecMapper.queryLearnRecList(where);
    }

    /*查询所有学习记录记录*/
    public ArrayList<LearnRec> queryAllLearnRec()  throws Exception {
        return learnRecMapper.queryLearnRecList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Cooking cookingObj,UserInfo userObj,String learnTime) throws Exception {
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_learnRec.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_learnRec.userObj='" + userObj.getUser_name() + "'";
    	if(!learnTime.equals("")) where = where + " and t_learnRec.learnTime like '%" + learnTime + "%'";
        recordNumber = learnRecMapper.queryLearnRecCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取学习记录记录*/
    public LearnRec getLearnRec(int recId) throws Exception  {
        LearnRec learnRec = learnRecMapper.getLearnRec(recId);
        return learnRec;
    }

    /*更新学习记录记录*/
    public void updateLearnRec(LearnRec learnRec) throws Exception {
        learnRecMapper.updateLearnRec(learnRec);
    }

    /*删除一条学习记录记录*/
    public void deleteLearnRec (int recId) throws Exception {
        learnRecMapper.deleteLearnRec(recId);
    }

    /*删除多条学习记录信息*/
    public int deleteLearnRecs (String recIds) throws Exception {
    	String _recIds[] = recIds.split(",");
    	for(String _recId: _recIds) {
    		learnRecMapper.deleteLearnRec(Integer.parseInt(_recId));
    	}
    	return _recIds.length;
    }
}
