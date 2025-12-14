package net.lucent.formation_arrays.blocks.block_entities.formation_cores;


import net.lucent.formation_arrays.api.cores.ICoreEnergyContainer;
import net.lucent.formation_arrays.api.cores.IFormationCore;
import net.lucent.formation_arrays.api.formations.node.AbstractFormationNode;

import net.lucent.formation_arrays.api.formations.node.FormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/**
 * TODO add storage
 * key slots. access control slip slot, formation slot, fuel slot,jade slip slots per formation slot
 * make a wrapper that keeps track of these things
 * */
public abstract class AbstractFormationCoreBlockEntity extends BlockEntity implements IFormationCore {

    private final HashMap<UUID,IFormationNode> formationNodes = new HashMap<>();
    private final ICoreEnergyContainer energyContainer;
    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, ICoreEnergyContainer energyContainer) {
        super(type, pos, blockState);
        this.energyContainer = energyContainer;
    }

    public void runFormations(Level level){
        if(energyContainer.isEmpty()) return;
        for(UUID formationId : formationNodes.keySet()){
            tryRunFormation(formationId,level);
        }
    }

    private void tryRunFormation(UUID formation, Level level){
        if(energyContainer.tryDecreaseEnergy(formationNodes.get(formation).getEnergyCost())){
            formationNodes.get(formation).run(this,level,this.getBlockPos());
        }
    }

    public void addFormationNode(IFormationNode node){
        formationNodes.put(UUID.randomUUID(),node);
    };
    public void removeFormationNode(UUID id){
        formationNodes.remove(id);
    };

    public IFormationNode getFormationNode(UUID id){
        return formationNodes.get(id);
    }



    public FormationPort<?> getFormationPort(UUID formationId, String portId, String portType){
        if(!formationNodes.containsKey(formationId)) return null;
        return formationNodes.get(formationId).getFormationPort(portId);
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
