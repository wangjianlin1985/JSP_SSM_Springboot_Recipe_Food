package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.LearnRec;

public interface LearnRecMapper {
	/*添加学习记录信息*/
	public void addLearnRec(LearnRec learnRec) throws Exception;

	/*按照查询条件分页查询学习记录记录*/
	public ArrayList<LearnRec> queryLearnRec(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有学习记录记录*/
	public ArrayList<LearnRec> queryLearnRecList(@Param("where") String where) throws Exception;

	/*按照查询条件的学习记录记录数*/
	public int queryLearnRecCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条学习记录记录*/
	public LearnRec getLearnRec(int recId) throws Exception;

	/*更新学习记录记录*/
	public void updateLearnRec(LearnRec learnRec) throws Exception;

	/*删除学习记录记录*/
	public void deleteLearnRec(int recId) throws Exception;

}
