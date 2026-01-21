package net.lucent.formation_arrays.blocks.block_entities.formation_cores;


import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.api.capability.IFormationFuel;
import net.lucent.formation_arrays.api.cores.ICoreEnergyContainer;
import net.lucent.formation_arrays.api.cores.IFormationCore;

import net.lucent.formation_arrays.api.formations.flags.IFormationFlagConditionData;
import net.lucent.formation_arrays.api.formations.node.IFormationPort;
import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.capability.IAccessControlToken;
import net.lucent.formation_arrays.api.capability.IFormationHolder;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.lucent.formation_arrays.blocks.blocks.BaseFormationCoreEntityBlock;
import net.lucent.formation_arrays.api.capability.Capabilities;
import net.lucent.formation_arrays.formations.FormationCoreItemStackHandler;
import net.lucent.formation_arrays.formations.node.CoreNodeSlot;
import net.lucent.formation_arrays.network.client_bound.SyncFormationCoreSlotData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * TODO add storage
 * key slots. access control slip slot, formation slot, fuel slot,jade slip slots per formation slot
 * make a wrapper that keeps track of these things
 * */
public abstract class AbstractFormationCoreBlockEntity extends BlockEntity implements IFormationCore, MenuProvider {

    public final CoreNodeSlot[] formationNodeSlots;

