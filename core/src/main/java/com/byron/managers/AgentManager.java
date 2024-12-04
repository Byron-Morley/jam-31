package com.byron.managers;

import com.byron.factories.AgentFactory;
import com.byron.interfaces.IAgentManager;
import com.byron.interfaces.IAgentService;
import com.byron.services.AgentService;

public class AgentManager implements IAgentManager {
    AgentFactory agentFactory;
    IAgentService agentService;

    public AgentManager() {
        this.agentFactory = new AgentFactory();
        this.agentService = new AgentService(this);
    }

    public AgentFactory getAgentFactory() {
        return agentFactory;
    }

    public IAgentService getAgentService() {
        return agentService;
    }
}
