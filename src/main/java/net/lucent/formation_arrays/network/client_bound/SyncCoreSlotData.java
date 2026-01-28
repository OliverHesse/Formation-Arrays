package net.lucent.formation_arrays.network.client_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public record SyncCoreSlotData(BlockPos core, boolean empty, String uuid, String registryObject, int slot,List<ConnectionSyncData> connectionSyncData)implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncCoreSlotData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "sync_core_slot_data"));

    public record ConnectionSyncData(String connectionId,BlockPos core,String formationId,String portId){
        public static final StreamCodec<ByteBuf, ConnectionSyncData> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                ConnectionSyncData::connectionId,
                BlockPos.STREAM_CODEC,
                ConnectionSyncData::core,
                ByteBufCodecs.STRING_UTF8,
                ConnectionSyncData::formationId,
                ByteBufCodecs.STRING_UTF8,
                ConnectionSyncData::portId,
                ConnectionSyncData::new
        );
    }
    public static final StreamCodec<ByteBuf, SyncCoreSlotData> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SyncCoreSlotData::core,
            ByteBufCodecs.BOOL,
            SyncCoreSlotData::empty,
            ByteBufCodecs.STRING_UTF8,
            SyncCoreSlotData::uuid,
            ByteBufCodecs.STRING_UTF8,
            SyncCoreSlotData::registryObject,
            ByteBufCodecs.VAR_INT,
            SyncCoreSlotData::slot,
            ByteBufCodecs.collection(ArrayList::new,ConnectionSyncData.STREAM_CODEC),
            SyncCoreSlotData::connectionSyncData,
            SyncCoreSlotData::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handlePayload(SyncCoreSlotData payload, IPayloadContext context) {

        context.enqueueWork(()->{
            if(context.player().level().getBlockEntity(payload.core()) instanceof AbstractFormationCoreBlockEntity coreBlockEntity){
                if(payload.empty){
                    coreBlockEntity.formationNodeSlots[payload.slot].empty();
                    return;
                }

                UUID nodeUUID = UUID.fromString(payload.uuid);
                ResourceLocation registryKey = ResourceLocation.bySeparator(payload.registryObject,':');
                IFormationNode node = FormationRegistry.FORMATION_REGISTRY.get(registryKey).getNewFormationNode(coreBlockEntity.getBlockPos(),nodeUUID);
                ItemStack itemStack = coreBlockEntity.getFormationItemStackHandler().getFormationItemStack(payload.slot);

                coreBlockEntity.formationNodeSlots[payload.slot].setFormationNode(node);
                coreBlockEntity.formationNodeSlots[payload.slot].setItemStack(itemStack);

                for(ConnectionSyncData syncData : payload.connectionSyncData()){

                    IFormationConnection<?> connection = node.getFormationConnection(syncData.connectionId());

                    connection.setCoreLocation(syncData.core);
                    connection.setFormationId(UUID.fromString(syncData.formationId()));
                    connection.setConnectionPort(syncData.portId);
                }

            }
        });

    }
}
