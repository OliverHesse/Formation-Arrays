package net.lucent.formation_arrays.items;

import net.lucent.formation_arrays.api.capability.IAccessControlToken;
import net.lucent.formation_arrays.api.capability.Capabilities;
import net.lucent.formation_arrays.data_components.ModDataComponents;
import net.lucent.formation_arrays.data_components.components.AccessTokenComponent;
import net.lucent.formation_arrays.gui.easy_gui_screens.AccessControlTokenScreen;
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

public class PlayerAccessControlToken extends Item {
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
        IAccessControlToken capability = item.getCapability(Capabilities.ACCESS_TOKEN_CAPABILITY);
        if(capability == null) return InteractionResultHolder.fail(item);

        if(level.isClientSide() && !player.isShiftKeyDown()) return InteractionResultHolder.fail(item);
        if(capability.isLinked(item) && level.isClientSide()) {
            System.out.println("is linked try to open screen");
            System.out.println(player.getUUID());
            System.out.println(capability.getOwnerId(item));
            if(player.getUUID().toString().equals(capability.getOwnerId(item))) {
                System.out.println("CREATE");
                Minecraft.getInstance().setScreen(new AccessControlTokenScreen(Component.empty()));

            };
            //TODO check if player is allowed to modify it. if so send packet to open menu and set access level
            return InteractionResultHolder.success(item);
        }
        else if(!capability.isLinked(item)){
            item.set(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT, AccessTokenComponent.fromPlayer(player));
        }

        return InteractionResultHolder.success(item);


    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        IAccessControlToken capability = stack.getCapability(Capabilities.ACCESS_TOKEN_CAPABILITY);
        if(capability == null) {
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            return;
        }

        if(capability.isLinked(stack)){
            //item is linked display player name
            UUID playerId = UUID.fromString(capability.getOwnerId(stack));

            updateTokenDisplayName(stack,context.level());

            String displayName = stack.get(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT).ownerName();
            int accessLevel = capability.getPermissionLevel(stack);

            tooltipComponents.add(Component.literal("Linked: "+displayName));
            tooltipComponents.add(Component.literal("Level: "+accessLevel));
            if(Minecraft.getInstance().player.getUUID().equals(playerId)) tooltipComponents.add(Component.literal("Shift+Right-Click to edit access level").withStyle(ChatFormatting.GRAY));


        }else{
            tooltipComponents.add(Component.literal("Shift+Right-Click to link").withStyle(ChatFormatting.GRAY));

        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public void updateTokenDisplayName(ItemStack controlToken, Level level){
        IAccessControlToken capability = controlToken.getCapability(Capabilities.ACCESS_TOKEN_CAPABILITY);
        if(capability == null) return;

        Player player = level.getPlayerByUUID(UUID.fromString(capability.getOwnerId(controlToken)));
        if(player == null) return;
        controlToken.set(ModDataComponents.ACCESS_CONTROL_DATA_COMPONENT,new AccessTokenComponent(capability.getOwnerId(controlToken),player.getDisplayName().getString(),capability.getPermissionLevel(controlToken)));
    }

    public boolean isPlayerOnline(UUID id,Level level){
       return level.getPlayerByUUID(id) == null;
    }


}
