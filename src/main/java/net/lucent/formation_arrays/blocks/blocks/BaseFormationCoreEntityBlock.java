package net.lucent.formation_arrays.blocks.blocks;

import com.mojang.serialization.MapCodec;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BaseFormationCoreEntityBlock extends BaseEntityBlock {

    public static final BooleanProperty FORMATION_CORE_STATE = BooleanProperty.create("formation_core_states");
    public final BiFunction<BlockPos,BlockState,AbstractFormationCoreBlockEntity> blockEntitySupplier;

    public BaseFormationCoreEntityBlock(Properties properties,BiFunction<BlockPos,BlockState,AbstractFormationCoreBlockEntity> blockEntitySupplier) {
        super(properties);
        this.blockEntitySupplier = blockEntitySupplier;
        this.registerDefaultState(this.defaultBlockState().setValue(FORMATION_CORE_STATE, false));

    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORMATION_CORE_STATE);

    }
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return blockEntitySupplier.apply(blockPos,blockState);

    }
}
