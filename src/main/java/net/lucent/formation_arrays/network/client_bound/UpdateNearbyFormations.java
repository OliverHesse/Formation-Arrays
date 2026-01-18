package net.lucent.formation_arrays.network.client_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public record UpdateNearbyFormations(List<PacketDataInstance> formations) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<UpdateNearbyFormations> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "update_nearby_formations"));

    public static final StreamCodec<ByteBuf, UpdateNearbyFormations> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new,PacketDataInstance.STREAM_CODEC),
            UpdateNearbyFormations::formations,
            UpdateNearbyFormations::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(UpdateNearbyFormations payload, IPayloadContext context) {

        FormationArrays.getClientFormationHolder().addFormations(payload.formations());
    }

    public record PacketDataInstance(String ownerId,int permissionLevel,String formationKey, String formationUUID, BlockPos coreLocation){
        public static final StreamCodec<ByteBuf, PacketDataInstance> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                PacketDataInstance::ownerId,
                ByteBufCodecs.VAR_INT,
                PacketDataInstance::permissionLevel,
                ByteBufCodecs.STRING_UTF8,
                PacketDataInstance::formationKey,
                ByteBufCodecs.STRING_UTF8,
                PacketDataInstance::formationUUID,
                BlockPos.STREAM_CODEC,
                PacketDataInstance::coreLocation,
                PacketDataInstance::new
        );
    }
}
