package ClassWork.Agents;

import ClassWork.AutoRunAgent;
import ClassWork.Behaviours.Producer.ReceiveTopicName;
import ClassWork.Behaviours.Producer.SendPrice;
import ClassWork.ConfigClass;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@AutoRunAgent(count = 2, name = "Producer")
public class Producer extends Agent {

    @Override
    protected void setup() {
        System.out.println(getLocalName()+" started!");
        registration();
        ConfigClass cfg = null;
        String cfgName = null;
        switch (getLocalName()) {
            case "Producer1":
                cfgName = "cfg1.xml";
                break;
            case "Producer2":
                cfgName = "cfg2.xml";
                break;
        }

        try {
            JAXBContext context = JAXBContext.newInstance(ConfigClass.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            cfg = (ConfigClass) jaxbUnmarshaller.unmarshal(
                    new File("src/main/resources/" + cfgName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        addBehaviour(new ReceiveTopicName(cfg));
    }

    public void registration() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Producer");
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
