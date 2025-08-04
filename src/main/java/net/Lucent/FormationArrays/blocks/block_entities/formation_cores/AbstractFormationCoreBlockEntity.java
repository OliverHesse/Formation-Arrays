package net.Lucent.FormationArrays.blocks.block_entities.formation_cores;

import net.Lucent.FormationArrays.formations.AbstractFormationNode;
import net.Lucent.FormationArrays.formations.interfaces.ISocket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AbstractFormationCoreBlockEntity extends BlockEntity {
    public List<AbstractFormationNode> nodes = new ArrayList<>();
    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }



    public ISocket<?> getOutputSocket(String socket_id, int formationId){
        return nodes.get(formationId).getOutputSocket(socket_id);
    }
}
