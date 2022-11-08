package HomeWork.Behaviours.Student;

import HomeWork.Additional.TimeInterval;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveTopicName extends Behaviour {
    private TimeInterval times;
    private MessageTemplate mt;

    public ReceiveTopicName(TimeInterval times) {
        this.times = times;
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
        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            System.out.println(getAgent().getLocalName()+" "+msg.getContent());
            getAgent().addBehaviour(new ReceiveProfessorTimes(msg.getContent(), times));
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
