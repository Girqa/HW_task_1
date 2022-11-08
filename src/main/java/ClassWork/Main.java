package ClassWork;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import jade.util.leap.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Reflections r = new Reflections("HomeWork");

        Map<String, String> agents = new HashMap<>();

        Set<Class<?>> classes = r.getTypesAnnotatedWith(AutoRunAgent.class);
        for (Class<?> annotatedClass: classes) {
            AutoRunAgent anno = annotatedClass.getAnnotation(AutoRunAgent.class);
            System.out.println(annotatedClass.getPackageName());
            for (int i = 0; i < anno.count(); i++) {
                System.out.println(anno.name());
                agents.put(anno.name() + (i + 1), "HomeWork.Agents."+anno.name());
            }
        }
        Properties pp = parseCmdLineArgs(agents);
        ProfileImpl p = new ProfileImpl(pp);
        Runtime.instance().setCloseVM(true);
        Runtime.instance().createMainContainer(p);
    }

    private static Properties parseCmdLineArgs(Map<String, String> createdAgents) {
        Properties props = new Properties();
        props.setProperty("gui", "true");
        props.setProperty("services",
                "jade.core.messaging.TopicManagementService;" +
                        "jade.core.event.NotificationService;");

        StringBuilder agents = new StringBuilder();

        for (Map.Entry<String, String> entry: createdAgents.entrySet()) {
            agents.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        props.setProperty("agents", agents.toString());
        return props;
    }
}
