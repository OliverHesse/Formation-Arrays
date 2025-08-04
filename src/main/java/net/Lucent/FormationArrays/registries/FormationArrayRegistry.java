package net.Lucent.FormationArrays.registries;

import net.Lucent.FormationArrays.FormationArrays;
import net.Lucent.FormationArrays.formations.interfaces.FormationUIElement;
import net.Lucent.FormationArrays.formations.interfaces.IFormation;
import net.Lucent.FormationArrays.formations.interfaces.IFormationNode;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FormationArrayRegistry {
    public static final ResourceKey<Registry<IFormation>> FORMATION_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"formations"));
    public static final ResourceKey<Registry<IFormationNode>> FORMATION_NODE_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"formation_nodes"));
    public static final ResourceKey<Registry<FormationUIElement>> FORMATION_UI_ELEMENT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"formation_ui_elements"));

    public static final DeferredRegister<IFormation> FORMATIONS =DeferredRegister.create(FORMATION_REGISTRY_KEY,FormationArrays.MOD_ID);
    public static final DeferredRegister<IFormationNode> FORMATION_NODES =DeferredRegister.create(FORMATION_NODE_REGISTRY_KEY,FormationArrays.MOD_ID);
    public static final DeferredRegister<FormationUIElement> FORMATION_UI_ELEMENTS =DeferredRegister.create(FORMATION_UI_ELEMENT_KEY,FormationArrays.MOD_ID);

    public static void register(IEventBus modEventBus){
        FORMATIONS.register(modEventBus);
        FORMATION_NODES.register(modEventBus);
        FORMATION_UI_ELEMENTS.register(modEventBus);
    }
}
