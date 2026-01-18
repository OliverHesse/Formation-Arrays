package net.lucent.formation_arrays.formations;

import net.lucent.easygui.util.textures.TextureData;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.formations.node.connections.ConnectionType;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.formations.connections.DefaultFormationConnection;
import net.lucent.formation_arrays.formations.node.FormationNode;
import net.lucent.formation_arrays.formations.node.FormationPort;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFormations {
    public static final DeferredRegister<IFormation> FORMATIONS = DeferredRegister.create(FormationRegistry.FORMATION_REGISTRY, FormationArrays.MOD_ID);

    public static final DeferredHolder<IFormation,  ? extends GenericFormation> TEST = FORMATIONS.register("test",
            ()->new GenericFormation((blockPos,uuid)->
            {

                FormationNode node = new FormationNode();
                node.addFormationConnection("grab_data",
                        new DefaultFormationConnection<>(
                                blockPos,
                                uuid,
                                ConnectionType.INTEGER,
                                Component.literal("Grab 1"),
                                Component.literal("a test formation"),
                                2
                        ));
                node.addFormationConnection("grab_data_2",
                        new DefaultFormationConnection<>(
                                blockPos,
                                uuid,
                                ConnectionType.DOUBLE,
                                Component.literal("Grab 2"),
                                Component.literal("a test formation"),
                                2.0
                        ));
                node.putFormationPort("give_1",
                        new FormationPort<Integer>(ConnectionType.INTEGER,Component.literal("GIVE ").withStyle(ChatFormatting.BOLD),Component.literal("gives an int"),
                                (level)->2));
                node.putFormationPort("give_2",
                        new FormationPort<>(ConnectionType.DOUBLE,Component.literal("GIVE 2 ").withStyle(ChatFormatting.BOLD),Component.literal("gives a double"),
                                (level)->2.0));
                return node;
                }
            ));


    public static void register(IEventBus eventBus){
        FORMATIONS.register(eventBus);
    }
}
