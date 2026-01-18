package net.lucent.formation_arrays.api.formations.node;

import net.minecraft.core.BlockPos;

import java.util.UUID;

public record AvailableFormation(String ownerId,int permissionLevel,IFormationNode node, BlockPos corePos){
}
