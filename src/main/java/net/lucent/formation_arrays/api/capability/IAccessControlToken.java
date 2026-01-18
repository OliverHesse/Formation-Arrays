package net.lucent.formation_arrays.api.capability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IAccessControlToken {

    String getOwnerId(ItemStack controlToken);
    int getPermissionLevel(ItemStack controlToken);
    boolean isLinked(ItemStack controlToken);
    boolean hasPermission(Player player,ItemStack itemStack); //checks if the given player has permission
    List<Player> filterPlayerList(ItemStack controlToken,List<Player> playerList);
    void tryUpdateAccessLevel(Player player,ItemStack controlToken,int value);

}
