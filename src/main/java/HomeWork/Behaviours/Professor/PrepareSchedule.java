package HomeWork.Behaviours.Professor;

import ClassWork.AdditionalClasses.Data;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PrepareSchedule extends OneShotBehaviour {
    private Data data;

    public PrepareSchedule(Data data) {
        this.data = data;
    }

    @Override
    public void action() {
        Map<String, Integer> schedule = data.getBestOffer();
        List<String> content = new ArrayList<>();

        for (String key: schedule.keySet()) {
            content.add(schedule.get(key) + " - " + key);
        }

        Collections.sort(content);
        System.out.println(String.join("\n", content));
    }
}
