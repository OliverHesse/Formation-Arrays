package net.lucent.formation_arrays.api.formations.flags;

import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;

public interface IFormationFlagConditionData {
    Vec3i getRelativePos();
    IFormationFlagCondition getCondition();
    boolean isFlagValid(Block formationCore);
}
