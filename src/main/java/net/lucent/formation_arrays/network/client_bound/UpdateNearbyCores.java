package net.lucent.formation_arrays.network.client_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.network.server_bound.RequestNearbyCoresPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public record UpdateNearbyCores (Set<BlockPos> blockPos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateNearbyCores> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "update_nearby_cores"));
    public static final StreamCodec<ByteBuf, UpdateNearbyCores> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(HashSet::new,BlockPos.STREAM_CODEC),
            UpdateNearbyCores::blockPos,
            UpdateNearbyCores::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(UpdateNearbyCores payload, IPayloadContext context) {

        FormationArrays.getClientCoreManager().updateCores(payload.blockPos());
    }
}
