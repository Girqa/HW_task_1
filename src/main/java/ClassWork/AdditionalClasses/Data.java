package ClassWork.AdditionalClasses;

import jade.core.AID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {
    private AID topic;
    private int numberOfProducers;
    private List<AgentPrice> agentPrices = new ArrayList<>();
    private Map<String, Integer> bestOffer;
    public AID getTopic() {
        return topic;
    }

    public void setTopic(AID topic) {
        this.topic = topic;
    }

    public int getNumberOfProducers() {
        return numberOfProducers;
    }

    public void setNumberOfProducers(int numberOfProducers) {
        this.numberOfProducers = numberOfProducers;
    }

    public List<AgentPrice> getAgentPrices() {
        return agentPrices;
    }

    public void setAgentPrices(List<AgentPrice> agentPrices) {
        this.agentPrices = agentPrices;
    }

    public Map<String, Integer> getBestOffer() {
        return bestOffer;
    }

    public void setBestOffer(Map<String, Integer> bestOffer) {
        this.bestOffer = bestOffer;
    }
}
