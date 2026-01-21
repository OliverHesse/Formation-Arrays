package net.lucent.formation_arrays.api.formations.node;

import io.netty.buffer.ByteBuf;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
//TODO go through and group functions
public interface IFormationNode {

    IFormationConnection<?> getFormationConnection(String id);
    void putFormationConnection(String id,IFormationConnection<?> connection);

    IFormationPort<?> getFormationPort(String port);
    boolean hasPort(String port);
    void putFormationPort(String portId, IFormationPort<?> port);

   default void addFormationConnection(String id,IFormationConnection<?> connection){
        putFormationConnection(id,connection);
    }
    default void linkFormationConnection(String id,BlockPos core, UUID formation,String port){
        IFormationConnection<?> connection = getFormationConnection(id);
        connection.setCoreLocation(core);
        connection.setFormationId(formation);
        connection.setConnectionPort(port);
    }

    Collection<IFormationConnection<?>> getFormationConnections();
    Collection<IFormationPort<?>> getFormationPorts();
    String getPortId(IFormationPort<?> port);
    IFormation getFormation();
    default Optional<?> runPort(String port,Level level){
        if(!hasPort(port)) return Optional.empty();
        return Optional.of(getFormationPort(port).run(level,this));
    };

    UUID getFormationId();
    ResourceLocation getFormationKey();
    void setFormationId(UUID id);
    int getEnergyCost();

    /**
     * used when a formation needs to burn extra energy during operation
     * @param core the core to burn energy from
     * @param energy how much energy
     * @return true if able to burn false otherwise
     */
    default boolean tryBurnEnergy(IFormationCore core,int energy){
        return core.tryBurnEnergy(energy);
    }

    void tick(AbstractFormationCoreBlockEntity blockEntity,List<ItemStack> jadeSlips);

    boolean activeLastTick();
    void setActiveLastTick(boolean activeLastTick);
    void deactivate(AbstractFormationCoreBlockEntity blockEntity);


    //Saving and writing data

    void encode( RegistryFriendlyByteBuf buf);

    void decode(RegistryFriendlyByteBuf buf);


    CompoundTag save( HolderLookup.Provider registries);
    void read(CompoundTag tag,HolderLookup.Provider registries);
}
