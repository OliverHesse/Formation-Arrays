package net.lucent.formation_arrays.formations;

import net.lucent.formation_arrays.api.formations.IFormation;
import net.lucent.formation_arrays.api.capability.IFormationHolder;
import net.lucent.formation_arrays.blocks.block_entities.formation_cores.AbstractFormationCoreBlockEntity;
import net.lucent.formation_arrays.api.capability.Capabilities;
import net.lucent.formation_arrays.capabilities.ModCapabilities;
import net.lucent.formation_arrays.util.ModTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormationCoreItemStackHandler extends ItemStackHandler {

    public final int ACCESS_CONTROL_TOKEN_SLOT = 0;
    public final int FUEL_SLOTS;
    public final int MAX_FORMATIONS;
    public final int JADE_SLIP_SLOTS_PER_FORMATION;
    public List<Integer> FORMATION_SLOTS = new ArrayList<>();
    public Set<Integer> JADE_SLIP_SLOTS = new HashSet<>();
    public AbstractFormationCoreBlockEntity entity;

    public FormationCoreItemStackHandler(int fuelSlots, int maxFormations, int jadeSlipSlotsPerFormation){
        super(1+fuelSlots+maxFormations+maxFormations*jadeSlipSlotsPerFormation);
        FUEL_SLOTS = fuelSlots;
        MAX_FORMATIONS = maxFormations;
        JADE_SLIP_SLOTS_PER_FORMATION = jadeSlipSlotsPerFormation;

        //Pre-calculate the slots for formations and jade slips
        for(int i =0;i<MAX_FORMATIONS;i++){
            int formationSlotStart = FUEL_SLOTS+i*(JADE_SLIP_SLOTS_PER_FORMATION+1)+1;
            FORMATION_SLOTS.add(formationSlotStart);
            for(int j =0;j<JADE_SLIP_SLOTS_PER_FORMATION;j++){
                JADE_SLIP_SLOTS.add(formationSlotStart+j+1);
            }
        }
    }

    public FormationCoreItemStackHandler( int maxFormations, int jadeSlipSlotsPerFormation){
        this(1,maxFormations,jadeSlipSlotsPerFormation);
    }


    @Override
    public int getSlotLimit(int slot) {
        if(slot == ACCESS_CONTROL_TOKEN_SLOT || FORMATION_SLOTS.contains(slot) || JADE_SLIP_SLOTS.contains(slot)) return 1;
        return super.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        //WHERE TAGS AND instance of comes in
        if(slot == ACCESS_CONTROL_TOKEN_SLOT) return stack.getCapability(Capabilities.ACCESS_TOKEN_CAPABILITY) != null;
        if(JADE_SLIP_SLOTS.contains(slot)) return stack.is(ModTags.Items.JADE_SLIP);
        if(slot >= 1 && slot <= FUEL_SLOTS) return stack.getCapability(Capabilities.FORMATION_FUEL_CAPABILITY) != null;
        if(FORMATION_SLOTS.contains(slot)) return stack.getCapability(Capabilities.FORMATION_HOLDER_CAPABILITY) != null;
        return super.isItemValid(slot, stack);
    }
    public ItemStack getFirstAvailableFuelItem(){
        for(int i = 1;i<1+FUEL_SLOTS;i++){
            if(!getStackInSlot(i).equals(ItemStack.EMPTY)) return getStackInSlot(i);
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getControlToken(){
        return getStackInSlot(ACCESS_CONTROL_TOKEN_SLOT);
    }
    public ItemStack getFormationItemStack(int slot){
        if(slot >=0 && slot < MAX_FORMATIONS) return getStackInSlot(FORMATION_SLOTS.get(slot));
        return ItemStack.EMPTY;
    }
    public List<ItemStack> getFormationJadeSlips(int slot){
        if(slot < 0 || slot >= MAX_FORMATIONS) return List.of();
        int formationSlot = FORMATION_SLOTS.get(slot);
        ArrayList<ItemStack> jadeSlips = new ArrayList<>();
        for(int i = formationSlot+1;i<=formationSlot+JADE_SLIP_SLOTS_PER_FORMATION;i++){
            jadeSlips.add(getStackInSlot(i));
        }
        return jadeSlips;
    }
    public IFormation getFormation(int slot){
        ItemStack item = getFormationItemStack(slot);
        if(item.equals(ItemStack.EMPTY)) return null;
        IFormationHolder formationHolder = item.getCapability(Capabilities.FORMATION_HOLDER_CAPABILITY);
        if(formationHolder ==null) return null;
        return formationHolder.getFormation(item);
    }
        @Override
        protected void onContentsChanged(int slot) {
            entity.setChanged();

            if (!entity.getLevel().isClientSide()) {
                entity.getLevel().sendBlockUpdated(entity.getBlockPos(),entity.getBlockState(),entity.getBlockState(),3);
                if(FORMATION_SLOTS.contains(slot)){
                    entity.updateFormationSlot(FORMATION_SLOTS.indexOf(slot));
                }
            }
        }




}
