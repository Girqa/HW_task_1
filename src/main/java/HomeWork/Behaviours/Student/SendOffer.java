package HomeWork.Behaviours.Student;

import HomeWork.Additional.JadePatternProvider;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SendOffer extends OneShotBehaviour {
    private Map<String, List<Integer>> diapasons;
    private AID topic;
    private List<String> students;
    public SendOffer(Map<String, List<Integer>> diapasons, AID topic) {
        this.diapasons = diapasons;
        this.topic = topic;
    }

    @Override
    public void onStart() {
        students = JadePatternProvider.getServiceProviders(getAgent(), "Student")
                .stream()
                .map(AID::getLocalName)
                .collect(Collectors.toList());
        sortStudents(students);
    }

    @Override
    public void action() {
        System.out.printf("%s -> %s\n",getAgent().getLocalName(), students);
        List<Integer> bookedTimes = new ArrayList<>();
        Map<String, Integer> schedule = new HashMap<>();
        // Записали лучшее время студента
        String curStudent = students.get(0);
        schedule.put(curStudent, minTime(diapasons.get(curStudent)));
        // Забронировали за ним время
        bookedTimes.add(schedule.get(curStudent));
        // Раскидали оставшихся агентов по принципу лучшее время в приоритете
        for (int i = 1; i < students.size(); i++) {
            String studentName = students.get(i);
            List<Integer> diapason = diapasons.get(studentName);
            diapason.removeAll(bookedTimes);

            Integer bestTime =  minTime(diapason);
            schedule.put(studentName, bestTime);
            bookedTimes.add(bestTime);
        }

        // Сформировали сообщение
        ObjectMapper mapper = new ObjectMapper();
        String content = null;
        try {
            content = mapper.writeValueAsString(schedule);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Шлем в топик
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("offer");
        msg.addReceiver(topic);
        msg.setContent(content);
        getAgent().send(msg);
        System.out.printf("%s offer %s\n", getAgent().getLocalName(),content);
    }

    private void sortStudents(List<String> list) {
        String currentStudent = getAgent().getLocalName();
        int distance = list.size() - list.indexOf(currentStudent);
        Collections.rotate(list, distance);
    }

    private Integer minTime(List<Integer> diapason) {
        Optional<Integer> min = diapason.stream().min((o1, o2) -> {
            if (o1 < o2 && o1 != -1){
                return -1;
            } else if (o1 == o2 && o1 != -1) {
                return 0;
            } else {
                return 1;
            }
        });
        return min.isPresent() ? min.get() : -1;
    }
}
