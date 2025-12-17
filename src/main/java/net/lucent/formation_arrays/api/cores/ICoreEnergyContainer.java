package net.lucent.formation_arrays.api.cores;

import net.neoforged.neoforge.energy.IEnergyStorage;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface ICoreEnergyContainer{


    int getCurrentEnergy();
    int getMaxEnergy();
    default double getEnergyPercent(){
        return ((double) getCurrentEnergy()/(double) getMaxEnergy())*100;
    }
    void increaseEnergy(int amount);


    //try to decrease energy. false -> was not able too, true-> was able too
    boolean tryDecreaseEnergy(int amount);


    default boolean isEmpty(){
        return getCurrentEnergy() == 0;
    }
}
