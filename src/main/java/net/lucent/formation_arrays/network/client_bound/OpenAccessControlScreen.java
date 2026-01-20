package net.lucent.formation_arrays.network.client_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.lucent.formation_arrays.data_components.components.AccessTokenComponent;
import net.lucent.formation_arrays.gui.easy_gui_screens.AccessControlTokenScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenAccessControlScreen()implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenAccessControlScreen> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "open_access_control_screen"));
    public static final StreamCodec<ByteBuf, OpenAccessControlScreen> STREAM_CODEC = StreamCodec.unit(new OpenAccessControlScreen());


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(OpenAccessControlScreen payload, IPayloadContext context) {



        context.enqueueWork(() -> {if(FMLLoader.getDist() == Dist.CLIENT)handleOnClient();});

    }
    /**

     */
    @OnlyIn(Dist.CLIENT)
    private static void handleOnClient() {
        Minecraft.getInstance().setScreen(new AccessControlTokenScreen(Component.empty()));
    }
}