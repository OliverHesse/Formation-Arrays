package net.lucent.formation_arrays.api.capability;

import net.minecraft.world.item.ItemStack;

public interface IFormationFuel {
    int estimateFuel(ItemStack itemStack);
    int getFuel(ItemStack itemStack);
}
