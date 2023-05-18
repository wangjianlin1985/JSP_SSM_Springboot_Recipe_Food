package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Cooking;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.CookCollect;

import com.chengxusheji.mapper.CookCollectMapper;
@Service
public class CookCollectService {

	@Resource CookCollectMapper cookCollectMapper;
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

    /*添加菜谱收藏记录*/
    public void addCookCollect(CookCollect cookCollect) throws Exception {
    	cookCollectMapper.addCookCollect(cookCollect);
    }

    /*按照查询条件分页查询菜谱收藏记录*/
    public ArrayList<CookCollect> queryCookCollect(Cooking cookingObj,UserInfo userObj,String collectionTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_cookCollect.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_cookCollect.userObj='" + userObj.getUser_name() + "'";
    	if(!collectionTime.equals("")) where = where + " and t_cookCollect.collectionTime like '%" + collectionTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return cookCollectMapper.queryCookCollect(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<CookCollect> queryCookCollect(Cooking cookingObj,UserInfo userObj,String collectionTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_cookCollect.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_cookCollect.userObj='" + userObj.getUser_name() + "'";
    	if(!collectionTime.equals("")) where = where + " and t_cookCollect.collectionTime like '%" + collectionTime + "%'";
    	return cookCollectMapper.queryCookCollectList(where);
    }

    /*查询所有菜谱收藏记录*/
    public ArrayList<CookCollect> queryAllCookCollect()  throws Exception {
        return cookCollectMapper.queryCookCollectList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Cooking cookingObj,UserInfo userObj,String collectionTime) throws Exception {
     	String where = "where 1=1";
    	if(null != cookingObj && cookingObj.getCookingId()!= null && cookingObj.getCookingId()!= 0)  where += " and t_cookCollect.cookingObj=" + cookingObj.getCookingId();
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_cookCollect.userObj='" + userObj.getUser_name() + "'";
    	if(!collectionTime.equals("")) where = where + " and t_cookCollect.collectionTime like '%" + collectionTime + "%'";
        recordNumber = cookCollectMapper.queryCookCollectCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取菜谱收藏记录*/
    public CookCollect getCookCollect(int collectId) throws Exception  {
        CookCollect cookCollect = cookCollectMapper.getCookCollect(collectId);
        return cookCollect;
    }

    /*更新菜谱收藏记录*/
    public void updateCookCollect(CookCollect cookCollect) throws Exception {
        cookCollectMapper.updateCookCollect(cookCollect);
    }

    /*删除一条菜谱收藏记录*/
    public void deleteCookCollect (int collectId) throws Exception {
        cookCollectMapper.deleteCookCollect(collectId);
    }

    /*删除多条菜谱收藏信息*/
    public int deleteCookCollects (String collectIds) throws Exception {
    	String _collectIds[] = collectIds.split(",");
    	for(String _collectId: _collectIds) {
    		cookCollectMapper.deleteCookCollect(Integer.parseInt(_collectId));
    	}
    	return _collectIds.length;
    }
}
