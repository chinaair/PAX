package com.chinaair.util;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JasperUtil {
	public static void createReportAndDownloadPDF(String jasperName, List<?> dataList, Map<String, Object> parametersMap) throws Exception  {
		JasperPrint print = createJasper(jasperName, dataList, parametersMap);
		HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
		//httpServletResponse.addHeader("Content-disposition", "inline; filename=report.pdf");//display PDF on browser
		ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(print, servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public static JasperPrint createJasper(String jasperName, List<?> dataList, Map<String, Object> parametersMap) throws JRException {
		String jasperPath = getJasperPath(jasperName);
		Map<String, Object> parameters = parametersMap;
		if(parameters == null) {
			parameters = new HashMap<>();
		}
		JasperPrint jasperPrint = null;
		if(jasperPath==null) {
			throw new RuntimeException("Jasper file error!");
		}
		
		if(dataList==null) {
			jasperPrint = JasperFillManager.fillReport(jasperPath, parameters);
		} else {
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(dataList);
			jasperPrint = JasperFillManager.fillReport(jasperPath, parameters, datasource);
		}
		return jasperPrint;
	}
	
	public static String getJasperPath(String jasperName) {
		String jasperPath = null;
		try {
			String jasperFileName = jasperName + ".jasper";
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			Enumeration<URL> names = cl.getResources("jasper/" + jasperFileName);
			while (names.hasMoreElements()) {
				URL jasperUrl = names.nextElement();
				jasperPath = jasperUrl.getPath();
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return jasperPath;
	}
}
