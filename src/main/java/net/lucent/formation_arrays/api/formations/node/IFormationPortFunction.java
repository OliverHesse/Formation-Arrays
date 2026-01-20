package net.lucent.formation_arrays.api.formations.node;

import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.minecraft.world.level.Level;

public interface IFormationPortFunction<T>{
    T run(Level level,  IFormationNode formationNode);
}
