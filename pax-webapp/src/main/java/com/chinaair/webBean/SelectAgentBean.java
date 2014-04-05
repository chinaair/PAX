package com.chinaair.webBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Agent;
import com.chinaair.services.AgentServiceBean;

@ConversationScoped
@Named("selectAgentBean")
public class SelectAgentBean implements Serializable {

	private static final long serialVersionUID = -4000495264925962145L;
	
	@EJB
	private AgentServiceBean agentService;
	
	private List<Agent> agentList;
	
	private Agent selectedAgent;
	
	@Inject
	private Conversation conversation;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		agentList = agentService.getAllAgent();
	}
	
	public void selectAgentFromDialog(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(selectedAgent);
	}

	public List<Agent> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<Agent> agentList) {
		this.agentList = agentList;
	}

	public Agent getSelectedAgent() {
		return selectedAgent;
	}

	public void setSelectedAgent(Agent selectedAgent) {
		this.selectedAgent = selectedAgent;
	}

}
