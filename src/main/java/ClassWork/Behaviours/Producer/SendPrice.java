package ClassWork.Behaviours.Producer;

import ClassWork.ConfigClass;
import ClassWork.Services.TopicHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SendPrice extends Behaviour {
    String topicName;
    String content;
    AID topic;
    ConfigClass cfg;
    boolean finish;
    MessageTemplate mt;

    public SendPrice(String topicName, ConfigClass cfg) {
        this.topicName = topicName;
        this.cfg = cfg;
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                MessageTemplate.MatchProtocol("quantity")
        );
    }

    @Override
    public void action() {
        topic = TopicHelper.createTopic(getAgent(), topicName);
        ACLMessage receive = getAgent().receive(mt);
        if (receive != null) {
            System.out.println(getAgent().getLocalName()+" SendPrice");
            System.out.println(getAgent().getLocalName() + ":" + receive.getContent());
            content = receive.getContent();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setProtocol("price");
            int price = cfg.getA()*Integer.parseInt(content) + cfg.getB();
            System.out.println(getAgent().getLocalName() + " price: " + price);
            msg.setContent(String.valueOf(price));
            msg.addReceiver(topic);
            getAgent().send(msg);
            finish = true;
        }
    }

    @Override
    public boolean done() {
        return finish;
    }
}
