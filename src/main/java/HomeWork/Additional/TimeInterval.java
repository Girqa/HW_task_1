package HomeWork.Additional;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cfg")
public class TimeInterval {
    @XmlElement
    private int start;
    @XmlElement
    private int end;
    @JsonIgnore
    private List<Integer> times;

    public List<Integer> getTimes() {
        if (times == null) {
            times = new ArrayList<>();
            for (int i = start; i < end+1; i++) {
                times.add(i);
            }
        }
        return times;
    }

    public List<Integer> intersection(TimeInterval t2) {
        getTimes().retainAll(t2.getTimes());
        return times;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}