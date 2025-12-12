package net.lucent.formation_arrays.blocks.block_entities.formation_cores;


import net.lucent.formation_arrays.api.formations.node.AbstractFormationNode;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.UUID;
/*
    TODO
    make sure to store the player that placed the core. so it can only link to others placed by the player.
    then add compatibility for popular team mods
 */
public abstract class AbstractFormationCoreBlockEntity extends BlockEntity {

    private final HashMap<UUID,AbstractFormationNode> nodeHashMap = new HashMap<>();
    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }



    public void addFormationNode(AbstractFormationNode node){
        nodeHashMap.put(UUID.randomUUID(),node);
    };
    public void removeFormationNode(UUID id){
        nodeHashMap.remove(id);
    };

    public AbstractFormationNode getFormationNode(UUID id){
        return nodeHashMap.get(id);
    }



}
