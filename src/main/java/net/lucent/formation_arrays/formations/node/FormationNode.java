package net.lucent.formation_arrays.formations.node;


import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationPortFunction;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.util.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

public class FormationNode implements IFormationNode {

    private final HashMap<String, IFormationConnection<?>> formationConnection = new HashMap<>();
    private final HashMap<String, IFormationPort<?>> formationPorts = new HashMap<>();
    private final ResourceLocation formationKey;
    private UUID formationUUID;
    private boolean activeLastTick;
    public final IFormation formation;
    public FormationNode(IFormation formation) {
        this.formationKey = FormationRegistry.FORMATION_REGISTRY.getKey(formation);
        this.formation = formation;
    }

    @Override
    public IFormationConnection<?> getFormationConnection(String id) {
        return formationConnection.get(id);
    }

    @Override
    public void putFormationConnection(String id, IFormationConnection<?> connection) {
        formationConnection.put(id,connection);
    }

    @Override
    public IFormationPort<?> getFormationPort(String port) {
        return formationPorts.get(port);
    }

    @Override
    public boolean hasPort(String port) {
        return formationPorts.containsKey(port);
    }

    @Override
    public void putFormationPort(String portId, IFormationPort<?> port) {
        formationPorts.put(portId,port);
    }



    @Override
    public Collection<IFormationConnection<?>> getFormationConnections() {
        return formationConnection.values();
    }

    @Override
    public Collection<IFormationPort<?>> getFormationPorts() {
        return formationPorts.values();
    }

    @Override
    public String getPortId(IFormationPort<?> port) {
        for(Map.Entry<String,IFormationPort<?>> entry : formationPorts.entrySet()){
            if(entry.getValue() == port) return entry.getKey();
        }
        return null; //make it clear it does not exist
    }

    @Override
    public IFormation getFormation() {
        return formation;
    }


    @Override
    public UUID getFormationId() {
        return formationUUID;
    }

    @Override
    public ResourceLocation getFormationKey() {
        return formationKey;
    }

    @Override
    public void setFormationId(UUID id) {
        this.formationUUID=id;
    }

    @Override
    public int getEnergyCost() {
        return 5;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        for(IFormationConnection<?> connection :getFormationConnections()){
            connection.encode(buf);
        }
    }

    @Override
    public void decode(RegistryFriendlyByteBuf buf) {
        for(IFormationConnection<?> connection :getFormationConnections()){
            connection.decode(buf);
        }
    }

    @Override
    public void tick(AbstractFormationCoreBlockEntity blockEntity) {

    }

    @Override
    public boolean activeLastTick() {
        return activeLastTick;
    }

    @Override
    public void setActiveLastTick(boolean activeLastTick) {
        this.activeLastTick = activeLastTick;
    }

    @Override
    public void deactivate(AbstractFormationCoreBlockEntity blockEntity) {

    }



    @Override
    public CompoundTag save(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        ListTag connectionListTag = new ListTag();
        System.out.println("saving formation node data");
        for (IFormationConnection<?> connection : getFormationConnections()){
            if(connection.getCoreLocation() == null) continue;
            System.out.println("Saving connection data");
            CompoundTag connectionTag = new CompoundTag();
            connectionTag.putString("connection_id",connection.getConnectionId());
            connectionTag.put("core_pos", NBTUtil.blockPos(connection.getCoreLocation()));
            connectionTag.putUUID("formation_uuid",connection.getFormationId());
            connectionTag.putString("port_id",connection.getConnectionPort());
            connectionListTag.add(connectionTag);

        }
        tag.put("connection_data",connectionListTag);
        return tag;
    }

    @Override
    public void read(CompoundTag tag, HolderLookup.Provider registries) {
        System.out.println("loading formation node data");
        ListTag connectionListTag = tag.getList("connection_data", Tag.TAG_COMPOUND);
        for(int i = 0;i<connectionListTag.size();i++){
            System.out.println("loading formation connection");
            CompoundTag connectionTag = connectionListTag.getCompound(i);
            IFormationConnection<?> connection = getFormationConnection(connectionTag.getString("connection_id"));
            connection.setConnectionPort(connectionTag.getString("port_id"));
            connection.setFormationId(connectionTag.getUUID("formation_uuid"));
            connection.setCoreLocation(NBTUtil.fromCompound(connectionTag.getCompound("core_pos")));
        }
    }
}
