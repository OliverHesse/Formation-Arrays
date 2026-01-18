package net.lucent.formation_arrays.formations.node;

import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationPortFunction;
import net.minecraft.network.chat.Component;

public record FormationPort<T>(String portType,Component name,Component description,IFormationPortFunction<T> runnable) implements IFormationPort<T> {
    @Override
    public String getPortType() {
        return portType;
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component getDescription() {
        return description;
    }


    @Override
    public IFormationPortFunction<T> getRunnable() {
        return runnable;
    }
}
