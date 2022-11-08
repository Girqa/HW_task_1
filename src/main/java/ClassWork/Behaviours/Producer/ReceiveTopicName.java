package ClassWork.Behaviours.Producer;

import ClassWork.ConfigClass;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveTopicName extends Behaviour {
    private ConfigClass cfg;
    private MessageTemplate mt;
    public ReceiveTopicName(ConfigClass cfg) {
        this.cfg = cfg;
    }

    @Override
    public void onStart() {
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchProtocol("topic name")
        );
    }

    @Override
    public void action() {
        ACLMessage receive = getAgent().receive(mt);
        if (receive != null) {
            System.out.println(getAgent().getLocalName()+" "+receive.getContent());
            getAgent().addBehaviour(new SendPrice(receive.getContent(), cfg));
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
