package net.lucent.formation_arrays.api.capability;

import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IFormationHolder {
    ResourceLocation getFormationResourceLocation(ItemStack stack);
    default IFormation getFormation(ItemStack itemStack){
        return FormationRegistry.FORMATION_REGISTRY.get(getFormationResourceLocation(itemStack));
    }
}
