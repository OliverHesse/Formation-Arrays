package net.lucent.formation_arrays.network.server_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.network.client_bound.UpdateNearbyCores;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Set;

public record RequestNearbyCoresPayload(BlockPos blockPos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestNearbyCoresPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "request_nearby_cores"));
    public static final StreamCodec<ByteBuf, RequestNearbyCoresPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            RequestNearbyCoresPayload::blockPos,
            RequestNearbyCoresPayload::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(RequestNearbyCoresPayload payload, IPayloadContext context) {


        BlockEntity blockEntity = context.player().level().getBlockEntity(payload.blockPos);
        if(blockEntity instanceof IFormationCore formationCore){
            int radius = formationCore.getDetectionRadius();

            Set<BlockPos> cores = FormationArrays.getCoreManager().getNearbyCores(context.player().level(),payload.blockPos,radius);
            PacketDistributor.sendToPlayer((ServerPlayer) context.player(),new UpdateNearbyCores(cores));
        }


    }


}
