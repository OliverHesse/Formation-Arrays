package net.lucent.formation_arrays.api.cores;

import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;
//TODO remember to move some functionality in abstract core to here. or at least add important methods here
public interface IFormationCore {

    List<IFormationNode> getFormationNodes();
    List<UUID> getFormationNodeIDs();
    IFormationNode getFormationNode(UUID formationId);
    ResourceLocation getFormationRegistryId(UUID formationId);
    IFormationPort<?> getFormationPort(UUID formationId, String portId);
    ICoreEnergyContainer getEnergyContainer();
    ItemStack getFormationItemStack(UUID formation);
    List<ItemStack> getFormationJadeSlips(UUID formation);
    String getOwnerId();
    int getPermissionLevel();
    int getDetectionRadius();
     default boolean tryBurnEnergy(int energy) {
         return getEnergyContainer().tryDecreaseEnergy(energy);
     }


}
