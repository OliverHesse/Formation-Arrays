package net.lucent.formation_arrays.capabilities;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.capability.IAccessControlToken;
import net.lucent.formation_arrays.capabilities.tokens.PlayerAccessToken;
import net.lucent.formation_arrays.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = FormationArrays.MOD_ID,bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    public static final ItemCapability<IAccessControlToken, @Nullable Void> ACCESS_TOKEN_CAPABILITY = ItemCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"access_token"), IAccessControlToken.class);


    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(
                ModCapabilities.ACCESS_TOKEN_CAPABILITY,
                (itemStack, context)->new PlayerAccessToken(),
                ModItems.PLAYER_ACCESS_CONTROL_TOKEN
        );
    }
}
