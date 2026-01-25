package net.lucent.formation_arrays;


import net.lucent.formation_arrays.gui.ModMenuTypes;
import net.lucent.formation_arrays.gui.easy_gui_screens.VariedSlotCountFormationCoreScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = FormationArrays.MOD_ID,dist = Dist.CLIENT)
public class FormationArrayClient {

    @EventBusSubscriber(modid = FormationArrays.MOD_ID,bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents{
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.TIER_1_FORMATION_CORE_MENU.get(), VariedSlotCountFormationCoreScreen::new);

        }


    }

    @EventBusSubscriber(modid = FormationArrays.MOD_ID,bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ClientGameEvents{

        @SubscribeEvent
        public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event) {
            FormationArrays.createClientCoreManager();
            FormationArrays.createClientFormationHolder();
        }

    }
}
