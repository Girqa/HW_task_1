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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiveDiapasons extends Behaviour {
    private AID topic;
    private int studentsAmount;
    private MessageTemplate mt;
    private Map<String, List<Integer>> diapasons;
    private boolean done;
    public ReceiveDiapasons(AID topic) {
        this.topic = topic;
        diapasons = new HashMap<>();
        done = false;
    }

    @Override
    public void onStart() {
        studentsAmount = JadePatternProvider.getServiceProviders(getAgent(), "Student").size();
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchProtocol("diapason")
        );
    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            String student = msg.getSender().getLocalName();
            String content = msg.getContent();

            ObjectMapper mapper = new ObjectMapper();
            List<Integer> diapason = null;
            try {
                diapason = mapper.readValue(content, new TypeReference<List<Integer>>(){});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Записали студента и его времена в словарь
            diapasons.put(student, diapason);
            studentsAmount--;
            // Если собрали времена всех студентов -> передаем обработчику
            if (studentsAmount == 0) {
                System.out.printf("%s collected %s\n", getAgent().getLocalName(), diapasons.toString());
                getAgent().addBehaviour(new SendOffer(diapasons, topic));
                done = true;
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return done;
    }
}
