package net.lucent.formation_arrays.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class NBTUtil {

    public static BlockPos fromCompound(CompoundTag tag){
        int x = tag.getInt("x");
        int y = tag.getInt("y");
        int z = tag.getInt("z");
        return new BlockPos(x,y,z);

    }
}
