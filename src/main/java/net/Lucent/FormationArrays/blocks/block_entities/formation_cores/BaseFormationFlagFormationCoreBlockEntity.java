package net.Lucent.FormationArrays.blocks.block_entities.formation_cores;

import net.Lucent.FormationArrays.formations.AbstractFormationNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseFormationFlagFormationCoreBlockEntity extends AbstractFormationCoreBlockEntity{

    public int MAX_FORMATION_NODES = 1;

    public List<FormationNodeSlotData> formationSlots = new ArrayList<>(){{add(new FormationNodeSlotData());}};

    public BaseFormationFlagFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);

    }
    public BaseFormationFlagFormationCoreBlockEntity setMaxFormationNodes(int maxFormationNodes){
        MAX_FORMATION_NODES = maxFormationNodes;
        clearFormationSlots();
        for(int i = 0;i<MAX_FORMATION_NODES;i++){
            formationSlots.add(new FormationNodeSlotData());
        }
        return this;
    }
    public void clearFormationSlots(){
        for(FormationNodeSlotData slotData : formationSlots){
            if(slotData.nodeId != null){
                removeFormationNode(slotData.nodeId);
            }
        }
        formationSlots.clear();
    }
    public static class FormationNodeSlotData{
        public ItemStack itemStack;
        public UUID nodeId;
        public int slotRotation;

        public FormationNodeSlotData(){}
    }

}
