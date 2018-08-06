package com.chinaair.webBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Customer;
import com.chinaair.entity.Employee;
import com.chinaair.entity.Menu;
import com.chinaair.services.CommonServiceBean;
import com.chinaair.services.EmployeeServiceBean;
import com.chinaair.services.MenuServiceBean;
import com.mysql.jdbc.StringUtils;

@ApplicationScoped
@Named("mainBean")
public class MainBean implements Serializable {

	private static final long serialVersionUID = -4000495264925962145L;
	
	@EJB
	private MenuServiceBean menuService;
	
	@EJB
	private EmployeeServiceBean employeeService;
	
	@EJB
	private CommonServiceBean commonService;
	
	private String localeCode;
	
	private static Map<String,Object> countries;
	
	
	private List<Menu> menuList;
	
	private Map<Long, List<Menu>> mapMenuChildList = new HashMap<Long, List<Menu>>();
	private Map<Long, List<Menu>> mapMenuParentList = new HashMap<Long, List<Menu>>();
	/*******Input Screen variables********/
	
	@Inject
	private LoginBean loginBean;
	
	private String menu;
	
	private String link;
	
	private int level;
	
	private String icon;
	
	private Long parentId;

	private Employee loginUser;
	
	private Map<String,List<Menu>> mapMenuList;
	
	private String userName;
	private String password;
	private List<SelectItem> parentMenuCombobox;
	private String parentMenu;
	
	private boolean disableParentCombobox;
	
	private boolean useFlag;
	
	private String searchMenu;
	
	private boolean isUpdate ;
	/*******End of Screen variables********/
	
	@Inject
	private Conversation conversation;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		if(mapMenuChildList == null){
			mapMenuChildList = new HashMap<Long, List<Menu>>();	
		}
		if(mapMenuParentList == null){
			mapMenuParentList = new HashMap<Long, List<Menu>>();	
		}
		if(mapMenuList == null){
			mapMenuList = new HashMap<String,List<Menu>>();	
		}
		if(menuList == null){
			menuList = new ArrayList<Menu>();	
		}
		
