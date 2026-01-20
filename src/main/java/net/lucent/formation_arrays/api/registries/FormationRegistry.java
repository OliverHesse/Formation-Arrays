package net.lucent.formation_arrays.api.registries;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.formations.IFormationRenderer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
@EventBusSubscriber(modid = FormationArrays.MOD_ID,bus = EventBusSubscriber.Bus.MOD)
public class FormationRegistry {

    public static final ResourceKey<Registry<IFormation>> FORMATION_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation
            .fromNamespaceAndPath(FormationArrays.MOD_ID,"formations"));
    public static final Registry<IFormation> FORMATION_REGISTRY = new RegistryBuilder<>(FORMATION_REGISTRY_KEY)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"none"))
            .create();

    public static class FormationRenderers{
        public static final ResourceKey<Registry<IFormationRenderer>> RENDERERS_KEY = ResourceKey.createRegistryKey(ResourceLocation
                .fromNamespaceAndPath(FormationArrays.MOD_ID,"formation_renderers"));
        public static final Registry<IFormationRenderer> RENDERERS_REGISTRY = new RegistryBuilder<>(RENDERERS_KEY)
                .defaultKey(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID,"none"))
                .create();

    }


    @SubscribeEvent
    public static void register(NewRegistryEvent event){
        event.register(FORMATION_REGISTRY);
        event.register(FormationRenderers.RENDERERS_REGISTRY);
    }
}
