package net.lucent.formation_arrays.api.formations.node;


import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public interface IFormationPort<T>{

    String getPortType();
    Component getName();
    Component getDescription();
    default T run(Level level){
        return getRunnable().run(level);
    }

    IFormationPortFunction<T> getRunnable();
;

}
