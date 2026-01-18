package net.lucent.formation_arrays.util;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.ModBlocks;
import net.lucent.formation_arrays.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FormationArrays.MOD_ID);

    public static final Supplier<CreativeModeTab> FORMATION_CORE_TAB = CREATIVE_MODE_TAB.register("formation_core_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLAYER_ACCESS_CONTROL_TOKEN.get()))
                    .title(Component.literal("formation array items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        ModItems.PLAYER_ACCESS_CONTROL_TOKEN.get();
                        ModBlocks.TIER_1_FORMATION_CORE.get();
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
