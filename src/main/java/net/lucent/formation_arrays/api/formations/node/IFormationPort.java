package net.lucent.formation_arrays.api.formations.node;


import net.lucent.formation_arrays.api.capability.IFormationHolder;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public interface IFormationPort<T>{

    String getPortType();
    Component getName();
    Component getDescription();
    default T run(Level level, IFormationNode node){
        return getRunnable().run(level,node);
    }

    IFormationPortFunction<T> getRunnable();
;

}
