package net.lucent.formation_arrays.blocks.block_entities.formation_cores;


import net.lucent.formation_arrays.api.cores.ICoreEnergyContainer;
import net.lucent.formation_arrays.api.cores.IFormationCore;

import net.lucent.formation_arrays.api.formations.node.FormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.formations.node.CoreNodeSlot;
import net.minecraft.core.BlockPos;
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

    private final HashMap<UUID, CoreNodeSlot> formationNodes = new HashMap<>();
    private final List<UUID> formationNodesToRemove = new ArrayList<>();
    private final HashMap<UUID,CoreNodeSlot> formationNodesToAdd = new HashMap<>();

    private final ICoreEnergyContainer energyContainer;
    private final FormationCoreItemStackHandler formationItemStackHandler;
    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, ICoreEnergyContainer energyContainer,FormationCoreItemStackHandler itemStackHandler) {
        super(type, pos, blockState);
        this.energyContainer = energyContainer;
        this.formationItemStackHandler = itemStackHandler;
    }

    public void clearRemoveQueue(){
        for(UUID oldId : formationNodesToRemove){
            formationNodes.remove(oldId);
        }
        formationNodesToRemove.clear();
    }
    public void clearInclusionMap(){
        for(UUID nodeSlot : formationNodesToAdd.keySet()){
            formationNodes.put(
                    nodeSlot,
                    formationNodesToAdd.get(nodeSlot)
            );
        }
        formationNodesToRemove.clear();
    }

    public void updateFormationSlot(UUID oldUUID){
        CoreNodeSlot oldSlot = formationNodes.get(oldUUID);
        formationNodesToRemove.add(oldUUID);
        formationNodesToAdd.put(
                UUID.randomUUID(),
                CoreNodeSlot.fromItemStack(
                        oldSlot.slot(),
                        formationItemStackHandler.getFormationItemStack(oldSlot.slot())
                )
        );

    }
    public void run(Level level){
        for(UUID formationId : formationNodes.keySet()){
            if(isFormationSlotValid(formationId)){
                tryRunFormation(formationId,level);
            }
        }
    }
    public boolean isFormationSlotValid(UUID formationUUID){
        if(!formationNodes.containsKey(formationUUID))return false;
        CoreNodeSlot nodeSlot = formationNodes.get(formationUUID);
        if(formationItemStackHandler.getFormationItemStack(nodeSlot.slot()) == nodeSlot.itemStack()) return true;
        updateFormationSlot(formationUUID);
        return false;
    }

    @Override
    public ICoreEnergyContainer getEnergyContainer() {
        return energyContainer;
    }


    public void runFormations(Level level){
        if(energyContainer.isEmpty()) return;
        for(UUID formationId : formationNodes.keySet()){
            tryRunFormation(formationId,level);
        }
    }

    private void tryRunFormation(UUID formation, Level level){
        if(tryBurnEnergy(getFormationNode(formation).getEnergyCost())){
            getFormationNode(formation).run(this,level,this.getBlockPos());
        }
    }

    public void removeFormationNode(UUID id){
        formationNodes.remove(id);
    };

    public IFormationNode getFormationNode(UUID id){
        return formationNodes.get(id).node();
    }



    public FormationPort<?> getFormationPort(UUID formationId, String portId, String portType){
        if(!formationNodes.containsKey(formationId)) return null;
        return getFormationNode(formationId).getFormationPort(portId);
    }
    //TODO
    public ItemStack getFormationItemStack(UUID formation){
        return null;
    }
    //TODO
    public List<ItemStack> getFormationJadeSlips(UUID formation){
        return null;
    }
    //TODO
    public String getOwnerId(){
        return null;
    }
    //TODO
    public int getPermissionLevel(){
        return 0;
    }

}
