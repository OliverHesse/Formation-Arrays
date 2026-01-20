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
    public static CompoundTag blockPos(BlockPos pos){
       CompoundTag tag =  new CompoundTag();
       tag.putInt("x",pos.getX());
       tag.putInt("y",pos.getY());
       tag.putInt("z",pos.getZ());
       return tag;
    }
}
