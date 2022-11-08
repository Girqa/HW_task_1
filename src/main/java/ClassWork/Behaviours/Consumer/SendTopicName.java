package ClassWork.Behaviours.Consumer;

import ClassWork.AdditionalClasses.Data;
import ClassWork.Services.TopicHelper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class SendTopicName extends OneShotBehaviour {
    private Data data;
    private List<AID> agents;

    public SendTopicName(Data data) {
        this.data = data;
    }

    @Override
    public void onStart() {
        agents = new ArrayList<>();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Producer");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(getAgent(), dfd);
            for (DFAgentDescription res: result) {
                agents.add(res.getName());
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        data.setNumberOfProducers(agents.size());
    }

    @Override
    public void action() {
        AID topic = TopicHelper.createTopic(getAgent(), "topic");
        data.setTopic(topic);
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("topic name");
        for (AID agent: agents) {
            msg.addReceiver(agent);
        }
        msg.setContent("topic");
        getAgent().send(msg);
    }
}