    public final ICoreEnergyContainer energyContainer;
    public final FormationCoreItemStackHandler formationItemStackHandler;
    public ContainerData dataSlot = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index){
                case 0 ->  (int)energyContainer.getCurrentEnergy();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        FormationArrays.getCoreManager().removeCore(getBlockPos());
    }

    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, ICoreEnergyContainer energyContainer, FormationCoreItemStackHandler itemStackHandler) {
        super(type, pos, blockState);
        this.energyContainer = energyContainer;
        this.formationItemStackHandler = itemStackHandler;
        this.formationItemStackHandler.entity = this;

        formationNodeSlots = new CoreNodeSlot[itemStackHandler.MAX_FORMATIONS];
        for(int i=0;i<formationNodeSlots.length;i++){
            formationNodeSlots[i] = CoreNodeSlot.createEmpty();
        }
        FormationArrays.getCoreManager().addCore(pos);
        if(FMLLoader.getDist() == Dist.CLIENT) NeoForge.EVENT_BUS.addListener(this::onRenderLevel);
    }

    public FormationCoreItemStackHandler getFormationItemStackHandler(){
        return formationItemStackHandler;
    }



    public void updateFormationSlot(int slot){
        ItemStack itemStack = formationItemStackHandler.getFormationItemStack(slot);
        if(itemStack == null || itemStack.equals(ItemStack.EMPTY)) {
            formationNodeSlots[slot].empty();
            sendSyncPacket();
            return;
        }
        //check if it is the same itemStack Obj if true no change so return
        if(formationNodeSlots[slot].getItemStack() == itemStack) return;

        //we have a new item
        formationNodeSlots[slot].setFormationNode(nodeFromItemStack(slot));
        formationNodeSlots[slot].setItemStack(itemStack);
        setChanged();
        sendSyncPacket();

    }

    public boolean isFormationSlotValid(int slot){
        updateFormationSlot(slot);
        return formationNodeSlots[slot].getFormationNode() != null;
    }


    @Override
    public ICoreEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    public void tryRecharge(){
        if(energyContainer.getCurrentEnergy() == energyContainer.getMaxEnergy()) return;
        ItemStack fuelItem =  getFormationItemStackHandler().getFirstAvailableFuelItem();
        IFormationFuel fuel = fuelItem.getCapability(Capabilities.FORMATION_FUEL_CAPABILITY);
        if(fuel == null) return;
        if(getEnergyContainer().getCurrentEnergy()+ fuel.estimateFuel(fuelItem) > getEnergyContainer().getMaxEnergy()) return;
        getEnergyContainer().increaseEnergy(fuel.getFuel(fuelItem));
    }





    public IFormationNode getFormationNode(UUID id){
        System.out.println("trying to find foramtion with id"+id.toString());
        for(CoreNodeSlot slot : formationNodeSlots){
            if(slot == null) continue;
            System.out.println("checking slot : "+slot.getFormationNode().getFormationId());
            if(slot.getFormationNode().getFormationId().equals(id)) return slot.getFormationNode();
        }
        return null;
    }

    /*
            generates a new formation node from the item stack in slot

            even if the formationSlots already has an instance of this node, this node has a different UUID
            meant for client side calculations
            if used on client side to reference server refer to slot
     */
    public  IFormationNode nodeFromItemStack(int slot){
        return getFormationItemStackHandler().getFormation(slot).getNewFormationNode(getBlockPos(),UUID.randomUUID());
    }

    public IFormationNode getFormationNode(int slot){
        updateFormationSlot(slot);
        return formationNodeSlots[slot].getFormationNode();
    }

    @Override
    public IFormationPort<?> getFormationPort(UUID formationId, String portId){

        return getFormationNode(formationId).getFormationPort(portId);
    }

    @Override
    public ResourceLocation getFormationRegistryId(UUID formationId) {

        return  ((IFormationHolder) getFormationItemStack(formationId).getItem()).getFormationResourceLocation(getFormationItemStack(formationId));

    }

    @Override
    public ItemStack getFormationItemStack(UUID formation){
        for(CoreNodeSlot slot: formationNodeSlots){
            if(slot.getFormationNode() == null )continue;
            if(slot.getUUID().equals(formation)) return slot.getItemStack();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public List<ItemStack> getFormationJadeSlips(UUID formation){
        for(int i = 0;i<formationNodeSlots.length;i++){
            if(formationNodeSlots[i].getFormationNode() == null) continue;
            if(formationNodeSlots[i].getUUID().equals(formation)) return formationItemStackHandler.getFormationJadeSlips(i);
        }
        return List.of();
    }

    @Override
    public String getOwnerId(){
        ItemStack itemStack = formationItemStackHandler.getControlToken();
        if(itemStack == ItemStack.EMPTY) return null;
        IAccessControlToken controlTokenCapability = itemStack.getCapability(Capabilities.ACCESS_TOKEN_CAPABILITY);
        if(controlTokenCapability == null) return null;
        return controlTokenCapability.getOwnerId(itemStack);
    }

    @Override
    public int getPermissionLevel(){
        ItemStack itemStack = formationItemStackHandler.getControlToken();
        if(itemStack == ItemStack.EMPTY) return 0;
        IAccessControlToken controlTokenCapability = itemStack.getCapability(Capabilities.ACCESS_TOKEN_CAPABILITY);
        if(controlTokenCapability == null) return 0;
        return controlTokenCapability.getPermissionLevel(itemStack);

    }

    //TODO
    @Override
    public List<IFormationNode> getFormationNodes() {
        List<IFormationNode> formationNodes = new ArrayList<>();
        for(CoreNodeSlot slot: formationNodeSlots){
            if(slot.getFormationNode() != null) formationNodes.add(slot.getFormationNode());
        }
        return formationNodes;
    }

    @Override
    public List<UUID> getFormationNodeIDs() {
        List<UUID> uuidList = new ArrayList<>();
        for(CoreNodeSlot slot: formationNodeSlots){
            if(slot.getFormationNode() != null)uuidList.add(slot.getUUID());
        }
        return uuidList;
    }



    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        syncCoreSlots(player,false);
        return null;
    }

    public void sendSyncPacket(){
        for(int slot = 0; slot<formationNodeSlots.length;slot++) {
            CoreNodeSlot nodeSlot = formationNodeSlots[slot];
            PacketDistributor.sendToAllPlayers(new SyncFormationCoreSlotData(getBlockPos(),nodeSlot.getFormationNode(),slot));
        }
    }


    public void syncCoreSlots(Player player,boolean consumeDirty){
        for(int slot = 0; slot<formationNodeSlots.length;slot++){
            CoreNodeSlot nodeSlot = formationNodeSlots[slot];

            if(consumeDirty){
                if(nodeSlot.consumeDirty()) nodeSlot.sendSyncPacketToPlayer(player,this,slot);
            }else {
                nodeSlot.sendSyncPacketToPlayer(player,this,slot);
            }

        }
    }

    @Override
    public int getDetectionRadius() {
        return 0;
    }

    public void runFormationNode(Level level,IFormationNode node){
        for(IFormationFlagConditionData conditionData : node.getFormation().getFormationFlagConditions()){
            if(!conditionData.isFlagValid(level,getBlockPos())) return;
        }
        node.tick(this,getFormationJadeSlips(node.getFormationId()));
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState){
        tryRecharge();

        /*
        if(!getEnergyContainer().isEmpty()) {
            level.setBlock(blockPos, blockState.setValue(BaseFormationCoreEntityBlock.FORMATION_CORE_STATE,true), Block.UPDATE_ALL_IMMEDIATE);
        }

         */
        if(blockState.getValue(BaseFormationCoreEntityBlock.FORMATION_CORE_STATE)){
            //ACTIVE SO RUN

            if(level.isClientSide()){

                for(CoreNodeSlot slot : formationNodeSlots){
                    if(slot.getFormationNode() == null) continue;
                    runFormationNode(level,slot.getFormationNode());
                    slot.getFormationNode().setActiveLastTick(true);
                }
            }else {
                for(CoreNodeSlot slot : formationNodeSlots){
                    if(slot.getFormationNode() == null) continue;

                    if(tryBurnEnergy(slot.getFormationNode().getEnergyCost())) {
                        runFormationNode(level,slot.getFormationNode());
                        slot.getFormationNode().setActiveLastTick(true);
                    }
                    else  {


                        level.setBlock(blockPos, blockState.setValue(BaseFormationCoreEntityBlock.FORMATION_CORE_STATE,false), Block.UPDATE_ALL_IMMEDIATE);
                    }
                }
            }
        }
        else {
            //TODO make more efficient by adding activeLastTick to core so this only needs to run once
            for(CoreNodeSlot slot : formationNodeSlots){
                if(slot.getFormationNode() == null) continue;
                if(slot.getFormationNode().activeLastTick()) slot.getFormationNode().deactivate(this);
                slot.getFormationNode().setActiveLastTick(false);
            }

        }
    }


    @OnlyIn(Dist.CLIENT)
    public void onRenderLevel(RenderLevelStageEvent event) {
        if(getBlockState().getValue(BaseFormationCoreEntityBlock.FORMATION_CORE_STATE)){
            //ACTIVE SO RUN
            for(CoreNodeSlot slot : formationNodeSlots){
                if(slot.getFormationNode() == null) continue;
                if(slot.getFormationNode().getFormation().getFormationRenderer() == null) continue;
                FormationRegistry.FormationRenderers.RENDERERS_REGISTRY.get(
                        slot.getFormationNode().getFormation().getFormationRenderer()
                ).render(event,getBlockPos(),slot.getFormationNode());
            }
        }
    }





        @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        System.out.println("SAVING BE STORAGE");
        tag.put("inventory",formationItemStackHandler.serializeNBT(registries));
        ListTag coreSlots = new ListTag();
        for(int i =0;i<formationNodeSlots.length;i++){
            CompoundTag slotTag = new CompoundTag();

            CoreNodeSlot slot = formationNodeSlots[i];
            if(slot.getFormationNode() == null) {
                coreSlots.add(new CompoundTag());
                continue;
            };
            slotTag.putUUID("uuid",slot.getUUID());
            slotTag.putString("registry_object",FormationRegistry.FORMATION_REGISTRY.getKey(formationItemStackHandler.getFormation(i)).toString());
            slotTag.put("node_data",slot.getFormationNode().save(registries));
            coreSlots.add(slotTag);
        }
        tag.put("core_slot_data",coreSlots);
        //TODO add qi
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        formationItemStackHandler.deserializeNBT(registries,tag.getCompound("inventory"));
        ListTag coreData = tag.getList("core_slot_data", CompoundTag.TAG_COMPOUND);
        for(int i = 0;i<formationNodeSlots.length;i++){
            CompoundTag data = coreData.getCompound(i);
            if(data.hasUUID("uuid")){
                UUID uuid = data.getUUID("uuid");
                ResourceLocation formationRegistryKey = ResourceLocation.bySeparator(data.getString("registry_object"),':');
                IFormationNode node = FormationRegistry.FORMATION_REGISTRY.get(formationRegistryKey).getNewFormationNode(getBlockPos(),uuid);
                node.read(data.getCompound("node_data"),registries);
                formationNodeSlots[i].setFormationNode(node);
                ItemStack itemStack = formationItemStackHandler.getFormationItemStack(i);
                formationNodeSlots[i].setItemStack(itemStack);
            }else formationNodeSlots[i].empty();

            updateFormationSlot(i);
        }
        //TODO add qi
    }
}
