package net.lucent.formation_arrays.network.server_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;
/*
    Assumes the origin core extends AbstractFormationCoreBlockEntity
 */
public record UpdateFormationConnectionStatus(ConnectionData connectionData,PortData portData,boolean connect) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateFormationConnectionStatus> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(FormationArrays.MOD_ID, "update_formation_connection_status"));

    public static record ConnectionData(BlockPos originCore, int formationSlot,String originConnection){
        public static final StreamCodec<ByteBuf,ConnectionData> STREAM_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC,
                ConnectionData::originCore,
                ByteBufCodecs.VAR_INT,
                ConnectionData::formationSlot,
                ByteBufCodecs.STRING_UTF8,
                ConnectionData::originConnection,
                ConnectionData::new
        );
    }
    public static record PortData(BlockPos portCore,String portFormation,String portId){
        public static final StreamCodec<ByteBuf,PortData> STREAM_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC,
                PortData::portCore,
                ByteBufCodecs.STRING_UTF8,
                PortData::portFormation,
                ByteBufCodecs.STRING_UTF8,
                PortData::portId,
                PortData::new
        );
    }

    public static final StreamCodec<ByteBuf,UpdateFormationConnectionStatus> STREAM_CODEC = StreamCodec.composite(
            ConnectionData.STREAM_CODEC,
            UpdateFormationConnectionStatus::connectionData,
            PortData.STREAM_CODEC,
            UpdateFormationConnectionStatus::portData,
            ByteBufCodecs.BOOL,
            UpdateFormationConnectionStatus::connect,
            UpdateFormationConnectionStatus::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(UpdateFormationConnectionStatus payload, IPayloadContext context) {

        //FIRST VERIFY CORE PERMISSIONS
        BlockPos origin = payload.connectionData.originCore();
        if(!payload.connect()){
            if(!(context.player().level().getBlockEntity(origin) instanceof AbstractFormationCoreBlockEntity core)) return;


            IFormationNode node = core.getFormationNode(payload.connectionData.formationSlot);
            if(node == null) return;
            IFormationConnection<?> connection = node.getFormationConnection(
                    payload.connectionData.originConnection
            );
            connection.setConnectionPort(null);
            connection.setFormationId(null);
            connection.setCoreLocation(null);
            return;
        }


        BlockPos other = payload.portData.portCore();
        if(context.player().level().getBlockEntity(origin) instanceof AbstractFormationCoreBlockEntity originCore &&
          context.player().level().getBlockEntity(other) instanceof IFormationCore otherCore){
            if(originCore.getOwnerId() == null || otherCore.getOwnerId() == null || !originCore.getOwnerId().equals(otherCore.getOwnerId())) return;

            if(originCore.getPermissionLevel() <= otherCore.getPermissionLevel()) return;
            //VALIDATE TYPES
            IFormationNode node = originCore.getFormationNode(payload.connectionData.formationSlot);
            if(node == null) return;
            IFormationConnection<?> connection =node.getFormationConnection(
                    payload.connectionData.originConnection
            );
            IFormationPort<?> port = otherCore.getFormationNode(UUID.fromString(payload.portData.portFormation)).getFormationPort(payload.portData.portId);

            if (!connection.getConnectionType().equals(port.getPortType())) return;

            connection.setConnectionPort(payload.portData.portId);
            connection.setFormationId(UUID.fromString(payload.portData.portFormation));
            connection.setCoreLocation(payload.portData.portCore);
            originCore.formationNodeSlots[payload.connectionData.formationSlot].markDirty();
            originCore.setChanged();

            context.player().sendSystemMessage(
                    Component.literal("CONNECTED ").append(connection.getName()).append(" TO ").append(port.getName())
            );

        }

    }
}
