package net.lucent.formation_arrays.data_components.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

public record FormationPlateComponent(String formation_id){
    public static final Codec<FormationPlateComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("formation_id").forGetter(FormationPlateComponent::formation_id)
            ).apply(instance, FormationPlateComponent::new)
    );
    public static final StreamCodec<ByteBuf, FormationPlateComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, FormationPlateComponent::formation_id,
            FormationPlateComponent::new
    );


    public static final StreamCodec<ByteBuf, FormationPlateComponent> UNIT_STREAM_CODEC = StreamCodec.unit(new FormationPlateComponent("ascension:none"));

}