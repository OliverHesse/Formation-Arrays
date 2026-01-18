package net.lucent.formation_arrays.items.formations;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.items.IFormationHolder;
import net.lucent.formation_arrays.data_components.ModDataComponents;
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


}
