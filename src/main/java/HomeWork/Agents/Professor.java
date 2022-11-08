package HomeWork.Agents;

import ClassWork.AutoRunAgent;
import ClassWork.Behaviours.Consumer.WinnerBeh;
import HomeWork.Additional.TimeInterval;
import HomeWork.Behaviours.Professor.ProfessorFMSBeh;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@AutoRunAgent(count = 1, name = "Professor")
public class Professor extends Agent {
    @Override
    protected void setup() {
        TimeInterval times;
        try {
            JAXBContext context = JAXBContext.newInstance(TimeInterval.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File source = new File("src/main/resources/professor.xml");
            times = (TimeInterval) unmarshaller.unmarshal(source);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        addBehaviour(new WakerBehaviour(this, 15000L) {
            @Override
            protected void onWake() {
                addBehaviour(new ProfessorFMSBeh(times));
            }
        });
    }
}
