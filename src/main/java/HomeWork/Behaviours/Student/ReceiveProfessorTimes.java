package HomeWork.Behaviours.Student;

import ClassWork.Services.TopicHelper;
import HomeWork.Additional.TimeInterval;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ReceiveProfessorTimes extends Behaviour {
    private MessageTemplate mt;
    private TimeInterval ti;
    private String topicName;
    private AID topic;
    private boolean received = false;

    public ReceiveProfessorTimes(String topicName, TimeInterval times) {
        this.topicName = topicName;
        this.ti = times;
    }

    @Override
    public void onStart() {
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                MessageTemplate.MatchProtocol("professor times")
        );
        topic = TopicHelper.createTopic(getAgent(), topicName);
    }

    @Override
    public void action() {
        ACLMessage receive = getAgent().receive(mt);
        if (receive != null) {
            received = true;
            String content = receive.getContent();
            // Спарсили JSON
            ObjectMapper mapper = new ObjectMapper();
            TimeInterval professor = null;
            try {
                professor = mapper.readValue(content, TimeInterval.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (getAgent().getLocalName().endsWith("1")) {
                List<Integer> diapason = ti.intersection(professor);
                getAgent().addBehaviour(new SendDiapason(diapason, topic));
                getAgent().addBehaviour(new ReceiveDiapasons(topic));
                getAgent().addBehaviour(new ReceiveOffers(topic));
            } else {
                List<Integer> diapason = ti.intersection(professor);
                getAgent().addBehaviour(new SendDiapason(diapason, topic));
                getAgent().addBehaviour(new ReceiveDiapasons(topic));
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return received;
    }
}
