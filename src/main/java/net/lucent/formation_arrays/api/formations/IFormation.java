package net.lucent.formation_arrays.api.formations;

import net.lucent.formation_arrays.formations.flags.FormationFlagConditionData;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.minecraft.network.chat.Component;

import java.util.List;
/**
 * this is what will be stored in the registry
 */
public interface IFormation {

    IFormationNode getNewFormationNode();
    List<FormationFlagConditionData> getFormationFlagConditions();
    Component getFormationTitle();
    Component getFormationDescription();

}
