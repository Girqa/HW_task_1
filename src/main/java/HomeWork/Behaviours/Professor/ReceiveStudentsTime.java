package HomeWork.Behaviours.Professor;

import ClassWork.AdditionalClasses.Data;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public class ReceiveStudentsTime extends Behaviour {
    private Data data;
    private MessageTemplate mt;
    private boolean done = false;

    public ReceiveStudentsTime(Data data) {
        this.data = data;
    }

    @Override
    public void onStart() {
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                MessageTemplate.MatchProtocol("best schedule")
        );
    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            Map<String, Integer> schedule = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                schedule = mapper.readValue(msg.getContent(), new TypeReference<Map<String, Integer>>(){});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Professor got best schedule");
            data.setBestOffer(schedule);
            done = true;
        }

    }

    @Override
    public boolean done() {
        return done;
    }
}
