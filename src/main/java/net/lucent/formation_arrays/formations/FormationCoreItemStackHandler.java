package net.lucent.formation_arrays.formations;

import net.lucent.formation_arrays.api.items.IAccessControlToken;
import net.lucent.formation_arrays.util.ModTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;
import java.util.Set;

public class FormationCoreItemStackHandler extends ItemStackHandler {

    public final int ACCESS_CONTROL_TOKEN_SLOT;
    public final int FUEL_SLOT_START;
    public final int FUEL_SLOT_END;
    public final int MAX_FORMATIONS;
    public final int JADE_SLIP_SLOTS_PER_FORMATION;
    public Set<Integer> FORMATION_SLOTS;
    public Set<Integer> JADE_SLIP_SLOTS;
    public FormationCoreItemStackHandler(int accessControlTokenSlot, int fuelSlotStart, int fuelSlotEnd, int maxFormations, int jadeSlipSlotsPerFormation){
        ACCESS_CONTROL_TOKEN_SLOT = accessControlTokenSlot;
        FUEL_SLOT_START = fuelSlotStart;
        FUEL_SLOT_END = fuelSlotEnd;
        MAX_FORMATIONS = maxFormations;
        JADE_SLIP_SLOTS_PER_FORMATION = jadeSlipSlotsPerFormation;

        //Pre-calculate the slots for formations and jade slips
        for(int i =0;i<MAX_FORMATIONS;i++){
            int formationSlotStart = fuelSlotEnd+i*(JADE_SLIP_SLOTS_PER_FORMATION+1)+1;
            FORMATION_SLOTS.add(formationSlotStart);
            for(int j =0;j<JADE_SLIP_SLOTS_PER_FORMATION;j++){
                JADE_SLIP_SLOTS.add(formationSlotStart+j+1);
            }
        }
    }

    public FormationCoreItemStackHandler(int accessControlTokenSlot, int maxFormations, int jadeSlipSlotsPerFormation){
        this(accessControlTokenSlot,1,1,maxFormations,jadeSlipSlotsPerFormation);
    }


    @Override
    public int getSlotLimit(int slot) {
        if(slot == ACCESS_CONTROL_TOKEN_SLOT || FORMATION_SLOTS.contains(slot) || JADE_SLIP_SLOTS.contains(slot)) return 1;
        return super.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        //WHERE TAGS AND instance of comes in
        if(slot == ACCESS_CONTROL_TOKEN_SLOT) return stack.getItem() instanceof IAccessControlToken;
        if(JADE_SLIP_SLOTS.contains(slot)) return stack.is(ModTags.Items.JADE_SLIP);
        if(slot >= FUEL_SLOT_START && slot <= FUEL_SLOT_END) return stack.is(ModTags.Items.FORMATION_CORE_FUEL);
        //TODO check for formation item
        //TODO need to actually create a formation item for this damn
        return super.isItemValid(slot, stack);
    }
}
