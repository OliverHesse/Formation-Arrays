package net.lucent.formation_arrays.formations.node;

import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.formations.node.connections.IFormationConnection;
import net.lucent.formation_arrays.api.capability.IFormationHolder;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.network.client_bound.SyncCoreSlotData;
import net.lucent.formation_arrays.network.client_bound.SyncFormationCoreSlotData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoreNodeSlot {


    public static CoreNodeSlot createEmpty(){return new CoreNodeSlot(ItemStack.EMPTY,null);}
    private ItemStack itemStack;
    private IFormationNode formationNode;
    private boolean dirty = false;
    public CoreNodeSlot(ItemStack itemStack, IFormationNode node){
        this.itemStack = itemStack;
        formationNode = node;
    }
    public UUID getUUID(){
        return formationNode.getFormationId();
    }
    public ItemStack getItemStack(){
        return itemStack;
    }
    public IFormationNode getFormationNode(){
        return formationNode;
    }
    public void setItemStack(ItemStack itemStack){
        this.itemStack = itemStack;
        markDirty();
    }
    public void setFormationNode(IFormationNode node){
        this.formationNode = node;
        markDirty();
    }
    public void empty(){
        this.formationNode = null;
        this.itemStack = ItemStack.EMPTY;
        markDirty();
    }
    public void markDirty(){this.dirty = true;}

    public boolean consumeDirty(){
        if(dirty){
            dirty = false;
            return true;
        }
        return false;
    }
    public void sendSyncPacketToPlayer(Player player, AbstractFormationCoreBlockEntity core, int slot){
        IFormationNode node = core.formationNodeSlots[slot].getFormationNode();
        PacketDistributor.sendToPlayer((ServerPlayer) player,new SyncFormationCoreSlotData(core.getBlockPos(),node,slot));
    }

    public static CoreNodeSlot fromItemStack(UUID uuid, ItemStack itemStack, BlockPos pos){
        return new CoreNodeSlot(itemStack,((IFormationHolder)itemStack.getItem()).getFormation(itemStack).getNewFormationNode(pos,uuid));
    }
}
