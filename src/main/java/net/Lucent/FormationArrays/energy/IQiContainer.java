package net.Lucent.FormationArrays.energy;

import java.math.BigDecimal;

public interface IQiContainer {
    void setQi(BigDecimal energy);
    BigDecimal getQi();
    void addQi(BigDecimal energy);
    void subQi(BigDecimal energy);
}
