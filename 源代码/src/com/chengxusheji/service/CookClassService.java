package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.CookClass;

import com.chengxusheji.mapper.CookClassMapper;
@Service
public class CookClassService {

	@Resource CookClassMapper cookClassMapper;
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

    /*添加烹饪分类记录*/
    public void addCookClass(CookClass cookClass) throws Exception {
    	cookClassMapper.addCookClass(cookClass);
    }

    /*按照查询条件分页查询烹饪分类记录*/
    public ArrayList<CookClass> queryCookClass(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return cookClassMapper.queryCookClass(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<CookClass> queryCookClass() throws Exception  { 
     	String where = "where 1=1";
    	return cookClassMapper.queryCookClassList(where);
    }

    /*查询所有烹饪分类记录*/
    public ArrayList<CookClass> queryAllCookClass()  throws Exception {
        return cookClassMapper.queryCookClassList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = cookClassMapper.queryCookClassCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取烹饪分类记录*/
    public CookClass getCookClass(int cookClassId) throws Exception  {
        CookClass cookClass = cookClassMapper.getCookClass(cookClassId);
        return cookClass;
    }

    /*更新烹饪分类记录*/
    public void updateCookClass(CookClass cookClass) throws Exception {
        cookClassMapper.updateCookClass(cookClass);
    }

    /*删除一条烹饪分类记录*/
    public void deleteCookClass (int cookClassId) throws Exception {
        cookClassMapper.deleteCookClass(cookClassId);
    }

    /*删除多条烹饪分类信息*/
    public int deleteCookClasss (String cookClassIds) throws Exception {
    	String _cookClassIds[] = cookClassIds.split(",");
    	for(String _cookClassId: _cookClassIds) {
    		cookClassMapper.deleteCookClass(Integer.parseInt(_cookClassId));
    	}
    	return _cookClassIds.length;
    }
}
