package net.lucent.formation_arrays.api.formations.flags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public interface IFormationFlagConditionData {
    Vec3i getRelativePos();
    IFormationFlagCondition getCondition();
    default boolean isFlagValid(Level level, BlockPos formationCore) {
        BlockPos flagPos = formationCore.offset(getRelativePos());
        return  getCondition().run(level,flagPos,formationCore);
    }
}
