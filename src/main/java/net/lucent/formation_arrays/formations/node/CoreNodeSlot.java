package net.lucent.formation_arrays.formations.node;

import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.items.IFormationHolder;
import net.minecraft.world.item.ItemStack;

public record CoreNodeSlot(int slot,ItemStack itemStack, IFormationNode node) {
    public static CoreNodeSlot fromItemStack(int slot,ItemStack itemStack){
        return new CoreNodeSlot(slot,itemStack,((IFormationHolder)itemStack.getItem()).getFormation().getNewFormationNode());
    }
}
