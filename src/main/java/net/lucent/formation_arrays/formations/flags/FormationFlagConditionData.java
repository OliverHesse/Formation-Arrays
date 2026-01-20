package net.lucent.formation_arrays.formations.flags;

import net.lucent.formation_arrays.api.formations.flags.IFormationFlagCondition;
import net.lucent.formation_arrays.api.formations.flags.IFormationFlagConditionData;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;

//Condition will be used for stuff like flag type
public record FormationFlagConditionData(Vec3i relativePos, IFormationFlagCondition condition)implements IFormationFlagConditionData {

    @Override
    public Vec3i getRelativePos() {
        return relativePos();
    }

    @Override
    public IFormationFlagCondition getCondition() {
        return condition();
    }


}
