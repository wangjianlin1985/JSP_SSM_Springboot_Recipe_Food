package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Cooking;

public interface CookingMapper {
	/*添加烹饪菜谱信息*/
	public void addCooking(Cooking cooking) throws Exception;

	/*按照查询条件分页查询烹饪菜谱记录*/
	public ArrayList<Cooking> queryCooking(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有烹饪菜谱记录*/
	public ArrayList<Cooking> queryCookingList(@Param("where") String where) throws Exception;

	/*按照查询条件的烹饪菜谱记录数*/
	public int queryCookingCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条烹饪菜谱记录*/
	public Cooking getCooking(int cookingId) throws Exception;

	/*更新烹饪菜谱记录*/
	public void updateCooking(Cooking cooking) throws Exception;

	/*删除烹饪菜谱记录*/
	public void deleteCooking(int cookingId) throws Exception;

}
