package net.lucent.formation_arrays.network.server_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.network.client_bound.UpdateNearbyCores;
import net.lucent.formation_arrays.network.client_bound.UpdateNearbyFormations;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record RequestNearbyFormationsPayload(BlockPos blockPos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RequestNearbyFormationsPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "request_nearby_formations"));
    public static final StreamCodec<ByteBuf, RequestNearbyFormationsPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            RequestNearbyFormationsPayload::blockPos,
            RequestNearbyFormationsPayload::new
    );
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(RequestNearbyFormationsPayload payload, IPayloadContext context) {


        BlockEntity blockEntity = context.player().level().getBlockEntity(payload.blockPos);
        if(blockEntity instanceof IFormationCore formationCore){
            int radius = formationCore.getDetectionRadius();

            Set<BlockPos> cores = FormationArrays.getCoreManager().getNearbyCores(context.player().level(),payload.blockPos,radius);
            List<UpdateNearbyFormations.PacketDataInstance> packet = new ArrayList<>();
            for(BlockPos core : cores){
                if(context.player().level().getBlockEntity(core) instanceof IFormationCore otherCore){
                    for (UUID formationID : otherCore.getFormationNodeIDs()){

                        ResourceLocation key = otherCore.getFormationRegistryId(formationID);
                        packet.add(new UpdateNearbyFormations.PacketDataInstance(
                                otherCore.getOwnerId(),
                                otherCore.getPermissionLevel(),
                                key.toString(),
                                formationID.toString(),
                                core
                        ));
                    }
                }
            }
            PacketDistributor.sendToPlayer((ServerPlayer) context.player(),new UpdateNearbyFormations(packet));
        }


    }


}