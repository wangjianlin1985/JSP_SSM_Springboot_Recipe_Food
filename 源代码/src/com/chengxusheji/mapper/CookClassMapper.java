package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.CookClass;

public interface CookClassMapper {
	/*添加烹饪分类信息*/
	public void addCookClass(CookClass cookClass) throws Exception;

	/*按照查询条件分页查询烹饪分类记录*/
	public ArrayList<CookClass> queryCookClass(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有烹饪分类记录*/
	public ArrayList<CookClass> queryCookClassList(@Param("where") String where) throws Exception;

	/*按照查询条件的烹饪分类记录数*/
	public int queryCookClassCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条烹饪分类记录*/
	public CookClass getCookClass(int cookClassId) throws Exception;

	/*更新烹饪分类记录*/
	public void updateCookClass(CookClass cookClass) throws Exception;

	/*删除烹饪分类记录*/
	public void deleteCookClass(int cookClassId) throws Exception;

}
