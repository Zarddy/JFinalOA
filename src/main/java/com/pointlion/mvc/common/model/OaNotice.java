package com.pointlion.mvc.common.model;

import com.jfinal.plugin.activerecord.Page;
import com.pointlion.mvc.common.model.base.BaseOaNotice;

/**
 * Generated by JFinal.
 */
public class OaNotice extends BaseOaNotice<OaNotice> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final OaNotice dao = new OaNotice();
	
	public Page<OaNotice> getPage(int pnum,int psize){
		return OaNotice.dao.paginate(pnum, psize, "select * "," FROM oa_notice n ORDER BY n.create_time DESC");
	}
}
