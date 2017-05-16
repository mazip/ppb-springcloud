package com.ppb.util.code;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author daizy
 *
 * 代码生成工程
 */
public class CodeFactory {
	
	private static final Log log = LogFactory.getLog(CodeFactory.class);
	
	private static String url = CodeResourceUtil.URL;
	private static String username = CodeResourceUtil.USERNAME;
	private static String passWord = CodeResourceUtil.PASSWORD;
	
	/**
	 * @param tableName  表名称
	 * @param codeName  注释
	 * @param entityPackage 实体model包
	 * @param keyType 主键类型  01 uuid  02 自增长
	 * @param pageFolder 
	 * @param isFtl  view层是否为ftl
	 */
	public static void codeGenerate(String tableName, String codeName, String entityPackage, String keyType, String pageFolder,
			boolean isFtl){
		log.info("----------------------------代码生成开始---------------------------");
		// pageFlder为空 默认为view
		if (null == pageFolder || "".equals(pageFolder)) {
			pageFolder = "view";
		}
		// 数据库链接
		CreateBean createBean = new CreateBean();
		createBean.setMysqlInfo(url, username, passWord);
		//根据表名称得到实体类名称---驼峰转换
		String className = createBean.getTablesNameToClassName(tableName);
		
		
		// model 主路径
		String pckPath = CodeResourceUtil.TARGET_ENTITY_PROJECT + "\\";
		// 实体类路径
		String modelPath = CodeResourceUtil.MODEL_PACKAGE.replace(".", "/")+ "\\" + className + ".java";
		String modelExamplePath = CodeResourceUtil.MODEL_PACKAGE.replace(".", "/")+ "\\" + className + "Example.java";
		
		String daoPath = CodeResourceUtil.DAO_PACKAGE.replace(".", "/")+ "\\" + className + "Mapper.java";
		
		String servicePath = CodeResourceUtil.SERVICE_PACKAGE.replace(".", "/")+ "\\" + className + "Service.java";
		
		String controllerPath = CodeResourceUtil.CONTROLLER_PACKAGE.replace(".", "/")+ "\\" + className + "Controller.java";
		
		String mapperPath = CodeResourceUtil.XML_MAPPER_PACKAGE.replace(".", "/")+"\\" + className + "Mapper.xml";
		
		String ftlPath = CodeResourceUtil.FTL_PACKAGE.replace(".", "/")+"\\" + className + "Page.ftl";
		
		Map<String,Object> context = new HashMap<String, Object>();
		context.put("className", className);
		context.put("lowClassName", className.substring(0, 1).toLowerCase()+className.substring(1, className.length()));
		context.put("entityPackage", CodeResourceUtil.MODEL_PACKAGE);
		context.put("servicePackage", CodeResourceUtil.SERVICE_PACKAGE);
		context.put("controllerPackage",CodeResourceUtil.CONTROLLER_PACKAGE);
		context.put("daoPackage", CodeResourceUtil.DAO_PACKAGE);
		context.put("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		context.put("tableName", tableName);
		try {
			context.put("feilds", createBean.getBeanFeilds(tableName));
			context.put("importPackage", createBean.getBeanImportPackage(tableName));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// 实体类生成
		CommonPageParser.WriterCreatePage(context, "EntityTemplate.ftl", pckPath, modelPath);
		CommonPageParser.WriterCreatePage(context, "EntityExampleTemplate.ftl", pckPath, modelExamplePath);
		CommonPageParser.WriterCreatePage(context, "DaoTemplate.ftl", pckPath, daoPath);
		CommonPageParser.WriterCreatePage(context, "ServiceTemplate.ftl", pckPath, servicePath);
		CommonPageParser.WriterCreatePage(context, "ControllerTemplate.ftl", pckPath, controllerPath);
		// xml生成
		CommonPageParser.WriterCreatePage(context, "MapperTemplate.ftl", CodeResourceUtil.TARGET_SERVER_MAPPER_PROJECT, mapperPath);
		CommonPageParser.WriterCreatePage(context, "easyui.ftl", "", ftlPath);
		
		log.info("----------------------------代码生成完毕---------------------------");
	}

}