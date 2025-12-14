package net.lucent.formation_arrays.formations.energy_containers;

import net.lucent.formation_arrays.api.cores.ICoreEnergyContainer;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CappedEnergyContainer implements ICoreEnergyContainer {

    private BigInteger currentEnergy = BigInteger.ZERO;
    private final BigInteger MAX_ENERGY;

    public CappedEnergyContainer(BigInteger maxEnergy) {
        MAX_ENERGY = maxEnergy;
    }


    @Override
    public BigInteger getCurrentEnergy() {
        return currentEnergy;
    }

    @Override
    public void increaseEnergy(BigInteger amount) {
        currentEnergy= currentEnergy.add(amount).min(MAX_ENERGY);
    }

    @Override
    public void increaseEnergy(int amount) {
        increaseEnergy(BigInteger.valueOf(amount));
    }

    @Override
    public boolean tryDecreaseEnergy(BigInteger amount) {
        if(BigInteger.ZERO.compareTo(currentEnergy.subtract(amount)) > 0)return false;
        currentEnergy = currentEnergy.subtract(amount);
        return true;
    }

    @Override
    public boolean tryDecreaseEnergy(int amount) {
        return tryDecreaseEnergy(BigInteger.valueOf(amount));
    }
}
