package com.chinaair.webBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Master;
import com.chinaair.services.MasterServiceBean;
import com.chinaair.util.JSFUtil;

@ConversationScoped
@Named("masterBean")
public class MasterBean implements Serializable {

	private static final long serialVersionUID = -4000495264925962145L;
	
	private static final String NEW = "1";
	
	private static final String EDIT = "2";
	
	@EJB
	private MasterServiceBean masterService;
	
	private List<Master> masterList;
	
	private Master selectedMaster;
	
	
	
	/*******Input Screen variables********/
	
	private String screenMode;
	
	
	private String masterNo;
	
	private String masterParent;
	
	private String name;
	
	private String value;
	
	private int level;
	
	
	private List<Master> parentMasterCombobox;
	
	private boolean disableParentCombobox;
	
	private boolean useFlag;
	
	private Master searchMaster;
	
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
		setBundle(ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle"));
		initValueItem();
	}
	
	public void selectMasterFromDialog(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(searchMaster);
	}
	
	public void initValueItem() {
		this.masterNo ="";
		this.name = "";
		this.value = "";
		this.level = 1;
		searchMaster = new Master();
		//this.masterParent = "";
		setScreenMode(NEW);
		createValueComboboxParentMaster();
		setMasterList(masterService.getAllMaster(false));
	}
	private boolean checkError(Master master,boolean isUpdate){
		//check userID is exists
		List<Master> list= masterService.findMasterByCondition(master, isUpdate);
		if(list != null && !list.isEmpty()){
			return true;	
		}
		return false;
	}
	public String registerMaster(){
		Master newMaster = new Master();
		newMaster.setLevel(new Long(this.level));
		newMaster.setMasterNo(this.masterNo);
		newMaster.setMasterParent(this.masterParent);
		newMaster.setName(this.name);
		newMaster.setValue(this.value);
		if(checkError(newMaster,false)) {
			JSFUtil.addError(bundle, null,null, "master_error_is_exist");
			return "";
		}
		masterService.insert(newMaster);
		setMasterList(masterService.getAllMaster(false));
		JSFUtil.addInfo(bundle, null,null, "master_saveSuccessfully");
		initValueItem();
		return "MasterInputScreen?faces-redirect=true";
	}
	public String gotoNewMasterScreen() {
		initValueItem();
		return "MasterInputScreen?faces-redirect=true";
	}
	
	public String onRowSelectMaster() {
		if(selectedMaster == null) {
			JSFUtil.addError(bundle, null,null, "resource_error_selectRecord");
			return null;
		}
		
		if(selectedMaster != null) {
			if(selectedMaster.getLevel()!=null){
				this.level = selectedMaster.getLevel().intValue();	
			}else{
				this.level = 0;
			}
			this.masterNo = selectedMaster.getMasterNo();
			this.value = selectedMaster.getValue();
			this.name = selectedMaster.getName();
			createValueComboboxParentMaster();
			this.masterParent = selectedMaster.getMasterParent();
		}
		screenMode = EDIT;
		return "";
	}
	
	
	public String updateMaster() {
		Master master = new Master();
		master.setMasterNo(this.masterNo);
		master.setLevel(new Long(this.level));
		master.setName(this.name);
		master.setMasterParent(this.masterParent);
		master.setValue(this.value);
		if (checkError(master, true)) {
			JSFUtil.addError(bundle, null,null, "master_error_is_exist");
			return "";
		}
		masterService.update(master);
		selectedMaster = null;
		setMasterList(masterService.getAllMaster(false));
		JSFUtil.addInfo(bundle, null,null, "master_updateSuccessfully");
		initValueItem();
		return "";
	}
	public String deleteMaster() {
		if(selectedMaster != null) {
			masterService.delete(selectedMaster);
			initValueItem();
			setMasterList(masterService.getAllMaster(false));
			JSFUtil.addInfo(bundle, null,null, "master_deleteSuccessfully");
		}else {
			JSFUtil.addError(bundle, null,null, "resource_error_selectRecord");
		}
		return "";
	}
	public String clearMaster() {
		selectedMaster = null;
		initValueItem();
		return "MenuListScreen?faces-redirect=true";
	}
	
	
	public void searchMaster() {
		setMasterList(masterService.searchMaster(searchMaster));
	}
	public String refresh() {
		initValueItem();
		return "MasterInputScreen.xhtml?faces-redirect=true";
	}

	public void createValueComboboxParentMaster(){
		parentMasterCombobox = new ArrayList<Master>();
		if(this.level > 1){
			parentMasterCombobox = masterService.findParentMaster(new Long(level) - 1);
		}
	}
	public void onchangeLevelCombobox(){
		createValueComboboxParentMaster();
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	public boolean isUseFlag() {
		return useFlag;
	}

	public void setUseFlag(boolean useFlag) {
		this.useFlag = useFlag;
	}

	
	public boolean isDisableParentCombobox() {
		return disableParentCombobox;
	}

	public void setDisableParentCombobox(boolean disableParentCombobox) {
		this.disableParentCombobox = disableParentCombobox;
	}


	/**
	 * @return the masterNo
	 */
	public String getMasterNo() {
		return masterNo;
	}

	/**
	 * @param masterNo the masterNo to set
	 */
	public void setMasterNo(String masterNo) {
		this.masterNo = masterNo;
	}

	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the parentMasterCombobox
	 */
	public List<Master> getParentMasterCombobox() {
		return parentMasterCombobox;
	}

	/**
	 * @param parentMasterCombobox the parentMasterCombobox to set
	 */
	public void setParentMasterCombobox(List<Master> parentMasterCombobox) {
		this.parentMasterCombobox = parentMasterCombobox;
	}


	/**
	 * @return the searchMaster
	 */
	public Master getSearchMaster() {
		return searchMaster;
	}

	/**
	 * @param searchMaster the searchMaster to set
	 */
	public void setSearchMaster(Master searchMaster) {
		this.searchMaster = searchMaster;
	}

	/**
	 * @return the masterList
	 */
	public List<Master> getMasterList() {
		return masterList;
	}

	/**
	 * @param masterList the masterList to set
	 */
	public void setMasterList(List<Master> masterList) {
		this.masterList = masterList;
	}

	/**
	 * @return the selectedMaster
	 */
	public Master getSelectedMaster() {
		return selectedMaster;
	}

	/**
	 * @param selectedMaster the selectedMaster to set
	 */
	public void setSelectedMaster(Master selectedMaster) {
		this.selectedMaster = selectedMaster;
	}

	/**
	 * @return the masterParent
	 */
	public String getMasterParent() {
		return masterParent;
	}

	/**
	 * @param masterParent the masterParent to set
	 */
	public void setMasterParent(String masterParent) {
		this.masterParent = masterParent;
	}

	/**
	 * @return the bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * @param bundle the bundle to set
	 */
	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}


}
