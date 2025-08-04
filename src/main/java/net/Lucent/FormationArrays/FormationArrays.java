package net.Lucent.FormationArrays;

import net.Lucent.FormationArrays.formations.AbstractFormationNode;
import net.Lucent.FormationArrays.formations.FormationRuntimeData;
import net.Lucent.FormationArrays.formations.data_channels.DataSocket;
import net.Lucent.FormationArrays.registries.FormationArrayRegistry;
import net.lucent.easygui.elements.BaseRenderable;

import net.lucent.easygui.holders.EasyGuiEventHolder;
import net.lucent.easygui.overlays.EasyGuiOverlay;
import net.lucent.easygui.overlays.EasyGuiOverlayManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FormationArrays.MOD_ID)
public class FormationArrays
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "formation_arrays";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FormationArrays(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        FormationArrayRegistry.register(modEventBus);
        testFormationNodes();
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        /*
        if (FMLEnvironment.dist == Dist.CLIENT) {
            EasyGuiOverlayManager.registerVanillaOverlayOverride(VanillaGuiLayers.PLAYER_HEALTH,
                    new EasyGuiOverlay(((easyGuiEventHolder, easyGuiOverlay) -> {
                        View view = new View(easyGuiEventHolder,easyGuiOverlay,0,0, Minecraft.getInstance().getWindow().getScreenWidth(),
                                Minecraft.getInstance().getWindow().getScreenHeight());
                        view.useMinecraftScale = true;
                        view.addChild(
                                new BaseRenderable(easyGuiEventHolder) {
                                    @Override
                                    public void renderSelf(GuiGraphics guiGraphics, int i, int i1, float v) {
                                        guiGraphics.fill(0,0,500,500,(new Color(255,255,255,255)).getRGB());
                                    }
                                }
                        );
                        easyGuiOverlay.view = view;
                    })));
        }

         */
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    public void testFormationNodes(){
        AbstractFormationNode node = new AbstractFormationNode(){

            @Override
            public void run(FormationRuntimeData runtimeData) {

            }
        };
        node.addOutputSocket("test_1",new DataSocket<Integer>(2,node));
        node.addOutputSocket("test_2",new DataSocket<String>("test",node));
        System.out.println("testing");

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
