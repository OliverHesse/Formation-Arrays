package net.lucent.formation_arrays.api.capability;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.capabilities.tokens.PlayerAccessToken;
import net.lucent.formation_arrays.items.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

public class Capabilities {

    public static final ItemCapability<IAccessControlToken, @Nullable Void> ACCESS_TOKEN_CAPABILITY = ItemCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"access_token"), IAccessControlToken.class);

    public static final ItemCapability<IFormationFuel, @Nullable Void> FORMATION_FUEL_CAPABILITY = ItemCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"formation_fuel"), IFormationFuel.class);

    public static final ItemCapability<IFormationHolder, @Nullable Void> FORMATION_HOLDER_CAPABILITY = ItemCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"formation_holder"), IFormationHolder.class);



}
