package ClassWork.Behaviours.Consumer;

import ClassWork.AdditionalClasses.Data;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class SendQuantity extends WakerBehaviour {
    private Data data;

    public SendQuantity(Agent a, long timeout, Data data) {
        super(a, timeout);
        this.data = data;
    }

    @Override
    protected void onWake() {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setContent("20");
        msg.setProtocol("quantity");
        msg.addReceiver(data.getTopic());
        getAgent().send(msg);
    }
}
