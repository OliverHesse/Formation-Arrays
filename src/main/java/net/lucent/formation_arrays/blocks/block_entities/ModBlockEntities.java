package net.lucent.formation_arrays.blocks.block_entities;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FormationArrays.MOD_ID);


    public static void register(IEventBus eventBus){

        BLOCK_ENTITIES.register(eventBus);

    }
}
