package net.lucent.formation_arrays.blocks.block_entities;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.ModBlocks;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.Tier1FormationCoreBlockEntity;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.Tier2FormationCoreBlockEntity;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.formations.energy_containers.CappedEnergyContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FormationArrays.MOD_ID);

    public static  final Supplier<BlockEntityType<Tier1FormationCoreBlockEntity>> TIER_1_FORMATION_CORE_BE =
            BLOCK_ENTITIES.register("tier_1_formation_core_be",() -> BlockEntityType.Builder.of(
                    (blockPos,blockState)->new Tier1FormationCoreBlockEntity(
                            blockPos,
                            blockState,
                            new CappedEnergyContainer(100000),
                            new FormationCoreItemStackHandler(1,1,3)
                    ), ModBlocks.TIER_1_FORMATION_CORE.get()).build(null));
    public static  final Supplier<BlockEntityType<Tier2FormationCoreBlockEntity>> TIER_2_FORMATION_CORE_BE =
            BLOCK_ENTITIES.register("tier_2_formation_core_be",() -> BlockEntityType.Builder.of(
                    (blockPos,blockState)->new Tier2FormationCoreBlockEntity(
                            blockPos,
                            blockState,
                            new CappedEnergyContainer(100000),
                            new FormationCoreItemStackHandler(1,3,3)
                    ), ModBlocks.TIER_2_FORMATION_CORE.get()).build(null));

    public static void register(IEventBus eventBus){

        BLOCK_ENTITIES.register(eventBus);

    }
}
