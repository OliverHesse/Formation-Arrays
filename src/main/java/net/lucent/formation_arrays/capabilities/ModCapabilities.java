package net.lucent.formation_arrays.capabilities;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.capability.Capabilities;
import net.lucent.formation_arrays.capabilities.tokens.PlayerAccessToken;
import net.lucent.formation_arrays.items.ModItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = FormationArrays.MOD_ID,bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){

     event.registerItem(
                Capabilities.ACCESS_TOKEN_CAPABILITY,
                (itemStack, context)->new PlayerAccessToken(),
                ModItems.PLAYER_ACCESS_CONTROL_TOKEN

        );



    }
}
