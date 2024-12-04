package com.byron.interfaces;

import com.byron.factories.AgentFactory;

public interface IAgentManager {

    IAgentService getAgentService();

    AgentFactory getAgentFactory();
}
