package net.lucent.formation_arrays.api.formations.node;


import net.minecraft.network.chat.Component;

public record FormationPort<T>(String portType, IFormationPortFunction<T> runnable, Component name, Component description) {

    public T run() {
        return runnable.run();
    }
;

}
