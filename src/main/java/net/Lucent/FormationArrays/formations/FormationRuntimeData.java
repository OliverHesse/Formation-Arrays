package net.Lucent.FormationArrays.formations;

import net.Lucent.FormationArrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record FormationRuntimeData(AbstractFormationCoreBlockEntity formationCore) {

    public Level level(){
        return formationCore.getLevel();
    }
    public BlockPos blockPos(){
        return formationCore.getBlockPos();
    }
}
