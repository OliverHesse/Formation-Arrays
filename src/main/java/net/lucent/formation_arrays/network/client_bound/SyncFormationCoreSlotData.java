package net.lucent.formation_arrays.network.client_bound;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.formations.FormationEncodingHelper;
import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

//currently only for AbstractFormationCoreBlockEntity
public record SyncFormationCoreSlotData(BlockPos core, IFormationNode node,int slot) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncFormationCoreSlotData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "sync_formation_node_data"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncFormationCoreSlotData> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public SyncFormationCoreSlotData decode(RegistryFriendlyByteBuf buf) {
                    return SyncFormationCoreSlotData.decode(buf);
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf, SyncFormationCoreSlotData packet) {
                    packet.encode(buf); // 👈 INSTANCE ACCESS HERE
                }
            };

    public void encode(RegistryFriendlyByteBuf buf){
        buf.writeBlockPos(core);
        buf.writeInt(slot);
        FormationEncodingHelper.encode(buf,node);
    }
    public static SyncFormationCoreSlotData decode(RegistryFriendlyByteBuf buf){
        BlockPos core = buf.readBlockPos();
        int slot = buf.readInt();
        return new SyncFormationCoreSlotData(core,FormationEncodingHelper.decode(buf,core),slot);
    }

    public static void handlePayload(SyncFormationCoreSlotData payload, IPayloadContext context) {
        context.enqueueWork(()-> {
                if(context.player().level().getBlockEntity(payload.core()) instanceof AbstractFormationCoreBlockEntity coreBlockEntity){
                    ItemStack itemStack = coreBlockEntity.getFormationItemStackHandler().getFormationItemStack(payload.slot);
                    coreBlockEntity.formationNodeSlots[payload.slot].setFormationNode(payload.node);
                    coreBlockEntity.formationNodeSlots[payload.slot].setItemStack(itemStack);
                }
            }
        );
    }
}
