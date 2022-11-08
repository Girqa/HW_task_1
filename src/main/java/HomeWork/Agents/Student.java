package HomeWork.Agents;

import ClassWork.AutoRunAgent;
import HomeWork.Additional.JadePatternProvider;
import HomeWork.Additional.TimeInterval;
import HomeWork.Behaviours.Student.ReceiveTopicName;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@AutoRunAgent(count = 4, name = "Student")
public class Student extends Agent {
    @Override
    protected void setup() {
        JadePatternProvider.registerYellowPage( this, "Student");
        TimeInterval times = null;
        try {
            JAXBContext context = JAXBContext.newInstance(TimeInterval.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            times = (TimeInterval) unmarshaller.unmarshal(new File(
                    "src/main/resources/" + getLocalName() + ".xml"
            ));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        addBehaviour(new ReceiveTopicName(times));
    }
}
