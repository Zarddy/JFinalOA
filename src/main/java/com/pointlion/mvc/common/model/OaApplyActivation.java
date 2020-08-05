package com.pointlion.mvc.common.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.base.BaseOaApplyActivation;
@SuppressWarnings("serial")
public class OaApplyActivation extends BaseOaApplyActivation<OaApplyActivation> {
	public static final OaApplyActivation dao = new OaApplyActivation();
	public static final String tableName = "oa_apply_activation";
	
	/***
	 * query by id
	 */
	public OaApplyActivation getById(String id){
		return OaApplyActivation.dao.findById(id);
	}
	
	/***
	 * del
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		OaApplyActivation o = OaApplyActivation.dao.getById(id);
    		o.delete();
    	}
	}
	
}