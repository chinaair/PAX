package com.chinaair.webBean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.chinaair.entity.Customer;
import com.chinaair.entity.Employee;
import com.chinaair.entity.Master;
import com.chinaair.entity.Menu;
import com.chinaair.services.CommonServiceBean;
import com.chinaair.services.EmployeeServiceBean;
import com.chinaair.services.MasterServiceBean;
import com.mysql.jdbc.StringUtils;

@SessionScoped
@Named("loginBean")
public class LoginBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private MainBean mainBean;
	
	@EJB
	private EmployeeServiceBean employeeService;

	@EJB
	private CommonServiceBean commonService;
	
	@EJB
	private MasterServiceBean masterService;
	
	private String localeCode;
	
	private boolean loggedIn;
	
	private Locale language;
	
	private ResourceBundle bundle;
	
	private Employee loginUser;
	
	private Map<Long,Menu> mapMenuList;
	
	private String userName;
	
	private String password;
	
	private String systemClass;
	
	private String rssLink;
	
	private String calendarLink;
	
	private Boolean sessionEnded; 
	
	@PostConstruct
	public void init() {
		language = new Locale("en");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(language);
		setBundle(ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle"));
		systemClass = "CI";
		List<Master> settingMasters = masterService.findByMasterParent("0001");
		for(Master m : settingMasters) {
			if("0005".equals(m.getMasterNo())) {//system class
				systemClass = m.getValue();
			}
			if("0006".equals(m.getMasterNo())) {
				rssLink = m.getValue();
			}
			if("0007".equals(m.getMasterNo())) {
				calendarLink = m.getValue();
			}
		}
		loginUser = null;
	}
	
	public void initScreen() {
		if(Boolean.TRUE.equals(sessionEnded)) {
			FacesMessage msg = new FacesMessage("Session ended! Please login again.", "INFO MSG");
	        msg.setSeverity(FacesMessage.SEVERITY_INFO);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        sessionEnded = false;
		}
	}
	
	public String login(){
		
		if(!StringUtils.isNullOrEmpty(this.userName)
				&& !StringUtils.isNullOrEmpty(this.password)){
			loginUser = employeeService.login(userName, password);
			if(loginUser != null){
				loggedIn = true;
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getSessionMap().put("loginUser", loginUser);
				return "/ui/Home.xhtml?faces-redirect=true";
			} else{
				 // Set login ERROR
		        FacesMessage msg = new FacesMessage("login_err_user_pass_incorrect", "ERROR MSG");
		        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		        FacesContext.getCurrentInstance().addMessage(null, msg);
		         
		        // To to login page
		       // JSFUtil.addInfo(bundle, null, null, "login_err_user_pass_incorrect");
		        return "";
				
			}
		}
		return "";
	}
	public String logout() {
        // Set the paremeter indicating that user is logged in to false
         
        // Set logout message
        FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        mainBean.resetMenu();
        this.loggedIn = false;
        this.loginUser = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
	 /**
     * Logout operation.
     * @return
     */
    
    public String doLogin() {
        return "/login.xhtml?faces-redirect=true";
    }
    
    public String doSystem() {
    	return "/ui/Home.xhtml?faces-redirect=true";
    }
	@PreDestroy
	public void destroy() {
		
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
		language = new Locale(newLocaleValue);
		FacesContext.getCurrentInstance().getViewRoot()
				.setLocale(language);
	}
	
	public String getLocaleCode() {
		return localeCode;
	}
 
 
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public MainBean getMainBean() {
		return mainBean;
	}

	public void setMainBean(MainBean mainBean) {
		this.mainBean = mainBean;
	}

	@Produces
	public Employee getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Employee loginUser) {
		this.loginUser = loginUser;
	}

	public Map<Long, Menu> getMapMenuList() {
		return mapMenuList;
	}

	public void setMapMenuList(Map<Long, Menu> mapMenuList) {
		this.mapMenuList = mapMenuList;
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

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Locale getLanguage() {
		return language;
	}

	public void setLanguage(Locale language) {
		this.language = language;
	}

	public String getSystemClass() {
		return systemClass;
	}

	public void setSystemClass(String systemClass) {
		this.systemClass = systemClass;
	}

	public String getRssLink() {
		return rssLink;
	}

	public void setRssLink(String rssLink) {
		this.rssLink = rssLink;
	}

	public String getCalendarLink() {
		return calendarLink;
	}

	public void setCalendarLink(String calendarLink) {
		this.calendarLink = calendarLink;
	}

	public Boolean getSessionEnded() {
		return sessionEnded;
	}

	public void setSessionEnded(Boolean sessionEnded) {
		this.sessionEnded = sessionEnded;
	}

}