		countries = new LinkedHashMap<String,Object>();
		countries.put("English", Locale.ENGLISH); //label, value
		countries.put("Vietnamese", new Locale("vi"));
		if(loginBean!=null && loginBean.isLoggedIn() && loginBean.getLoginUser()!=null){
			loginUser = loginBean.getLoginUser();
		} 
	}
	 public String doLogin() {
	        loginUser = null;
	        resetMenu();
	        return "/login.xhtml?faces-redirect=true";
	}
	public void resetMenu(){
		mapMenuChildList = new HashMap<Long, List<Menu>>();
		mapMenuParentList = new HashMap<Long, List<Menu>>();
		mapMenuList = new HashMap<String,List<Menu>>();	
		menuList = new ArrayList<Menu>();
	}
	public List<Menu> getMenuParent(){
		if(mapMenuList == null){
			mapMenuList = new HashMap<String,List<Menu>>();	
		}
		if(loginBean!=null){
			loginUser = loginBean.getLoginUser();
		}
		if(loginUser != null){
			if(mapMenuList.containsKey(loginUser.getUserId())){
				menuList = mapMenuList.get(loginUser.getUserId());
			} else{
				String authority = loginUser.getAuthority();
				if(!StringUtils.isNullOrEmpty(authority)){
					menuList = menuService.getParentMenuByChildMenuId(authority);
					mapMenuList.put(loginUser.getUserId(), menuList);
				}
			}
		} 
		return menuList;
	}
	
	public void selectMenuFromDialog(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(searchMenu);
	}
	
	public String getMenuName(Menu menuItem){
		Locale local =  FacesContext.getCurrentInstance().getViewRoot().getLocale();
		String menu = "";
		if(local.getLanguage().equals("vi")){
			menu = menuItem.getMenu_vi();
		} else{
			menu = menuItem.getMenu();
		}
		return menu;
		
	}
	
	public List<Menu> getMenuChildList(Long parentId){
		List<Menu> menuChildList = new ArrayList<>();
		Map<Long, Menu> mapMenu = new HashMap<Long, Menu>();
		if(mapMenuChildList == null){
			mapMenuChildList = new HashMap<Long, List<Menu>>();
		}
		List<Menu> tmpMenulist = new ArrayList<>();
		String authority = loginUser.getAuthority();
		if(!StringUtils.isNullOrEmpty(authority)){
			if(!mapMenuChildList.containsKey(parentId)){
				tmpMenulist =  menuService.getMenuByParentId(parentId);
				if(!tmpMenulist.isEmpty()){
					mapMenuChildList.put(parentId, tmpMenulist);
				}
			}else{
				tmpMenulist = mapMenuChildList.get(parentId);
			}
			if(authority.contains(",")){
				String[] buff = authority.split(",");
				if(buff != null){
					for(int i=0;i<buff.length;i++){
						Long key = new Long(buff[i]);
						mapMenu.put(key, null);
					}
				}
				
			} else{
				Long key = new Long(authority);
				mapMenu.put(key, null);
			}
			for(Menu menu : tmpMenulist){
				if(mapMenu.containsKey(menu.getId())){
					mapMenu.put(menu.getId(), menu);
					menuChildList.add(menu);
				}
			}
		}
		return menuChildList;
	}
	
	public List<Menu> getMenuListLevel1(){
		if(mapMenuParentList == null){
			mapMenuParentList = new HashMap<Long, List<Menu>>();
		}
		if(mapMenuParentList.isEmpty()){
			menuList =  menuService.getParentMenu(1);	
			mapMenuParentList.put((long)1, menuList);
		} else{
			menuList =  mapMenuParentList.get(1);
		}
		return menuList;
	}
	
	
	
    /**
     * Redirect to login page.
     * @return Login page name.
     */
    public String redirectToLogin() {
        return "/login.xhtml?faces-redirect=true";
    }
     
    /**
     * Go to login page.
     * @return Login page name.
     */
    public String toLogin() {
        return "/login.xhtml";
    }
     
    /**
     * Redirect to info page.
     * @return Info page name.
     */
    public String redirectToInfo() {
        return "/home.xhtml?faces-redirect=true";
    }
     
    /**
     * Go to info page.
     * @return Info page name.
     */
    public String toInfo() {
        return "/home.xhtml";
    }
     
    /**
     * Redirect to welcome page.
     * @return Welcome page name.
     */
    public String redirectToHome() {
        return "/ui/Home.xhtml?faces-redirect=true";
    }
     
    /**
     * Go to welcome page.
     * @return Welcome page name.
     */
    public String toHome() {
        return "/ui/Home.xhtml";
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


	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}


	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getSearchMenu() {
		return searchMenu;
	}

	public void setSearchMenu(String searchMenu) {
		this.searchMenu = searchMenu;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isUseFlag() {
		return useFlag;
	}

	public void setUseFlag(boolean useFlag) {
		this.useFlag = useFlag;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}


	public List<SelectItem> getParentMenuCombobox() {
		return parentMenuCombobox;
	}

	public void setParentMenuCombobox(List<SelectItem> parentMenuCombobox) {
		this.parentMenuCombobox = parentMenuCombobox;
	}

	public boolean isDisableParentCombobox() {
		return disableParentCombobox;
	}

	public void setDisableParentCombobox(boolean disableParentCombobox) {
		this.disableParentCombobox = disableParentCombobox;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Employee getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Employee loginUser) {
		this.loginUser = loginUser;
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	public String getMessage() {
		Customer aCust = commonService.getOneCustomer();
		return aCust.getName();
	}
	
	public String moveToCreateCustPage() {
		return "createCustomer?faces-redirect=true";
	}
	
	public void countryLocaleCodeChanged(ValueChangeEvent e) {
		String newLocaleValue = e.getNewValue().toString();

		// loop country map to compare the locale code
		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(newLocaleValue)) {

				FacesContext.getCurrentInstance().getViewRoot()
						.setLocale((Locale) entry.getValue());

			}
		}
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
}
