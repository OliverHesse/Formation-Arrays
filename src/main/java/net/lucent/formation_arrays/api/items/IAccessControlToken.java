package net.lucent.formation_arrays.api.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IAccessControlToken {

    String getOwnerId(ItemStack controlToken);
    int getPermissionLevel(ItemStack controlToken);
    boolean isLinked(ItemStack controlToken);

    List<Player> filterPlayerList(ItemStack controlToken,List<Player> playerList);
}
