package net.lucent.formation_arrays.blocks;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.block_entities.ModBlockEntities;
import net.lucent.formation_arrays.blocks.blocks.BaseFormationCoreEntityBlock;
import net.lucent.formation_arrays.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FormationArrays.MOD_ID);




    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockItems(name,toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItems(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name,() -> new BlockItem(block.get(),new Item.Properties()));
    }


    public static void register(IEventBus eventBus){

        BLOCKS.register(eventBus);
    }
}
