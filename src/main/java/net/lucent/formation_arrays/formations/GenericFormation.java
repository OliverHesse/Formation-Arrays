package net.lucent.formation_arrays.formations;

import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.formations.IFormationRenderer;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.formations.flags.FormationFlagConditionData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class GenericFormation implements IFormation {
    public interface IFormationNodeSupplier{
        IFormationNode run(IFormation formation,BlockPos pos,UUID formationId);
    }
    Component displayName;
    IFormationNodeSupplier supplier;
    private final IFormationRenderer formationRenderer;
    public GenericFormation(Component displayName,IFormationRenderer renderer,IFormationNodeSupplier supplier){
        this.supplier = supplier;
        this.displayName = displayName;
        this.formationRenderer = renderer;
    }

    @Override
    public IFormationRenderer getFormationRenderer() {
        return formationRenderer;
    }

    @Override
    public boolean hasRenderer() {
        return formationRenderer !=null;
    }

    @Override
    public IFormationNode getNewFormationNode(BlockPos corePos, UUID formationId) {
        return supplier.run(this,corePos,formationId);
    }

    @Override
    public List<FormationFlagConditionData> getFormationFlagConditions() {
        return List.of();
    }

    @Override
    public Component getFormationTitle() {
        return displayName;
    }

    @Override
    public Component getFormationDescription() {
        return null;
    }
}
