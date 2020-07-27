package com.pointlion.mvc.common.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.dto.ZtreeNode;
import com.pointlion.mvc.common.model.base.BaseSysOrg;
import com.pointlion.mvc.common.utils.ContextUtil;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class SysOrg extends BaseSysOrg<SysOrg> {
	public static final SysOrg dao = new SysOrg();
	public static List<SysOrg> allChildren = new ArrayList<SysOrg>();
	/***
	 * 根据主键查询
	 */
	public SysOrg getById(String id){
		return SysOrg.dao.findById(id);
	}
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SysOrg o = SysOrg.dao.getById(id);
    		o.delete();
    	}
	}

	/***
	 * 根据id 查询孩子(只查一级孩子)
	 * @param id
	 * @param type  0:部门 1:公司
	 * @return
	 */
	public List<SysOrg> getChildrenByPid(String id,String type){
		String sql = "select * from sys_org m where m.parent_id='"+id+"' ";
		if(StrKit.notBlank(type)){
			sql = sql + " and m.type='"+type+"' ";
		}
		sql = sql + " order by m.sort";
		return SysOrg.dao.find(sql);
	}
	/***
	 * 递归查询，所有的的孩子（不包含自己）,树形结构
	 * @param id
	 * @return
	 */
	public List<SysOrg> getChildrenAllTree(String id){
		List<SysOrg> list =  getChildrenByPid(id,null);//根据id查询孩子
		for(SysOrg o : list){
			o.setChildren(getChildrenAllTree(o.getId()));
		}
		return list;
	}
	/***
	 * 递归查询，所有的的孩子（不包含自己）
	 * @return
	 */
	public List<SysOrg> getAllChildren(String pid){
		allChildren.clear();
		List<SysOrg> orgList = dao.findAll();
		return getChildrenAll(orgList,pid);
	}
	private List<SysOrg> getChildrenAll(List<SysOrg>  orgs ,String pid){
		for (SysOrg org : orgs) {
			// 判断是否存在孩子
			if (pid.equals(org.getParentId())) {
				// 递归遍历上一级
				getChildrenAll(orgs, org.getId());
				allChildren.add(org);
			}
		}
		return allChildren;
	}
	/***
	 * 查询同一级的子公司下的所有单位
	 * 如果传入的是子公司id。返回不包含自己的所有的部门。
	 * 如果传入的是部门id。返回的是所有同一子公司的部门(也不包含自己)。
	 */
	public List<SysOrg> getChildCompanyList(String id){
		List<SysOrg> result = new ArrayList<SysOrg>();
		SysOrg org = SysOrg.dao.getById(id);
		if(org!=null){
			if("1".equals(org.getType())){//子公司
				result = dao.find("select * from sys_org o where o.parent_child_company_id='"+org.getId()+"'");
			}else if("".equals(org.getType())){//部门
				result = dao.find("select * from sys_org o where o.parent_child_company_id='"+org.getParentChildCompanyId()+"'");
			}
		}
		return result;
	}
	/***
	 * 递归查询，所有的父亲（不包括自己）
	 */
	public List<SysOrg> getParentAll(String id){
		List<SysOrg> result = new ArrayList<SysOrg>();
		SysOrg org = SysOrg.dao.getById(id);//自己
		result = getHisParent(result,org);
		return result;
	}
	/***
	 * 递归查父级
	 * @param list
	 * @param org
	 * @return
	 */
	public List<SysOrg> getHisParent(List<SysOrg> list,SysOrg org){
		String pid = org.getParentId();
		if(StrKit.notBlank(pid)){
			SysOrg parent = SysOrg.dao.getById(org.getParentId());
			list.add(parent);
			getHisParent(list,parent);
		}
		return list;
	}
	/***
	 * 菜单转成ZTreeNode
	 * @param 
	 * olist 数据
	 * open  是否展开所有
	 * ifOnlyLeaf 是否只选叶子
	 * @return
	 */
	public List<ZtreeNode> toZTreeNode(List<SysOrg> olist,Boolean open,Boolean ifOnlyLeaf){
		List<ZtreeNode> list = new ArrayList<ZtreeNode>();
		for(SysOrg o : olist){
			ZtreeNode node = toZTreeNode(o);
			if(o.getChildren()!=null&&o.getChildren().size()>0){//如果有孩子
				node.setChildren(toZTreeNode(o.getChildren(),open,ifOnlyLeaf));
				if(ifOnlyLeaf){//如果只选叶子
					node.setNocheck(true);
				}
			}
			if("1".equals(node.getType())){
				node.setOpen(true);
			}else{
				node.setOpen(open);
			}
			list.add(node);
		}
		return list;
	}
	/***
	 * 菜单转成ZtreeNode
	 * @param 
	 * @return
	 */
	public ZtreeNode toZTreeNode(SysOrg org){
		ZtreeNode node = new ZtreeNode();
		node.setId(org.getId());
		node.setName(org.getName());
		node.setType(org.getType());
		if("1".equals(org.getType())){
			node.setIcon(ContextUtil.getCtx()+"/common/plugins/zTree_v3/css/zTreeStyle/img/diy/1_open.png");
		}
//		node.setLevel(menu.getLevel());
		return node;
	}

	/***
	 * 根据id 查询孩子分页
	 * @param pnum
	 * @param psize
	 * @param pid
	 * @return
	 */
	public Page<Record> getChildrenPageByPid(int pnum,int psize, String pid){
		String sql = " from sys_org o1 LEFT JOIN sys_org o2 on o1.parent_id=o2.id ";
		if(StrKit.notBlank(pid)){
			sql = sql + " where o1.parent_id='"+pid+"' ";
		}
		sql = sql + " order by o1.sort ";
		Page<Record> page =  Db.paginate(pnum, psize, "select o1.* , o2.name parent_name ", sql);
		List<Record> list = page.getList();
		for(Record r : list){
			String parentCompanyId = r.getStr("parent_child_company_id");
			SysOrg o = SysOrg.dao.getById(parentCompanyId);
			if(o!=null){
				r.set("parent_child_company_name", o.getName());
			}
		}
		return page;
	}
	
	/****
	 * 获取某机构所在的一级子公司
	 */
	public SysOrg getFirstChildCompany(SysOrg org){
		SysOrg result = null;
		List<SysOrg> allList = org.getParentAll(org.getId());
		allList.add(org);//自己也有可能是子公司类型
		if(allList!=null&&allList.size()>0){
			for(SysOrg o:allList){
				if(StrKit.isBlank(o.getParentChildCompanyId())&&!"#root".equals(o.getId())&&"#root".equals(o.getParentId())){
					result = o;
				}
			}
		}
		return result;
	}
	
	/***
	 * 根据username获取组织结构
	 * @param username
	 */
	public SysOrg getByUsername(String username){
		SysUser user = SysUser.dao.getByUsername(username);
		if(user!=null){
			String orgid = user.getOrgid();
			if(StrKit.notBlank(orgid)){
				SysOrg org = SysOrg.dao.getById(orgid);
				return org;
			}
		}
		return null;
	}
	public SysOrg getByUserId(String userid){
		SysUser user = SysUser.dao.getById(userid);
		if(user!=null){
			String orgid = user.getOrgid();
			if(StrKit.notBlank(orgid)){
				SysOrg org = SysOrg.dao.getById(orgid);
				return org;
			}
		}
		return null;
	}
	
	public String getOrgNameById(String id){
		SysOrg org = SysOrg.dao.getById(id);
		if(org!=null){
			return org.getName();
		}else{
			return "";
		}
	}
	
}
