package net.Lucent.FormationArrays.energy;

import java.math.BigDecimal;

public class CappedQiContainer implements IQiContainer {

    private BigDecimal energy = BigDecimal.ZERO;
    private final BigDecimal MAX_ENERGY;
    public final String qiType;
    public CappedQiContainer(String qiType, BigDecimal max){
        this.qiType = qiType;
        this.MAX_ENERGY = max;
    }

    @Override
    public void setQi(BigDecimal energy){
        this.energy = energy.min(MAX_ENERGY);
    }
    @Override
    public BigDecimal getQi(){
        return energy;
    }
    @Override
    public void addQi(BigDecimal energy){
        this.energy = MAX_ENERGY.min(this.energy.add(energy));
    }
    @Override
    public void subQi(BigDecimal energy){
        this.energy = BigDecimal.ZERO.max(this.energy.subtract(energy));
    }

}
