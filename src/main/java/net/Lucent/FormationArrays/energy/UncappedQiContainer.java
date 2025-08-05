package net.Lucent.FormationArrays.energy;

import java.math.BigDecimal;

public class UncappedQiContainer implements IQiContainer{

    private BigDecimal energy = BigDecimal.ZERO;

    public final String qiType;
    public UncappedQiContainer(String qiType){
        this.qiType = qiType;
    }

    @Override
    public void setQi(BigDecimal energy){
        this.energy = energy;
    }
    @Override
    public BigDecimal getQi(){
        return energy;
    }

    @Override
    public void addQi(BigDecimal energy) {
        this.energy = this.energy.add(energy);
    }

    @Override
    public void subQi(BigDecimal energy){
        this.energy = BigDecimal.ZERO.max(this.energy.subtract(energy));
    }

    public void addQiWithCap(BigDecimal energy,BigDecimal cap){
        this.energy = cap.min(this.energy.add(energy));
    }
}
