package ClassWork.Behaviours.Consumer;

import ClassWork.AdditionalClasses.AgentPrice;
import ClassWork.AdditionalClasses.Data;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveAnswers extends Behaviour {
    private Data data;
    private int count = 0;
    private MessageTemplate mt;

    public ReceiveAnswers(Data data) {
        this.data = data;
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchProtocol("price")
        );
    }

    @Override
    public void action() {
        ACLMessage receive = getAgent().receive(mt);
        if (receive != null) {
            data.getAgentPrices().add(new AgentPrice(
                    Integer.parseInt(receive.getContent()),
                    receive.getSender().getLocalName()
            ));
            count++;
        }
    }

    @Override
    public boolean done() {
        return count == data.getNumberOfProducers();
    }
}
