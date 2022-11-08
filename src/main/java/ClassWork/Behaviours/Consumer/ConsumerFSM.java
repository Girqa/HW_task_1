package ClassWork.Behaviours.Consumer;

import ClassWork.AdditionalClasses.Data;
import jade.core.behaviours.FSMBehaviour;

public class ConsumerFSM extends FSMBehaviour {
    public ConsumerFSM() {
        Data data = new Data();

        registerFirstState(new SendTopicName(data), "firstState");
        registerState(new SendQuantity(getAgent(), 1000, data), "secondState");
        registerState(new ReceiveParallelBeh(getAgent(), data), "thirdState");
        registerLastState(new WinnerBeh(data), "lastState");

        registerDefaultTransition("firstState", "secondState");
        registerDefaultTransition("secondState", "thirdState");
        registerDefaultTransition("thirdState", "lastState");
    }
}
