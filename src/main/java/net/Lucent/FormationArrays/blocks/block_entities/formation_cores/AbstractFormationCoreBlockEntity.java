package net.Lucent.FormationArrays.blocks.block_entities.formation_cores;

import net.Lucent.FormationArrays.energy.FormationCoreQiContainer;
import net.Lucent.FormationArrays.formations.AbstractFormationNode;
import net.Lucent.FormationArrays.formations.interfaces.ISocket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/*
    TODO
    make sure to store the player that placed the core. so it can only link to others placed by the player.
    then add compatibility for popular team mods
 */
public abstract class AbstractFormationCoreBlockEntity extends BlockEntity {

    private final HashMap<UUID,AbstractFormationNode> nodeHashMap = new HashMap<>();
    public final FormationCoreQiContainer formationCoreQiContainer = new FormationCoreQiContainer(new BigDecimal(0));
    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }


    public ISocket<?> getOutputSocket(String socket_id, UUID formationId){
        return nodeHashMap.get(formationId).getOutputSocket(socket_id);
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
