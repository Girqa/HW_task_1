package HomeWork.Behaviours.Professor;

import ClassWork.AdditionalClasses.Data;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;

public class ReceiveParallelBeh extends ParallelBehaviour {
    public ReceiveParallelBeh(Agent a, Data data) {
        super(a, WHEN_ANY);
        addSubBehaviour(new ReceiveStudentsTime(data));
        addSubBehaviour(new WakerBehaviour(getAgent(), 2000L) {
            @Override
            protected void onWake() {
                System.out.println("Время ожидания истекло");
            }
        });
    }
}
