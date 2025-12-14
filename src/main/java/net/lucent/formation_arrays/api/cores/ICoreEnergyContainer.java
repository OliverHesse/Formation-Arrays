package net.lucent.formation_arrays.api.cores;

import net.neoforged.neoforge.energy.IEnergyStorage;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface ICoreEnergyContainer{


    BigInteger getCurrentEnergy();

    void increaseEnergy(BigInteger amount);
    void increaseEnergy(int amount);


    //try to decrease energy. false -> was not able too, true-> was able too
    boolean tryDecreaseEnergy(BigInteger amount);
    boolean tryDecreaseEnergy(int amount);


    default boolean isEmpty(){
        return BigInteger.ZERO.compareTo(getCurrentEnergy()) == 0;
    }
}
