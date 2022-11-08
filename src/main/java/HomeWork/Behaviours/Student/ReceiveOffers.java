package HomeWork.Behaviours.Student;

import HomeWork.Additional.JadePatternProvider;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReceiveOffers extends Behaviour {
    private AID topic;
    private List<Map<String, Integer>> offers;
    private MessageTemplate mt;
    private boolean got;
    private int studentsAmount;
    public ReceiveOffers(AID topic) {
        this.topic = topic;
    }

    @Override
    public void onStart() {
        got = false;
        offers = new ArrayList<>();
        studentsAmount = JadePatternProvider.getServiceProviders(getAgent(), "Student").size();
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchProtocol("offer")
        );
    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            studentsAmount--;
            Map<String, Integer> offer = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                offer = mapper.readValue(msg.getContent(), new TypeReference<Map<String, Integer>>(){});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            offers.add(offer);

            if (studentsAmount == 0) {
                getAgent().addBehaviour(new SendBestSchedule(topic, offers));
                got = false;
            }
        }
    }

    @Override
    public boolean done() {
        return got;
    }
}
