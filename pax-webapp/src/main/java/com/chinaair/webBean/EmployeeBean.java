package com.chinaair.webBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Employee;
import com.chinaair.entity.Menu;
import com.chinaair.services.EmployeeServiceBean;
import com.chinaair.services.MenuServiceBean;
import com.chinaair.util.JSFUtil;
import com.mysql.jdbc.StringUtils;

@ConversationScoped
@Named("employeeBean")
public class EmployeeBean implements Serializable {

	private static final long serialVersionUID = -4000495264925962145L;
	
	private static final String NEW = "0";
	
	private static final String VIEW = "1";
	
	private static final String EDIT = "2";
	
	@EJB
	private EmployeeServiceBean employeeService;
	
	@EJB
	private MenuServiceBean menuService;
	
	private List<Employee> employeeList;
	
	private Employee selectedEmployee;
	
	
	
	/*******Input Screen variables********/
	
	private String screenMode;
	
	
	private String empName;
	
	private String taxCode;
	
	private String position;
	
	private String userId;
	
	private String password;
	
	private String authority;
		
	private boolean usageFlag;
	private List<SelectItem> menuCombobox;
	private List<Menu> menuList;
	private Map<Long,Menu> mapMenuList;
	private Map<Long,Menu> mapMenuCombobox;
	private Long selectedMenuId;
	
	private String searchEmpName ;
	private String searchUserId ;
	
	private boolean isUpdate ;
	/*******End of Screen variables********/
	
	private ResourceBundle bundle;
	
