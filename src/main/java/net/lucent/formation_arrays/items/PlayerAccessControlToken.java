package net.lucent.formation_arrays.items;

import net.lucent.formation_arrays.api.items.IAccessControlToken;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.lucent.formation_arrays.data_components.components.AccessTokenComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class PlayerAccessControlToken extends Item implements IAccessControlToken {
    public PlayerAccessControlToken(Properties properties) {
        super(properties);
    }

    //TODO let player stack multiple unmodified ones
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack item = player.getItemInHand(usedHand);

        if(level.isClientSide() && !player.isShiftKeyDown()) return InteractionResultHolder.fail(item);
        if(isLinked(item)) {
            //TODO check if player is allowed to modify it. if so send packet to open menu and set access level
            return InteractionResultHolder.success(item);
        }

        item.set(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT, AccessTokenComponent.fromPlayer(player));
        return InteractionResultHolder.success(item);


    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(!context.level().isClientSide()) return;
        if(isLinked(stack)){
            //item is linked display player name
            UUID playerId = UUID.fromString(getOwnerId(stack));

            updateTokenDisplayName(stack,context.level());

            String displayName = getDisplayName(stack);
            int accessLevel = getPermissionLevel(stack);

            tooltipComponents.add(Component.literal("Linked: "+displayName));
            tooltipComponents.add(Component.literal("Level: "+accessLevel));
            if(Minecraft.getInstance().player.getUUID().equals(playerId)) tooltipComponents.add(Component.literal("Shift+Right-Click to edit access level").withStyle(ChatFormatting.GRAY));


        }else{
            tooltipComponents.add(Component.literal("Shift+Right-Click to link").withStyle(ChatFormatting.GRAY));

        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public void updateTokenDisplayName(ItemStack controlToken, Level level){

        Player player = level.getPlayerByUUID(UUID.fromString(getOwnerId(controlToken)));
        if(player == null) return;
        controlToken.set(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT,AccessTokenComponent.fromPlayer(player));
    }

    public boolean isPlayerOnline(UUID id,Level level){
       return level.getPlayerByUUID(id) == null;
    }

    public String getDisplayName(ItemStack controlToken){
        return controlToken.get(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT).ownerName();
    }

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
    public List<Player> filterPlayerList(ItemStack controlToken, List<Player> playerList) {
        if(controlToken.has(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT)) return List.of();
        for (Player player : playerList){
            if(player.getUUID().toString().equals(controlToken.get(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT).owner())) return List.of(player);
        }
        return List.of();
    }
}
