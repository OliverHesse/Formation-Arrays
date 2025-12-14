package net.lucent.formation_arrays.api.cores;

import net.lucent.formation_arrays.api.formations.node.FormationPort;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;
//TODO remember to move some functionality in abstract core to here. or at least add important methods here
public interface IFormationCore {


    FormationPort<?> getFormationPort(UUID formationId,String portId,String portType);
    ItemStack getFormationItemStack(UUID formation);
    List<ItemStack> getFormationJadeSlips(UUID formation);
    String getOwnerId();
    int getPermissionLevel();
}
