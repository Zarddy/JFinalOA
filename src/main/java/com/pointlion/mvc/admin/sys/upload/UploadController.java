/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.upload.UploadFile;
import com.pointlion.config.MainConfig;
import com.pointlion.mvc.common.base.BaseController;
import com.pointlion.mvc.common.utils.office.excel.ExcelUtil;

/***
 * 通知公告控制器（web）
 * @author Administrator
 *
 */
public class UploadController extends BaseController {
	
	/***
	 * 文件上传
	 */
	public void upload(){
		UploadFile file = getFile("file","/content");
		Map<String,String> data = new HashMap<String , String>();
		//重命名为新文件名-防止上传名称重复。
//		File f = file.getFile().renameTo(new File(file.getSaveDirectory()+"aa.jpg"));
		data.put("filename", file.getFileName());
		data.put("path", MainConfig.constants.getBaseUploadPath()+"content/");
		renderSuccess(data,"上传成功");
	}
	
	/***
	 * 导入组织结构，角色，用户
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
    public void importOrgRoleUser() throws FileNotFoundException, IOException{
    	List<List<String>> list = ExcelUtil.excelToList("D:/1.xlsx");
    	SysOrgImportService.me.importOrg(list);
    	SysRoleImportService.me.importRole(list);
    	SysUserImportService.me.importUser(list);
    	renderSuccess();
    }
}
