package net.lucent.formation_arrays.formations;

import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.formations.flags.FormationFlagConditionData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class GenericFormation implements IFormation {
    public interface IFormationNodeSupplier{
        IFormationNode run(BlockPos pos,UUID formationId);
    }
    IFormationNodeSupplier supplier;
    public GenericFormation(IFormationNodeSupplier supplier){
        this.supplier = supplier;
    }

    @Override
    public IFormationNode getNewFormationNode(BlockPos corePos, UUID formationId) {
        return supplier.run(corePos,formationId);
    }

    @Override
    public List<FormationFlagConditionData> getFormationFlagConditions() {
        return List.of();
    }

    @Override
    public Component getFormationTitle() {
        return null;
    }

    @Override
    public Component getFormationDescription() {
        return null;
    }
}
