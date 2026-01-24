package net.lucent.formation_arrays.blocks.blocks;

import com.mojang.serialization.MapCodec;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
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
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORMATION_CORE_STATE);

    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if(!level.isClientSide()){
            FormationArrays.getCoreManager().addCore(pos);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);

    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {

        BlockEntity entity = blockEntitySupplier.apply(blockPos,blockState);

        return entity;

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType,blockEntityType,
                (level1, blockPos, blockState, blockEntity) ->
                        ( (AbstractFormationCoreBlockEntity)blockEntity).tick(level1, blockPos, blockState));
    }

    //TODO Modify to work regardless off which block was pressed;
    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof AbstractFormationCoreBlockEntity formationCoreBlockEntity) {

            if (!level.isClientSide()) {
                player.openMenu(new SimpleMenuProvider(formationCoreBlockEntity, Component.literal("Formation Core")), pos);

            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }
}
