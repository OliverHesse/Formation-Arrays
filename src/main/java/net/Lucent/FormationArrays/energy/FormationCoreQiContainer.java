package net.Lucent.FormationArrays.energy;

import java.math.BigDecimal;
import java.util.HashMap;
//TODO i need to make a decision
//Do i want max qi to be for individual qi types or for all qi.
//as in if the container stores a max of 200 qi. this is 200 for any type of qi. or 200 of the sum of all qi
public class FormationCoreQiContainer {
    public final BigDecimal MAX_ENERGY;
    public HashMap<String, UncappedQiContainer> energyContainers = new HashMap<>();

    public FormationCoreQiContainer(BigDecimal MAX_ENERGY){
        this.MAX_ENERGY = MAX_ENERGY;
    }
    public void addQiContainer(String type){
        energyContainers.put(type,new UncappedQiContainer(type));
    }
    public UncappedQiContainer getQiContainer(String type){
        if(energyContainers.containsKey(type)) return energyContainers.get(type);
        addQiContainer(type);
        return energyContainers.get(type);
    }
    //TODO
    public BigDecimal getTotalQi(){
        return BigDecimal.ZERO;
    }
    //TODO
    public BigDecimal getQiOfType(String type){
        return BigDecimal.ZERO;
    }
    public void addQiOfType(String type,BigDecimal energy){
        UncappedQiContainer container = getQiContainer(type);
        container.addQiWithCap(energy,MAX_ENERGY.subtract(getTotalQi()));
    }
    public void subtractQiOfType(String type,BigDecimal energy){
        UncappedQiContainer container = getQiContainer(type);
        container.subQi(energy);
        if(container.getQi().compareTo(BigDecimal.ZERO) == 0){
            energyContainers.remove(type);
        }
    }

}
