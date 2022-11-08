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

public class SendBestTime extends Behaviour {
    private TimeInterval times;
    private TimeInterval professor;
    private MessageTemplate mt;
    private AID topic;
    private String content;

    public SendBestTime(AID topic, TimeInterval professor, TimeInterval times) {
        this.topic = topic;
        this.times = times;
        this.professor = professor;
    }

    @Override
    public void onStart() {
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchProtocol("new times")
        );
        List<Integer> intersection = times.intersection(professor);
        if (intersection.size() > 0) {
            sendBest(intersection.get(0).toString());
        } else {
            sendBest("-1");
        }
    }

    @Override
    public void action() {
        ACLMessage receive = getAgent().receive(mt);
        if (receive != null) {
            System.out.println(getAgent().getLocalName()+" SendBestTime");
            System.out.println(getAgent().getLocalName()+":"+receive.getContent());
            content = receive.getContent();
            // Спарсили JSON
            ObjectMapper mapper = new ObjectMapper();
            TimeInterval professor = null;
            try {
                professor = mapper.readValue(content, TimeInterval.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Получили доступные времена
            List<Integer> intersectTimes = times.intersection(professor);
            // Отправляем в чат лучшее время
            if (intersectTimes.size() > 0) {
                sendBest(String.valueOf(intersectTimes.get(0)));
            } else {
                sendBest("-1");
            }
        }

    }

    @Override
    public boolean done() {
        return false;
    }

    private void sendBest(String content) {
        ACLMessage bestTime = new ACLMessage(ACLMessage.INFORM);
        bestTime.setProtocol("best time");
        bestTime.setContent(content);
        System.out.println(getAgent().getLocalName() + " sends " + bestTime.getContent());
        bestTime.addReceiver(topic);
        getAgent().send(bestTime);
    }
}
