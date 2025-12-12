package net.lucent.formation_arrays.api.formations.flags;

import net.minecraft.world.level.block.Block;

public interface IFormationFlagCondition {

    boolean run(Block flag,Block formationCore);
}
