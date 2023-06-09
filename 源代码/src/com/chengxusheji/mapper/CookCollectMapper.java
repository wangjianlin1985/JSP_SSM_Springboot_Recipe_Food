package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.CookCollect;

public interface CookCollectMapper {
	/*添加菜谱收藏信息*/
	public void addCookCollect(CookCollect cookCollect) throws Exception;

	/*按照查询条件分页查询菜谱收藏记录*/
	public ArrayList<CookCollect> queryCookCollect(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有菜谱收藏记录*/
	public ArrayList<CookCollect> queryCookCollectList(@Param("where") String where) throws Exception;

	/*按照查询条件的菜谱收藏记录数*/
	public int queryCookCollectCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条菜谱收藏记录*/
	public CookCollect getCookCollect(int collectId) throws Exception;

	/*更新菜谱收藏记录*/
	public void updateCookCollect(CookCollect cookCollect) throws Exception;

	/*删除菜谱收藏记录*/
	public void deleteCookCollect(int collectId) throws Exception;

}
