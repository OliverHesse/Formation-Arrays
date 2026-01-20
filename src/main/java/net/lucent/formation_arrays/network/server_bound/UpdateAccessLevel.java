package net.lucent.formation_arrays.network.server_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.capability.IAccessControlToken;
import net.lucent.formation_arrays.api.capability.Capabilities;
import net.lucent.formation_arrays.capabilities.tokens.PlayerAccessToken;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.lucent.formation_arrays.network.client_bound.SyncAccessToken;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateAccessLevel(int accessLevelChange) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateAccessLevel> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "update_access_level"));
    public static final StreamCodec<ByteBuf, UpdateAccessLevel> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            UpdateAccessLevel::accessLevelChange,
            UpdateAccessLevel::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(UpdateAccessLevel payload, IPayloadContext context) {


        ItemStack stack =  context.player().getItemInHand(InteractionHand.MAIN_HAND);
        IAccessControlToken capability = stack.getCapability(Capabilities.ACCESS_TOKEN_CAPABILITY);
        if(capability == null) return;
        capability.tryUpdateAccessLevel(context.player(),stack,payload.accessLevelChange());
        if(capability instanceof PlayerAccessToken){
            //TODO set up a better way to sync capabilities
            PacketDistributor.sendToPlayer((ServerPlayer) context.player(),new SyncAccessToken(stack.get(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT)));

        }
    }


}