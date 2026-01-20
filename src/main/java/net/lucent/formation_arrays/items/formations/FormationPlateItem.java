package net.lucent.formation_arrays.items.formations;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.capability.IFormationHolder;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FormationPlateItem extends Item implements IFormationHolder {
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    public FormationPlateItem(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getFormationResourceLocation(ItemStack stack) {
        if(!stack.is(this) || !stack.has(ModDataComponents.FORMATION_PLATE_COMPONENT)) return ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"none");
        return ResourceLocation.bySeparator(stack.get(ModDataComponents.FORMATION_PLATE_COMPONENT),':');

    }


    @Override
    public Component getName(ItemStack stack) {
        if(stack.has(ModDataComponents.FORMATION_PLATE_COMPONENT)){
            ResourceLocation id = ResourceLocation.bySeparator(stack.get(ModDataComponents.FORMATION_PLATE_COMPONENT),':');
            if(FormationRegistry.FORMATION_REGISTRY.containsKey(id)) return FormationRegistry.FORMATION_REGISTRY.get(id).getFormationTitle();
        }
        return super.getName(stack);
    }
}
