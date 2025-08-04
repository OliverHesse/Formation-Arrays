package net.Lucent.FormationArrays.energy;

import java.math.BigDecimal;
import java.util.HashMap;

public class FormationCoreQiContainer {
    public final BigDecimal MAX_ENERGY;
    public HashMap<String,QiContainer> energyContainers = new HashMap<>();

    public FormationCoreQiContainer(BigDecimal MAX_ENERGY){
        this.MAX_ENERGY = MAX_ENERGY;
    }

    public QiContainer getQiContainer(String type){
        if(energyContainers.containsKey(type)) return energyContainers.get(type);

        energyContainers.put(type,new QiContainer(type,MAX_ENERGY));
        return energyContainers.get(type);
    }

}
