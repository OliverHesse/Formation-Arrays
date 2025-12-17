package net.lucent.formation_arrays.formations.node;

import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.items.IFormationHolder;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public record CoreNodeSlot(UUID uuid, ItemStack itemStack, IFormationNode node) {
    public static CoreNodeSlot fromItemStack(UUID uuid,ItemStack itemStack){
        return new CoreNodeSlot(uuid,itemStack,((IFormationHolder)itemStack.getItem()).getFormation().getNewFormationNode());
    }
}
