package net.lucent.formation_arrays;
import net.lucent.formation_arrays.blocks.ModBlocks;
import net.lucent.formation_arrays.blocks.block_entities.ModBlockEntities;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.lucent.formation_arrays.formations.ModFormations;
import net.lucent.formation_arrays.gui.ModMenuTypes;
import net.lucent.formation_arrays.items.ModItems;

import net.lucent.formation_arrays.network.ModPayloads;
import net.lucent.formation_arrays.util.core_managers.ClientCoreManager;
import net.lucent.formation_arrays.util.core_managers.CoreManager;
import net.lucent.formation_arrays.util.ModCreativeModeTabs;
import net.lucent.formation_arrays.util.formation_holder.ClientFormationHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.apache.logging.log4j.LogManager;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FormationArrays.MOD_ID)
public class FormationArrays
{

    private static CoreManager CORE_MANAGER = new CoreManager(); // World ID -> SectManager
    private static ClientCoreManager CLIENT_CORE_MANAGER = new ClientCoreManager();
    private static ClientFormationHolder CLIENT_FORMATION_HOLDER = new ClientFormationHolder();
    public static CoreManager getCoreManager() {
        return CORE_MANAGER;
    }


    public static ClientCoreManager getClientCoreManager(){return CLIENT_CORE_MANAGER;}
    public static ClientFormationHolder getClientFormationHolder(){return CLIENT_FORMATION_HOLDER;}
    @OnlyIn(Dist.CLIENT)
    public static void createClientCoreManager(){CLIENT_CORE_MANAGER = new ClientCoreManager();}
    @OnlyIn(Dist.CLIENT)
    public static void createClientFormationHolder(){CLIENT_FORMATION_HOLDER = new ClientFormationHolder();}
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "formation_arrays";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public void registrations(IEventBus modEventBus){

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModFormations.register(modEventBus);
    }
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FormationArrays(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading

        registrations(modEventBus);


        //testFormationNodes();
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }



    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        CORE_MANAGER =  CoreManager.get(event.getServer());
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        CORE_MANAGER.save();
    }

    @EventBusSubscriber(modid = FormationArrays.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents {




        @SubscribeEvent
        public static void registerPayloads(RegisterPayloadHandlersEvent event) {

            ModPayloads.registerPayloads(event);
        }
    }


}
