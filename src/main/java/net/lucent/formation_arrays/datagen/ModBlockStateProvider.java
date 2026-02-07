package net.lucent.formation_arrays.datagen;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider  extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FormationArrays.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        //TODO change because array core will be a block entity
        blockWithItem(ModBlocks.TIER_1_FORMATION_CORE);
        blockWithItem(ModBlocks.TIER_2_FORMATION_CORE);
    }



    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(),cubeAll(deferredBlock.get()));

    }
}