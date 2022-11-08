package ClassWork.AdditionalClasses;

public class AgentPrice {
    private int price;
    private String agentName;

    public AgentPrice(int price, String agentName) {
        this.price = price;
        this.agentName = agentName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
