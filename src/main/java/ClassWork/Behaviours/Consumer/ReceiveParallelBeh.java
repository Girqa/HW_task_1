package ClassWork.Behaviours.Consumer;

import ClassWork.AdditionalClasses.Data;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;

public class ReceiveParallelBeh extends ParallelBehaviour {
    private Data data;

    public ReceiveParallelBeh(Agent a, Data data) {
        super(a, WHEN_ANY);
        this.data = data;

        addSubBehaviour(new ReceiveAnswers(data));
        addSubBehaviour(new WakerBehaviour(getAgent(), 2000) {
            @Override
            protected void onWake() {
                System.out.println("Время ожидания ответов истекло");
            }
        });
    }

}