	@Inject
	private Conversation conversation;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");
		initValueItem();
		setEmployeeList(employeeService.getAllEmployee());
	}
	
	public void selectEmployeeFromDialog(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(selectedEmployee);
	}
	
	public void initValueItem() {
		setScreenMode(NEW);
		this.empName = "";
		this.position = "";
		this.authority = "";
		this.password = "";
		this.userId = "";
		this.taxCode = "";
		this.usageFlag = false;
		createValueComboboxMenu();
	}
	private boolean checkError(Employee employee,boolean isUpdate){
		//check userID is exists
		Employee emp = employeeService.findEmployeeByUserId(employee, isUpdate);
		if(emp != null){
			return true;	
		}
		return false;
	}
	public String registerEmployee() {
		
		Employee newEmployee = new Employee();
		newEmployee.setEmpName(this.empName);
		newEmployee.setPassword(this.password);
		newEmployee.setUserId(this.userId);
		newEmployee.setTaxCode(this.taxCode);
		newEmployee.setPosition(this.position);
		newEmployee.setUsageFlag(this.usageFlag);
		String menuIdList = "";
		if(menuList!=null && !menuList.isEmpty()){
			for(Menu menu: menuList){
				if(StringUtils.isNullOrEmpty(menuIdList)){
					menuIdList = menu.getId().toString();
				}else{
					menuIdList += "," + menu.getId().toString();
				}
			}
		}
		newEmployee.setAuthority(menuIdList);
		if(checkError(newEmployee,false)) {
			JSFUtil.addInfo(bundle, null,null, "employee_error_userId_is_exist");
			return "";
		}
		employeeService.insert(newEmployee);
		searchEmployee();
		JSFUtil.addInfo(bundle, null, null, "employee_saveSuccessfully");
		return "EmployeeListScreen?faces-redirect=true";
	}
	public String gotoNewEmployeeScreen() {
		initValueItem();
		return "EmployeeScreen?faces-redirect=true";
	}
	
	public String gotoViewEmployeeScreen() {
		if(selectedEmployee == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return null;
		}
		mapMenuList = new HashMap<Long,Menu>();
		if(selectedEmployee != null) {
			this.empName = selectedEmployee.getEmpName();
			this.position = selectedEmployee.getPosition();
			this.taxCode = selectedEmployee.getTaxCode();
			this.userId = selectedEmployee.getUserId();
			this.password = selectedEmployee.getPassword();
			this.authority = selectedEmployee.getAuthority();
			this.usageFlag = selectedEmployee.isUsageFlag();
			menuList = new ArrayList<>();
			if(!StringUtils.isNullOrEmpty(authority)){
				if(authority.contains(",")){
					String[] buff = authority.split(",");
					if(buff != null){
						for(int i=0;i<buff.length;i++){
							Long key = new Long(buff[i]);
							if(!mapMenuList.containsKey(key)){
								Menu menu = menuService.findMenuByMenuId(key);
								mapMenuList.put(key, menu);
							}
							menuList.add(mapMenuList.get(key));
						}
					}
					
				}else{ 
					Long key = new Long(authority);
					if(!mapMenuList.containsKey(key)){
						Menu menu = menuService.findMenuByMenuId(key);
						mapMenuList.put(key, menu);
					}
					menuList.add(mapMenuList.get(key));
				}
			}
		}
		setScreenMode(VIEW);
		return "EmployeeScreen?faces-redirect=true";
	}
	
	public void editEmployee() {
		setScreenMode(EDIT);
		createValueComboboxMenu();
	}
	
	public String updateEmployee() {
		selectedEmployee.setEmpName(this.empName);
		selectedEmployee.setPosition(this.position);
		selectedEmployee.setTaxCode(this.taxCode);
		selectedEmployee.setUserId(this.userId);
		selectedEmployee.setPassword(this.password);
		selectedEmployee.setUsageFlag(this.usageFlag);;
		String menuIdList = "";
		if(menuList!=null && !menuList.isEmpty()){
			for(Menu menu: menuList){
				if(StringUtils.isNullOrEmpty(menuIdList)){
					menuIdList = menu.getId().toString();
				}else{
					menuIdList += "," + menu.getId().toString();
				}
			}
		}
		selectedEmployee.setAuthority(menuIdList);
		if (checkError(selectedEmployee, true)) {
			JSFUtil.addInfo(bundle, null,null, "employee_error_userId_is_exist");
			return "";
		}
		employeeService.update(selectedEmployee);
		selectedEmployee = null;
		searchEmployee();
		JSFUtil.addInfo(bundle, null, null, "employee_updateSuccessfully");
		return "EmployeeListScreen?faces-redirect=true";
	}
	public String deleteEmployee() {
		if(selectedEmployee != null) {
			employeeService.delete(selectedEmployee);
			initValueItem();
			searchEmployee();
			JSFUtil.addInfo(bundle, null, null, "employee_deleteSuccessfully");
			return "EmployeeListScreen?faces-redirect=true";
		}else {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
		}
		return "";
	}
	public String cancelRegisterEmployee() {
		initValueItem();
		selectedEmployee = null;
		searchEmployee();
		return "EmployeeListScreen?faces-redirect=true";
	}
	
	public void printEmployeeInfo() {
	}
	
	public void searchEmployee() {
		setEmployeeList(employeeService.findEmployeeByCondition(searchEmpName,searchUserId));
	}
	public void refresh() {
		initValueItem();
	}

	public void addMenu(){
		if(menuList == null){
			menuList = new  ArrayList<>();
		}
		if(mapMenuList == null){
			mapMenuList = new HashMap<Long,Menu>();
		}
		if(selectedMenuId !=null && !mapMenuList.containsKey(selectedMenuId) && mapMenuCombobox.get(selectedMenuId)!=null){
			Menu menu = mapMenuCombobox.get(selectedMenuId);
			mapMenuList.put(selectedMenuId,menu);
			menuList.add(menu);
		}
	}
	public void removeMenu(Menu menu){
		if(menu!=null && menuList!=null && !menuList.isEmpty()){
			menuList.remove(menu);
		}
	}
	public void onRowSelectEmployee(){
		
	}
	
	public void createValueComboboxMenu() {
		Locale local =  FacesContext.getCurrentInstance().getViewRoot().getLocale();
		menuCombobox = new ArrayList<SelectItem>();
		mapMenuCombobox = new HashMap<Long,Menu>();
		List<Menu> list = menuService.getAllMenuChild();
		if (!list.isEmpty()) {
			for (Menu menu : list) {
				String menuName = "";
				if(local.getLanguage().equals("vi")){
					menuName = menu.getMenu_vi();
				} else{
					menuName = menu.getMenu();
				}
				menuCombobox.add(new SelectItem(menu.getId(), menuName));
				mapMenuCombobox.put(menu.getId(), menu);
			}

		}
	}
	/***************From there, get set methods only******************/
	

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}


	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	/**
	 * @return the screenMode
	 */
	public String getScreenMode() {
		return screenMode;
	}

	/**
	 * @param screenMode the screenMode to set
	 */
	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getSearchEmpName() {
		return searchEmpName;
	}

	public void setSearchEmpName(String searchEmpName) {
		this.searchEmpName = searchEmpName;
	}

	public String getSearchUserId() {
		return searchUserId;
	}

	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}

	public List<SelectItem> getMenuCombobox() {
		return menuCombobox;
	}

	public void setMenuCombobox(List<SelectItem> menuCombobox) {
		this.menuCombobox = menuCombobox;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Long getSelectedMenuId() {
		return selectedMenuId;
	}

	public void setSelectedMenuId(Long selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

	public boolean isUsageFlag() {
		return usageFlag;
	}

	public void setUsageFlag(boolean usageFlag) {
		this.usageFlag = usageFlag;
	}

}
