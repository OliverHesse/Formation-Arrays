package net.lucent.formation_arrays.capabilities;

import net.lucent.formation_arrays.api.capability.Capabilities;
import net.lucent.formation_arrays.api.capability.IFormationFuel;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.minecraft.world.item.ItemStack;

public class ConsumeItemFormationFuel implements IFormationFuel {
    @Override
    public int estimateFuel(ItemStack itemStack) {
        if(!itemStack.has(ModDataComponents.FORMATION_FUEL)) return 0;
        return itemStack.get(ModDataComponents.FORMATION_FUEL);
    }

    @Override
    public int getFuel(ItemStack itemStack) {
        if(!itemStack.has(ModDataComponents.FORMATION_FUEL)) return 0;
        int amount = itemStack.get(ModDataComponents.FORMATION_FUEL);
        itemStack.shrink(1);
        return amount;
    }
}
