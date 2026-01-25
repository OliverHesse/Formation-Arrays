package net.lucent.formation_arrays.gui.menus;

import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.util.inventory.EasyItemHandlerSlot;
import net.lucent.easygui.util.inventory.EasySlot;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.blocks.blocks.BaseFormationCoreEntityBlock;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.gui.ModMenuTypes;
import net.lucent.formation_arrays.network.server_bound.RequestNearbyFormationsPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFormationCoreMenu extends AbstractContainerMenu {
    private final AbstractFormationCoreBlockEntity coreBlockEntity;
    private final ContainerData data;
    private final Inventory inventory;
    public boolean canModify;
    protected AbstractFormationCoreMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, AbstractFormationCoreBlockEntity blockEntity, ContainerData dataSlot) {
        super(menuType, containerId);
        this.coreBlockEntity = blockEntity;
        this.inventory = inventory;
        this.data = dataSlot;
        FORMATION_INVENTORY_SLOT_COUNT = coreBlockEntity.getFormationItemStackHandler().getSlots();

        createSlots();
        if(blockEntity.getLevel().isClientSide()) PacketDistributor.sendToServer(new RequestNearbyFormationsPayload(blockEntity.getBlockPos()));
        addDataSlots(dataSlot);
    }


    public void createSlots(){
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        ItemStackHandler handler = coreBlockEntity.formationItemStackHandler;
        for(int i = 0; i<FORMATION_INVENTORY_SLOT_COUNT;i++){
            this.addSlot(new EasyItemHandlerSlot(handler,i,0,0,"formation_slot_"+i));
        }

    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new EasySlot(playerInventory, l + i * 9 + 9, 0, 0,"p"+(l + i * 9 + 9)));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new EasySlot(playerInventory, i,0, 0,"p"+i));
        }
    }
    public Level getLevel(){return this.coreBlockEntity.getLevel();}
    public AbstractFormationCoreBlockEntity getCoreBlockEntity(){
        return coreBlockEntity;
    }
    public ContainerData getData(){
        return data;
    }
    public Inventory getInventory(){
        return inventory;
    }
    public FormationCoreItemStackHandler getItemStackHandler(){
        return getCoreBlockEntity().getFormationItemStackHandler();
    }
    @Override
    public void setCarried(ItemStack stack) {
        if(canModify) super.setCarried(stack);
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if(!(player instanceof ServerPlayer)) return false;
        BlockState state = coreBlockEntity.getBlockState();

        coreBlockEntity.getLevel().setBlock(coreBlockEntity.getBlockPos(),state.cycle(BaseFormationCoreEntityBlock.FORMATION_CORE_STATE), Block.UPDATE_ALL_IMMEDIATE);
        return true;
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if(!inventory.player.level().isClientSide){
            coreBlockEntity.syncCoreSlots(inventory.player,true);
        }
    }
    // QUICK MOVE LOGIC
    public  final int HOTBAR_SLOT_COUNT = 9;
    public  final int PLAYER_INVENTORY_ROW_COUNT = 3;
    public  final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    public  final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    public  final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    public  final int VANILLA_FIRST_SLOT_INDEX = 0;
    public  final int FORMATION_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    public final int FORMATION_INVENTORY_SLOT_COUNT;

    @Override
    public ItemStack quickMoveStack(Player player, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();


        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // try to move vanilla item into
            Integer newIndex = getValidSlot(sourceStack);
            if(newIndex != null){
                //move too new slot
                if(!moveItemStackTo(sourceStack,FORMATION_FIRST_SLOT_INDEX,FORMATION_FIRST_SLOT_INDEX+FORMATION_INVENTORY_SLOT_COUNT,false)){
                    return ItemStack.EMPTY;
                }
            }else return ItemStack.EMPTY;
        } else if (pIndex < FORMATION_FIRST_SLOT_INDEX + FORMATION_INVENTORY_SLOT_COUNT) {
            // This is a slot in the formation core. just move to inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    public Integer getValidSlot(ItemStack stack){
        for(int i = 0; i<FORMATION_INVENTORY_SLOT_COUNT;i++){
            if(getItemStackHandler().isItemValid(i,stack)) return i;
        }
        return null;
    }


}
