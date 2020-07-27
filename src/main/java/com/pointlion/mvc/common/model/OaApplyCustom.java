package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseOaApplyCustom;
@SuppressWarnings("serial")
public class OaApplyCustom extends BaseOaApplyCustom<OaApplyCustom> {
	public static final OaApplyCustom dao = new OaApplyCustom();
	public static final String tableName = "oa_apply_custom";
	
	/***
	 * 根据主键查询
	 */
	public OaApplyCustom getById(String id){
		return OaApplyCustom.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize){
		String sql  = " from "+tableName+" o ";
		return Db.paginate(pnum, psize, " select * ", sql);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		OaApplyCustom o = OaApplyCustom.dao.getById(id);
    		o.delete();
    	}
	}
	
}