import HomeWork.Additional.TimeInterval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static org.junit.jupiter.api.Assertions.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimeIntervalTest{
    private int start;
    private int end;
    @BeforeEach
    public void init(){
        File file = new File("src/main/resources/professor.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            doc = dbf.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element root = doc.getDocumentElement();
        Node startNode = root.getElementsByTagName("start").item(0);
        Node endNode = root.getElementsByTagName("end").item(0);
        start = Integer.parseInt(startNode.getTextContent());
        end = Integer.parseInt(endNode.getTextContent());
    }

    @Test
    public void parseXml() {
        JAXBContext context = null;
        TimeInterval timeInterval = null;
        try {
            context = JAXBContext.newInstance(TimeInterval.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            timeInterval = (TimeInterval) unmarshaller.unmarshal(
                    new File("src/main/resources/professor.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        assertEquals(timeInterval.getStart(), start);
        assertEquals(timeInterval.getEnd(), end);
    }

    @Test
    public void times() {
        File config = new File("src/main/resources/professor.xml");
        TimeInterval ti = null;
        try {
            JAXBContext context = JAXBContext.newInstance(TimeInterval.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ti = (TimeInterval) unmarshaller.unmarshal(config);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        List<Integer> testList = new ArrayList<>();
        for (int i = start; i < end+1; i++) {
            testList.add(i);
        }
        assertIterableEquals(ti.getTimes(), testList);
    }

    @Test
    public void intersection() {
        TimeInterval ti1 = null;
        File cfg = new File("src/main/resources/professor.xml");
        try {
            JAXBContext context = JAXBContext.newInstance(TimeInterval.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ti1 = (TimeInterval) unmarshaller.unmarshal(cfg);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        TimeInterval ti2 = new TimeInterval();
        ti2.setStart(12);
        ti2.setEnd(17);

        assertIterableEquals(ti1.intersection(ti2), List.of(15, 16, 17));
    }
}
