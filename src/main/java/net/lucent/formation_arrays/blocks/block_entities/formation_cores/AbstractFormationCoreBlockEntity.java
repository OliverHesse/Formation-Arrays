package net.lucent.formation_arrays.blocks.block_entities.formation_cores;


import net.lucent.formation_arrays.api.cores.ICoreEnergyContainer;
import net.lucent.formation_arrays.api.cores.IFormationCore;

import net.lucent.formation_arrays.api.formations.node.FormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.items.IAccessControlToken;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.formations.node.CoreNodeSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/**
 * TODO add storage
 * key slots. access control slip slot, formation slot, fuel slot,jade slip slots per formation slot
 * make a wrapper that keeps track of these things
 * */
public abstract class AbstractFormationCoreBlockEntity extends BlockEntity implements IFormationCore {

    private final CoreNodeSlot[] formationNodeSlots;
    private final HashMap<UUID,Integer> idSlotMap = new HashMap<>();

    private final ICoreEnergyContainer energyContainer;
    private final FormationCoreItemStackHandler formationItemStackHandler;
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
        formationNodeSlots = new CoreNodeSlot[itemStackHandler.MAX_FORMATIONS];
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
        formationNodeSlots[slot] = CoreNodeSlot.fromItemStack(UUID.randomUUID(),itemStack);
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
        return formationNodeSlots[slot].node();
    }

    @Override
    public FormationPort<?> getFormationPort(UUID formationId, String portId, String portType){
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
        return ((IAccessControlToken)formationItemStackHandler.getControlToken().getItem()).getOwnerId(formationItemStackHandler.getControlToken());
    }

    @Override
    public int getPermissionLevel(){
        return ((IAccessControlToken)formationItemStackHandler.getControlToken().getItem()).getPermissionLevel(formationItemStackHandler.getControlToken());
    }


    public void tick(Level level, BlockPos blockPos, BlockState blockState){}


}
