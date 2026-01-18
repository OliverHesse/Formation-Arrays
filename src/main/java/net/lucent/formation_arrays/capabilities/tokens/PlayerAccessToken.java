package net.lucent.formation_arrays.capabilities.tokens;

import net.lucent.formation_arrays.api.capability.IAccessControlToken;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.lucent.formation_arrays.data_components.components.AccessTokenComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

//assumes AccessTokenComponent
public class PlayerAccessToken implements IAccessControlToken {
    @Override
    public String getOwnerId(ItemStack controlToken) {
        return controlToken.get(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT).owner();
    }

    @Override
    public int getPermissionLevel(ItemStack controlToken) {
        return controlToken.get(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT).accessLevel();
    }

    @Override
    public boolean isLinked(ItemStack controlToken) {
        return controlToken.has(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT);
    }

    @Override
    public boolean hasPermission(Player player, ItemStack itemStack) {

        return isLinked(itemStack) && getOwnerId(itemStack).equals(player.getUUID().toString());
    }

    @Override
    public List<Player> filterPlayerList(ItemStack controlToken, List<Player> playerList) {
        if(!isLinked(controlToken)) return List.of();
        for (Player player : playerList){
            if(player.getUUID().toString().equals(getOwnerId(controlToken))) return List.of(player);
        }
        return List.of();
    }
    public String getDisplayName(ItemStack controlToken){
        return controlToken.get(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT).ownerName();
    }

    @Override
    public void tryUpdateAccessLevel(Player player, ItemStack controlToken,int value) {
        if(!isLinked(controlToken)) return;

        if(!player.getUUID().toString().equals(getOwnerId(controlToken)) || getPermissionLevel(controlToken)+value <0) return;
        System.out.println("control Token value updated");
        controlToken.set(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT,new AccessTokenComponent(getOwnerId(controlToken),getDisplayName(controlToken),getPermissionLevel(controlToken)+value));

    }


}
