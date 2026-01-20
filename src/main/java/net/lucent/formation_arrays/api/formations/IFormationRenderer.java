package net.lucent.formation_arrays.api.formations;

import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.logging.Level;

public interface IFormationRenderer{

    void render(RenderLevelStageEvent event, BlockPos core, IFormationNode node);
}
