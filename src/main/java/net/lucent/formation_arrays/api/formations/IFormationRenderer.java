package net.lucent.formation_arrays.api.formations;

import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.logging.Level;

public interface IFormationRenderer{

    void tick(IFormationCore core, IFormationNode node);
    void render(RenderLevelStageEvent event, BlockPos core, IFormationNode node);
}
