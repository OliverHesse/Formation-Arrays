package net.lucent.formation_arrays.gui.menus;

import net.lucent.easygui.elements.inventory.DisplaySlot;
import net.lucent.easygui.interfaces.IEasyGuiScreen;
import net.lucent.easygui.interfaces.events.Hoverable;
import net.lucent.easygui.util.inventory.EasyItemHandlerSlot;
import net.lucent.easygui.util.inventory.EasySlot;
import net.lucent.formation_arrays.blocks.ModBlocks;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.blocks.blocks.BaseFormationCoreEntityBlock;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.gui.ModMenuTypes;
import net.lucent.formation_arrays.network.server_bound.RequestNearbyCoresPayload;
import net.lucent.formation_arrays.network.server_bound.RequestNearbyFormationsPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class Tier1FormationCoreMenu extends AbstractFormationCoreMenu{

    public final AbstractFormationCoreBlockEntity coreBlockEntity;
    public final ContainerData data;
    public final Inventory inventory;
    public boolean canModify = true;

    public Tier1FormationCoreMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId,inventory, (AbstractFormationCoreBlockEntity) inventory.player.level().getBlockEntity(extraData.readBlockPos()),new SimpleContainerData(1));
    }
    public Tier1FormationCoreMenu(int containerId, Inventory inventory, AbstractFormationCoreBlockEntity blockEntity, ContainerData dataSlot) {
        super(ModMenuTypes.TIER_1_FORMATION_CORE_MENU.get(),containerId);
        System.out.println("trying to create menu");


        this.inventory = inventory;

        this.coreBlockEntity = blockEntity;
        createSlots();
        this.data = dataSlot;

        addPlayerInventory(inventory);
        addCoreInventory();
        if(blockEntity.getLevel().isClientSide()) PacketDistributor.sendToServer(new RequestNearbyFormationsPayload(blockEntity.getBlockPos()));
        addDataSlots(dataSlot);
    }


    public void createSlots(){
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        ItemStackHandler handler = coreBlockEntity.formationItemStackHandler;
        this.addSlot(new EasyItemHandlerSlot(handler,0,0,0,"c"+0));

        this.addSlot(new EasyItemHandlerSlot(handler,1,0,0,"c"+1));

        this.addSlot(new EasyItemHandlerSlot(handler,2,0,0,"c"+2));

        this.addSlot(new EasyItemHandlerSlot(handler,3,0,0,"c"+3));
        this.addSlot(new EasyItemHandlerSlot(handler,4,0,0,"c"+4));
        this.addSlot(new EasyItemHandlerSlot(handler,5,0,0,"c"+5));
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
    public AbstractFormationCoreBlockEntity getCoreBlockEntity(){return this.coreBlockEntity;}
    public Inventory getInventory(){return this.inventory;}
    public FormationCoreItemStackHandler getItemStackHandler(){return coreBlockEntity.formationItemStackHandler;}
    public void addCoreInventory(){
        /*
        ItemStackHandler handler = coreBlockEntity.formationItemStackHandler;
        this.addSlot(new SlotItemHandler(handler,0,155,5));

        this.addSlot(new SlotItemHandler(handler,1,8,76));

        this.addSlot(new SlotItemHandler(handler,2,80,18));

        this.addSlot(new SlotItemHandler(handler,3,80,40));
        this.addSlot(new SlotItemHandler(handler,4,80,58));
        this.addSlot(new SlotItemHandler(handler,5,80,76));

         */
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if(!inventory.player.level().isClientSide){
            coreBlockEntity.syncCoreSlots(inventory.player,true);
        }
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
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(coreBlockEntity.getLevel(), coreBlockEntity.getBlockPos()),
                player, ModBlocks.TIER_1_FORMATION_CORE.get());
    }

}
