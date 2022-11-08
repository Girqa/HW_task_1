package ClassWork.Agents;

import ClassWork.AutoRunAgent;
import ClassWork.Behaviours.Consumer.ConsumerFSM;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

@AutoRunAgent(count = 1, name = "Consumer")
public class Consumer extends Agent {
    @Override
    protected void setup() {
        System.out.println("Consumer started!");
        addBehaviour(new WakerBehaviour(this, 15000) {
            @Override
            protected void onWake() {
                getAgent().addBehaviour(new ConsumerFSM());
            }
        });
    }
}
