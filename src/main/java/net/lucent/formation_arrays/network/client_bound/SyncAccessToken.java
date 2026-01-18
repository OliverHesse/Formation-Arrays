package net.lucent.formation_arrays.network.client_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.lucent.formation_arrays.data_components.components.AccessTokenComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashSet;
import java.util.Set;

public record SyncAccessToken (AccessTokenComponent component) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncAccessToken> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "sync_access_token"));
    public static final StreamCodec<ByteBuf, SyncAccessToken> STREAM_CODEC = StreamCodec.composite(
            AccessTokenComponent.STREAM_CODEC,
            SyncAccessToken::component,
            SyncAccessToken::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(SyncAccessToken payload, IPayloadContext context) {

        if(context.player().getItemInHand(InteractionHand.MAIN_HAND).has(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT)){

            context.player().getItemInHand(InteractionHand.MAIN_HAND).set(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT,payload.component);
        }
    }
}