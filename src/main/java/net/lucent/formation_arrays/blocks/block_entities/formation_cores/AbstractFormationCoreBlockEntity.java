package net.lucent.formation_arrays.blocks.block_entities.formation_cores;


import net.lucent.formation_arrays.api.cores.ICoreEnergyContainer;
import net.lucent.formation_arrays.api.cores.IFormationCore;

import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.items.IAccessControlToken;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.formations.node.CoreNodeSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/**
 * TODO add storage
 * key slots. access control slip slot, formation slot, fuel slot,jade slip slots per formation slot
 * make a wrapper that keeps track of these things
 * */
public abstract class AbstractFormationCoreBlockEntity extends BlockEntity implements IFormationCore, MenuProvider {

    private final CoreNodeSlot[] formationNodeSlots;
    private final HashMap<UUID,Integer> idSlotMap = new HashMap<>();

    public final ICoreEnergyContainer energyContainer;
    public final FormationCoreItemStackHandler formationItemStackHandler;
    public ContainerData dataSlot = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index){
                case 0 ->  (int)energyContainer.getEnergyPercent();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, ICoreEnergyContainer energyContainer,FormationCoreItemStackHandler itemStackHandler) {
        super(type, pos, blockState);
        this.energyContainer = energyContainer;
        this.formationItemStackHandler = itemStackHandler;
        this.formationItemStackHandler.entity = this;

        formationNodeSlots = new CoreNodeSlot[itemStackHandler.MAX_FORMATIONS];
        for(int i = 0;i<formationNodeSlots.length;i++){
            formationNodeSlots[i] = new CoreNodeSlot(null,ItemStack.EMPTY,null);
        }
    }

    public FormationCoreItemStackHandler getFormationItemStackHandler(){
        return formationItemStackHandler;
    }
    public void clearFormationSlot(int slot){
        idSlotMap.remove(formationNodeSlots[slot].uuid());
        formationNodeSlots[slot] = null;
    }
    public void updateFormationSlot(int slot){
        ItemStack itemStack = formationItemStackHandler.getFormationItemStack(slot);
        if(itemStack.equals(ItemStack.EMPTY)) {
            clearFormationSlot(slot);
            return;
        }
        if(itemStack == formationNodeSlots[slot].itemStack()) return;
        clearFormationSlot(slot);
        formationNodeSlots[slot] = CoreNodeSlot.fromItemStack(UUID.randomUUID(),itemStack,getBlockPos());
        idSlotMap.put(formationNodeSlots[slot].uuid(),slot);
    }
    public void run(Level level){
        if(energyContainer.isEmpty()) return;
        for(int i = 0;i<formationNodeSlots.length;i++){
            if(isFormationSlotValid(i)){
                tryRunFormation(i,level);
            }
        }

    }
    public boolean isFormationSlotValid(int slot){
        updateFormationSlot(slot);
        return formationNodeSlots[slot] != null;
    }


    @Override
    public ICoreEnergyContainer getEnergyContainer() {
        return energyContainer;
    }



    private void tryRunFormation(int slot, Level level){
        if(tryBurnEnergy(getFormationNode(slot).getEnergyCost())){
            getFormationNode(slot).run(this,level,this.getBlockPos());
        }
    }





    public IFormationNode getFormationNode(UUID id){
        return getFormationNode(idSlotMap.get(id));
    }

    public IFormationNode getFormationNode(int slot){
        updateFormationSlot(slot);
        return formationNodeSlots[slot].node();
    }

    @Override
    public IFormationPort<?> getFormationPort(UUID formationId, String portId, String portType){
        if(!idSlotMap.containsKey(formationId)) return null;
        return getFormationNode(formationId).getFormationPort(portId);
    }

    @Override
    public ItemStack getFormationItemStack(UUID formation){
        return formationItemStackHandler.getFormationItemStack(idSlotMap.get(formation));
    }

    @Override
    public List<ItemStack> getFormationJadeSlips(UUID formation){
        return formationItemStackHandler.getFormationJadeSlips(idSlotMap.get(formation));
    }

    @Override
    public String getOwnerId(){
        ItemStack itemStack = formationItemStackHandler.getControlToken();
        if(itemStack == ItemStack.EMPTY) return null;
        if(!itemStack.has(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT)) return null;
        return ((IAccessControlToken)itemStack.getItem()).getOwnerId(itemStack);
    }

    @Override
    public int getPermissionLevel(){
        ItemStack itemStack = formationItemStackHandler.getControlToken();
        if(itemStack == ItemStack.EMPTY) return 0;
        if(!itemStack.has(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT)) return 0;

        return ((IAccessControlToken)itemStack.getItem()).getPermissionLevel(itemStack);
    }

    //TODO
    @Override
    public List<IFormationNode> getFormationNodes() {
        List<IFormationNode> formationNodes = new ArrayList<>();
        for(CoreNodeSlot slot: formationNodeSlots){
            formationNodes.add(slot.node());
        }
        return formationNodes;
    }

    @Override
    public List<UUID> getFormationNodeIDs() {
        List<UUID> uuidList = new ArrayList<>();
        for(CoreNodeSlot slot: formationNodeSlots){
            uuidList.add(slot.uuid());
        }
        return uuidList;
    }

    @Override
    public int getDetectionRadius() {
        return 0;
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState){}





    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory",formationItemStackHandler.serializeNBT(registries));
        //TODO add qi
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        formationItemStackHandler.deserializeNBT(registries,tag.getCompound("inventory"));
        //TODO add qi
    }
}
