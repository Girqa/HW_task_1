package HomeWork.Behaviours;

import ClassWork.Services.TopicHelper;
import HomeWork.Additional.JadePatternProvider;
import HomeWork.Additional.TimeInterval;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CorrectBestTime extends Behaviour {
    private MessageTemplate mt;
    private int studentsAmount;
    private int receivedMsgs;
    private TimeInterval professor;
    private AID topic;
    private boolean done = false;
    private Map<String, Integer> schedule;
    private Map<String, Integer> finalSchedule;
    private List<AID> students;

    public CorrectBestTime(AID topic, TimeInterval professor) {
        this.topic = topic;
        this.professor = professor;
    }

    @Override
    public void onStart() {
        schedule = new TreeMap<>();
        finalSchedule = new HashMap<>();
        mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchProtocol("best time")
        );
        studentsAmount = JadePatternProvider.getServiceProviders(getAgent(), "Student").size();
        students = JadePatternProvider.getServiceProviders(getAgent(), "Student");
        receivedMsgs = 0;
    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            String student = msg.getSender().getLocalName();
            String content = msg.getContent();
            schedule.put(student, Integer.parseInt(content));
        } else if (schedule.size() == students.size()) {
            String earliestStudent = earliestStudent(schedule);

        }
    }

    @Override
    public boolean done() {
        return done;
    }

    private String earliestStudent(Map<String, Integer> students) {
        String studentName = students.keySet().iterator().next();
        int minTime = students.get(studentName);
        for (Map.Entry<String, Integer> student: students.entrySet()) {
            if (student.getValue() != -1 && student.getValue() < minTime) {
                minTime = student.getValue();
                studentName = student.getKey();
            }
        }
        System.out.println("Earliest student is "+studentName);
        return studentName;
    }

    private void sendTimeInterval(List<AID> receivers, TimeInterval interval) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("new times");
        receivers.forEach(msg::addReceiver);
        ObjectMapper mapper = new ObjectMapper();
        String content = null;
        try {
            content = mapper.writeValueAsString(interval);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        msg.setContent(content);
        getAgent().send(msg);
    }
}

/*
@Override
    public void action() {
        ACLMessage msg = getAgent().receive(mt);
        if (msg != null) {
            System.out.println("Corrector received " + msg.getContent() + " from " + msg.getSender().getLocalName());
            String student = msg.getSender().getLocalName();
            String content = msg.getContent();
            if (receivedMsgs < studentsAmount-1 && !finalSchedule.containsKey(student)) {
                receivedMsgs += 1;
                if (Integer.parseInt(content) == -1) {
                    finalSchedule.put(student, Integer.parseInt(content));
                    System.out.println(student + " can't visit meeting");
                } else {
                    schedule.put(student, Integer.parseInt(content));
                }
            } else if (studentsAmount > 0 && !finalSchedule.containsKey(student)){
                // Выкидываем самого ранненго студента а профессору уменьшаем интервал
                schedule.put(student, Integer.parseInt(content));
                String earliestStudent = earliestStudent(schedule);
                finalSchedule.put(earliestStudent, schedule.get(earliestStudent));
                if (professor.getEnd() >= schedule.get(earliestStudent)+1) {
                    professor.setStart(schedule.get(earliestStudent) + 1);  // Вычеркнули время саммого раннего
                }
                schedule.remove(earliestStudent);
                ACLMessage newIntervals = new ACLMessage(ACLMessage.INFORM);
                newIntervals.setProtocol("new times");
                newIntervals.addReceiver(topic);
                ObjectMapper mapper = new ObjectMapper();
                String interval = null;
                try {
                    interval = mapper.writeValueAsString(professor);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                newIntervals.setContent(interval);
                System.out.println("New interval: "+interval);
                System.out.println("Search next earliest...");
                schedule.clear();
                studentsAmount -= 1;
                receivedMsgs = 0;
                getAgent().send(newIntervals);
            } else if (studentsAmount == 0){
                ObjectMapper mapper = new ObjectMapper();
                String resultContent = null;
                try {
                    resultContent = mapper.writeValueAsString(finalSchedule);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ACLMessage result = new ACLMessage(ACLMessage.CONFIRM);
                result.setProtocol("schedule");
                result.addReceiver(topic);
                result.setContent(resultContent);
                getAgent().send(result);
                System.out.println("Resulting Schedule is "+resultContent);
                done = true;
            }
        } else {
            block();
        }
    }
 */