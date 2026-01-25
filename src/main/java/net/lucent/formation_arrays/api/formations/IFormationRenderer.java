package net.lucent.formation_arrays.api.formations;

import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public interface IFormationRenderer{

    void tick(Level level,BlockPos pos ,IFormationCore core, IFormationNode node);
    void render(RenderLevelStageEvent event, BlockPos core, IFormationNode node);
}
