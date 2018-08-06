package com.chinaair.webBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Menu;
import com.chinaair.services.MenuServiceBean;
import com.chinaair.util.JSFUtil;

@ConversationScoped
@Named("menuBean")
public class MenuBean implements Serializable {

	private static final long serialVersionUID = -4000495264925962145L;
	
	private static final String NEW = "0";
	
	private static final String VIEW = "1";
	
	private static final String EDIT = "2";
	
	@EJB
	private MenuServiceBean menuService;
	
	private List<Menu> menuList;
	
	private Menu selectedMenu;
	
	
	
	/*******Input Screen variables********/
	
	private String screenMode;
	
	
	private String menu;
	
	private String menu_vi;
	
	private String link;
	
	private String icon;
	
	private int level;
	
	private Long parentId;
	
	private List<SelectItem> parentMenuCombobox;
	private String parentMenu;
	
	private boolean disableParentCombobox;
	
	private boolean useFlag;
	
	private String searchMenu;
	
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
	}
	
	public void selectMenuFromDialog(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(searchMenu);
	}
	
	public void initValueItem() {
		setScreenMode(NEW);
		this.menu = "";
		this.menu_vi = "";
		this.level = 1;
		this.parentId = null;
		this.useFlag = true;
		this.link = "";
		this.icon = "";
		createValueComboboxParentMenu();
		setMenuList(menuService.getAllMenu());
	}
	private boolean checkError(Menu menu,boolean isUpdate){
		//check userID is exists
		List<Menu> list= menuService.findMenuByCondition(menu,isUpdate);
		if(list != null && !list.isEmpty()){
			return true;	
		}
		return false;
	}
	public String registerMenu(){
		Menu newMenu = new Menu();
		newMenu.setMenu(this.menu);
		newMenu.setMenu_vi(this.menu_vi);
		newMenu.setLevel(this.level);
		newMenu.setParentId(this.parentId);
		newMenu.setUseFlag(this.useFlag);
		if(level > 1){
			newMenu.setLink(this.link);	
		}
		newMenu.setIcon(this.icon);
		if(checkError(newMenu,false)) {
			JSFUtil.addInfo(bundle, null,null, "menu_error_is_exist");
			return "";
		}
		menuService.insert(newMenu);
		setMenuList(menuService.getAllMenu());
		JSFUtil.addInfo(bundle, null,null, "menu_saveSuccessfully");
		return "MenuListScreen?faces-redirect=true";
	}
	public String gotoNewMenuScreen() {
		initValueItem();
		return "MenuScreen?faces-redirect=true";
	}
	
	public String gotoViewMenuScreen() {
		if(selectedMenu == null) {
			JSFUtil.addError(bundle, null,null, "resource_error_selectRecord");
			return null;
		}
		
		if(selectedMenu != null) {
			this.menu = selectedMenu.getMenu();
			this.menu_vi = selectedMenu.getMenu_vi();
			this.level = selectedMenu.getLevel();
			createValueComboboxParentMenu();
			this.parentId = selectedMenu.getParentId();
			this.useFlag = selectedMenu.getUseFlag();
			this.parentMenu = selectedMenu.getParentMenu();
			this.link = selectedMenu.getLink();
			this.icon = selectedMenu.getIcon();
		}
		setScreenMode(VIEW);
		return "MenuScreen?faces-redirect=true";
	}
	
	public void editMenu() {
		setScreenMode(EDIT);
	}
	
	public String updateMenu() {
		selectedMenu.setMenu(this.menu);
		selectedMenu.setMenu_vi(this.menu_vi);
		selectedMenu.setLevel(this.level);
		selectedMenu.setParentId(this.parentId);
		selectedMenu.setUseFlag(this.useFlag);
		if(level > 1){
			selectedMenu.setLink(this.link);	
		}
		selectedMenu.setIcon(this.icon);
		if (checkError(selectedMenu, true)) {
			JSFUtil.addInfo(bundle, null,null, "employee_error_userId_is_exist");
			return "";
		}
		menuService.update(selectedMenu);
		selectedMenu = null;
		setMenuList(menuService.getAllMenu());
		JSFUtil.addInfo(bundle, null,null, "menu_updateSuccessfully");
		return "MenuListScreen?faces-redirect=true";
	}
	public String deleteMenu() {
		if(selectedMenu != null) {
			menuService.delete(selectedMenu);
			initValueItem();
			setMenuList(menuService.getAllMenu());
			JSFUtil.addInfo(bundle, null,null, "employee_deleteSuccessfully");
			return "EmployeeListScreen?faces-redirect=true";
		}else {
			JSFUtil.addError(bundle, null,null, "resource_error_selectRecord");
		}
		return "";
	}
	public String cancelRegisterMenu() {
		initValueItem();
		selectedMenu = null;
		setMenuList(menuService.getAllMenu());
		return "MenuListScreen?faces-redirect=true";
	}
	
	
	public void searchMenu() {
		setMenuList(menuService.findMenuByCondition(searchMenu));
	}
	public void refresh() {
		initValueItem();
	}

	public void createValueComboboxParentMenu(){
		parentMenuCombobox = new ArrayList<SelectItem>();
		if(this.level > 1){
			List<Menu> list = menuService.findParentMenu(level - 1);
			if(!list.isEmpty()){
				for(Menu menu : list){
					parentMenuCombobox.add(new SelectItem(menu.getId(),menu.getMenu()));
				}
				
			}	
		}
	}
	public void onchangeLevelCombobox(){
		createValueComboboxParentMenu();
	}
	public void onRowSelectMenu(){
		
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

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Menu getSelectedMenu() {
		return selectedMenu;
	}

	public void setSelectedMenu(Menu selectedMenu) {
		this.selectedMenu = selectedMenu;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getMenu_vi() {
		return menu_vi;
	}

	public void setMenu_vi(String menu_vi) {
		this.menu_vi = menu_vi;
	}


}
