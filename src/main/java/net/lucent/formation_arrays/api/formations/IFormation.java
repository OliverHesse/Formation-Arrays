package net.lucent.formation_arrays.api.formations;

import net.lucent.formation_arrays.formations.flags.FormationFlagConditionData;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.minecraft.network.chat.Component;

import java.util.List;
/**
 *things i need to consider
 * formation cores will need an ownership token, this is either linked to a player or a sect
 * for formations to communicate these must first match
 * these ownership tokens will also be given an access level, a formation core can access all formation cores with an access level lower
 * than itself
 */
public interface IFormation {

    IFormationNode getFormationNode();
    List<FormationFlagConditionData> getFormationFlagConditions();
    Component getFormationTitle();
    Component getFormationDescription();

}
