package HomeWork.Behaviours.Professor;

import ClassWork.AdditionalClasses.Data;
import HomeWork.Additional.TimeInterval;
import jade.core.behaviours.FSMBehaviour;

public class ProfessorFMSBeh extends FSMBehaviour {
    public ProfessorFMSBeh(TimeInterval times) {
        Data data = new Data();

        registerFirstState(new SendTopicName(data), "firstState");
        registerState(new SendProfessorTime(getAgent(), 1000L, data, times), "secondState");
        registerState(new ReceiveParallelBeh(getAgent(), data), "thirdState");
        registerLastState(new PrepareSchedule(data), "lastState");

        registerDefaultTransition("firstState", "secondState");
        registerDefaultTransition("secondState", "thirdState");
        registerDefaultTransition("thirdState", "lastState");
    }
}
