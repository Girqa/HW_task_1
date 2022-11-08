package HomeWork.Behaviours.Professor;

import ClassWork.AdditionalClasses.Data;
import HomeWork.Additional.TimeInterval;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;

public class SendProfessorTime extends WakerBehaviour {
    private Data data;
    private TimeInterval times;
    public SendProfessorTime(Agent a, long timeout, Data data, TimeInterval times) {
        super(a, timeout);
        this.data = data;
        this.times = times;
    }

    @Override
    protected void onWake() {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        ObjectMapper mapper = new ObjectMapper();
        String content = null;
        try {
            content = mapper.writeValueAsString(times);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        msg.setContent(content);
        msg.setProtocol("professor times");
        msg.addReceiver(data.getTopic());
        getAgent().send(msg);
        System.out.println("Professor sends: " + content);
    }
}
