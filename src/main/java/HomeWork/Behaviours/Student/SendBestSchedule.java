package HomeWork.Behaviours.Student;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SendBestSchedule extends OneShotBehaviour {
    private AID topic;
    private List<Map<String, Integer>> offers;
    public SendBestSchedule(AID topic, List<Map<String, Integer>> offers) {
        this.topic = topic;
        this.offers = offers;
    }

    @Override
    public void action() {
        Map<String, Integer> bestOffer = getBestOne(offers);

        ObjectMapper mapper = new ObjectMapper();
        String content = null;
        try {
            content = mapper.writeValueAsString(bestOffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
         msg.setProtocol("best schedule");
         msg.setContent(content);
         msg.addReceiver(topic);
         getAgent().send(msg);
         System.out.printf("Best schedule is - %s\n", content);
    }

    private Map<String, Integer> getBestOne(List<Map<String, Integer>> map) {
        // Сортировка от меньшего к большему
        map.sort(new Comparator<Map<String, Integer>>() {
            @Override
            public int compare(Map<String, Integer> o1, Map<String, Integer> o2) {
                long c1 = o1.values().stream().filter(a -> a==-1).count();
                long c2 = o2.values().stream().filter(a -> a==-1).count();
                if (c1 > c2) {
                    return 1;
                } else if(c1 == c2) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return map.get(0);
    }
}
