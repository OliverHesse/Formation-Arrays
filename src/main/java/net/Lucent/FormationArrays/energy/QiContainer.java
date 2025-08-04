package net.Lucent.FormationArrays.energy;

import net.neoforged.neoforge.capabilities.Capabilities;

import java.math.BigDecimal;

public class QiContainer{

    private BigDecimal energy = BigDecimal.ZERO;
    private final BigDecimal MAX_ENERGY;
    public final String qiType;
    public QiContainer(String qiType,BigDecimal max){
        this.qiType = qiType;
        this.MAX_ENERGY = max;
    }

    public void setQi(BigDecimal energy){
        this.energy = energy.min(MAX_ENERGY);
    }
    public BigDecimal getQi(){
        return energy;
    }
    public void addQi(BigDecimal energy){
        this.energy = MAX_ENERGY.min(this.energy.add(energy));
    }
    public void subQi(BigDecimal energy){
        this.energy = BigDecimal.ZERO.max(this.energy.subtract(energy));
    }

}
