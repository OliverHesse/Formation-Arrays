package net.lucent.formation_arrays.formations.energy_containers;

import net.lucent.formation_arrays.api.cores.ICoreEnergyContainer;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CappedEnergyContainer implements ICoreEnergyContainer {

    private int currentEnergy = 0;
    private final int MAX_ENERGY;

    public CappedEnergyContainer(int maxEnergy) {
        MAX_ENERGY = maxEnergy;
    }


    @Override
    public int getCurrentEnergy() {
        return currentEnergy;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }


    @Override
    public void increaseEnergy(int amount) {
        currentEnergy = Math.min(MAX_ENERGY,getCurrentEnergy()+amount);
    }



    @Override
    public boolean tryDecreaseEnergy(int amount) {
        int newVal = getCurrentEnergy()-amount;
        if(newVal < 0) return false;

        currentEnergy = newVal;
        return true;
    }
}
