package net.lucent.formation_arrays.api.items;

import net.minecraft.world.item.ItemStack;

public interface IAccessControlToken {

    String getOwnerId(ItemStack controlToken);
    int getPermissionLevel(ItemStack controlToken);
    boolean isLinked(ItemStack controlToken);
}
