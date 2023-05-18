package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.CookClass;
import com.chengxusheji.po.Cooking;

import com.chengxusheji.mapper.CookingMapper;
@Service
public class CookingService {

	@Resource CookingMapper cookingMapper;
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

    /*添加烹饪菜谱记录*/
    public void addCooking(Cooking cooking) throws Exception {
    	cookingMapper.addCooking(cooking);
    }

    /*按照查询条件分页查询烹饪菜谱记录*/
    public ArrayList<Cooking> queryCooking(CookClass cookClassObj,String cookingName,String gongxiao,String yongliao,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != cookClassObj && cookClassObj.getCookClassId()!= null && cookClassObj.getCookClassId()!= 0)  where += " and t_cooking.cookClassObj=" + cookClassObj.getCookClassId();
    	if(!cookingName.equals("")) where = where + " and t_cooking.cookingName like '%" + cookingName + "%'";
    	if(!gongxiao.equals("")) where = where + " and t_cooking.gongxiao like '%" + gongxiao + "%'";
    	if(!yongliao.equals("")) where = where + " and t_cooking.yongliao like '%" + yongliao + "%'";
    	if(!addTime.equals("")) where = where + " and t_cooking.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return cookingMapper.queryCooking(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Cooking> queryCooking(CookClass cookClassObj,String cookingName,String gongxiao,String yongliao,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != cookClassObj && cookClassObj.getCookClassId()!= null && cookClassObj.getCookClassId()!= 0)  where += " and t_cooking.cookClassObj=" + cookClassObj.getCookClassId();
    	if(!cookingName.equals("")) where = where + " and t_cooking.cookingName like '%" + cookingName + "%'";
    	if(!gongxiao.equals("")) where = where + " and t_cooking.gongxiao like '%" + gongxiao + "%'";
    	if(!yongliao.equals("")) where = where + " and t_cooking.yongliao like '%" + yongliao + "%'";
    	if(!addTime.equals("")) where = where + " and t_cooking.addTime like '%" + addTime + "%'";
    	return cookingMapper.queryCookingList(where);
    }

    /*查询所有烹饪菜谱记录*/
    public ArrayList<Cooking> queryAllCooking()  throws Exception {
        return cookingMapper.queryCookingList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(CookClass cookClassObj,String cookingName,String gongxiao,String yongliao,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != cookClassObj && cookClassObj.getCookClassId()!= null && cookClassObj.getCookClassId()!= 0)  where += " and t_cooking.cookClassObj=" + cookClassObj.getCookClassId();
    	if(!cookingName.equals("")) where = where + " and t_cooking.cookingName like '%" + cookingName + "%'";
    	if(!gongxiao.equals("")) where = where + " and t_cooking.gongxiao like '%" + gongxiao + "%'";
    	if(!yongliao.equals("")) where = where + " and t_cooking.yongliao like '%" + yongliao + "%'";
    	if(!addTime.equals("")) where = where + " and t_cooking.addTime like '%" + addTime + "%'";
        recordNumber = cookingMapper.queryCookingCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取烹饪菜谱记录*/
    public Cooking getCooking(int cookingId) throws Exception  {
        Cooking cooking = cookingMapper.getCooking(cookingId);
        return cooking;
    }

    /*更新烹饪菜谱记录*/
    public void updateCooking(Cooking cooking) throws Exception {
        cookingMapper.updateCooking(cooking);
    }

    /*删除一条烹饪菜谱记录*/
    public void deleteCooking (int cookingId) throws Exception {
        cookingMapper.deleteCooking(cookingId);
    }

    /*删除多条烹饪菜谱信息*/
    public int deleteCookings (String cookingIds) throws Exception {
    	String _cookingIds[] = cookingIds.split(",");
    	for(String _cookingId: _cookingIds) {
    		cookingMapper.deleteCooking(Integer.parseInt(_cookingId));
    	}
    	return _cookingIds.length;
    }
}
