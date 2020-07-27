/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.common.dto;

import java.util.List;

/**
 * Ztree节点数据封装
 * @author 董华健
 */
public class ZtreeNode {
	
	/**
	 * 节点id
	 */
	private String id;

	private String type;
	/**
	 * 节点名称
	 */
	private String name;
	/***
	 * 是否展开
	 */
	private boolean open;

	/**
	 * 是否上级节点
	 */
	private boolean isParent;

	/**
	 * 是否选中
	 */
	private boolean checked;

	/**
	 * 是否选中
	 */
	private boolean nocheck;
	
	/**
	 * 节点图标
	 */
	private String icon;
	
	private long level;
	
	/**
	 * 子节点数据
	 */
	private List<ZtreeNode> children;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<ZtreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ZtreeNode> children) {
		this.children = children;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	
}
