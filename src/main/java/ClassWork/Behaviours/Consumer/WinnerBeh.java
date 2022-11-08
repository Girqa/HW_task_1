package ClassWork.Behaviours.Consumer;

import ClassWork.AdditionalClasses.Data;
import jade.core.behaviours.OneShotBehaviour;

public class WinnerBeh extends OneShotBehaviour {
    private Data data;

    public WinnerBeh(Data data) {
        this.data = data;
    }

    @Override
    public void action() {
        int price1 = data.getAgentPrices().get(0).getPrice();
        int price2 = data.getAgentPrices().get(1).getPrice();
        if (price1 == price2) {
            System.out.println("Цены одинаковые, "+price1);
        } else if(price1 < price2) {
            System.out.println("Победитель "+data.getAgentPrices().get(0).getAgentName());
        } else {
            System.out.println("Победитель"+data.getAgentPrices().get(1).getAgentName());
        }
    }
}
