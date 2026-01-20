package net.lucent.formation_arrays.api.formations.flags;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public interface IFormationFlagCondition {

    boolean run(Level level, BlockPos flag, BlockPos formationCore);
}
