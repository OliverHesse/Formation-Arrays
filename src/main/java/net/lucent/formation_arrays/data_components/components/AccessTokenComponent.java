package net.lucent.formation_arrays.data_components.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

public record AccessTokenComponent(String owner, String  ownerName, int accessLevel){
    public static final Codec<AccessTokenComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("owner").forGetter(AccessTokenComponent::owner),
                    Codec.STRING.fieldOf("owner_name").forGetter(AccessTokenComponent::ownerName),
                    Codec.INT.fieldOf("access_level").forGetter(AccessTokenComponent::accessLevel)
            ).apply(instance, AccessTokenComponent::new)
    );
    public static final StreamCodec<ByteBuf, AccessTokenComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, AccessTokenComponent::owner,
            ByteBufCodecs.STRING_UTF8, AccessTokenComponent::ownerName,
            ByteBufCodecs.INT, AccessTokenComponent::accessLevel,
            AccessTokenComponent::new
    );

    public static AccessTokenComponent fromPlayer(Player player){
        return new AccessTokenComponent(player.getStringUUID(),player.getDisplayName().getString(),0);
    }
}
