package HomeWork.Behaviours.Student;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class SendDiapason extends OneShotBehaviour {
    private List<Integer> diapason;
    AID topic;

    public SendDiapason(List<Integer> diapason, AID topic) {
        this.diapason = diapason;
        this.topic = topic;
    }

    @Override
    public void action() {
        ObjectMapper mapper = new ObjectMapper();
        String content = null;
        try {
            content = mapper.writeValueAsString(diapason);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("diapason");
        msg.setContent(content);
        msg.addReceiver(topic);
        getAgent().send(msg);  // Отправили свои времена
    }
}
