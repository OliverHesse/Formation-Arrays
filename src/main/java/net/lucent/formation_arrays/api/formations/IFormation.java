package net.lucent.formation_arrays.api.formations;

import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.formations.flags.FormationFlagConditionData;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

/**
 * this is what will be stored in the registry
 */
public interface IFormation {
    public interface IFormationNodeSupplier{
        IFormationNode run(BlockPos pos,UUID formationId);
    }
    IFormationRenderer getFormationRenderer();
    boolean hasRenderer();
    IFormationNode getNewFormationNode(BlockPos corePos, UUID formationId);
    List<FormationFlagConditionData> getFormationFlagConditions();
    Component getFormationTitle();
    Component getFormationDescription();
}
