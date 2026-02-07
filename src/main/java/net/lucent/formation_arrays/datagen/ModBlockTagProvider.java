package net.lucent.formation_arrays.datagen;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.ModBlocks;
import net.lucent.formation_arrays.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FormationArrays.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Blocks.FORMATION_CORE).add(
                ModBlocks.TIER_1_FORMATION_CORE.get(),
                ModBlocks.TIER_2_FORMATION_CORE.get()
        );
    }
}