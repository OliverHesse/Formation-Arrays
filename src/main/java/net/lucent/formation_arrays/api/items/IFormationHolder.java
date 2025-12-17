package net.lucent.formation_arrays.api.items;

import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.minecraft.resources.ResourceLocation;

public interface IFormationHolder {
    ResourceLocation getFormationResourceLocation();
    default IFormation getFormation(){
        return FormationRegistry.FORMATION_REGISTRY.get(getFormationResourceLocation());
    }
}
