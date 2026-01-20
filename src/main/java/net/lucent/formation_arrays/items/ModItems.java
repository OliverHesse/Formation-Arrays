package net.lucent.formation_arrays.items;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.items.formations.FormationPlateItem;
import net.lucent.formation_arrays.items.fuel.EnergyCrystal;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FormationArrays.MOD_ID);

    public static final DeferredItem<Item> PLAYER_ACCESS_CONTROL_TOKEN = ITEMS.register("player_access_control_token",
            () -> new PlayerAccessControlToken(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> FORMATION_PLATE = ITEMS.register("formation_plate",
            () -> new FormationPlateItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ENERGY_CRYSTAL = ITEMS.register("energy_crystal",
            ()->new EnergyCrystal(new Item.Properties()));
    public static void register(IEventBus modEventBus){
        ITEMS.register(modEventBus);
    }
}
