package net.lucent.formation_arrays.api.formations;

import net.lucent.formation_arrays.api.formations.node.IFormationNode;
import net.lucent.formation_arrays.api.registries.FormationRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.nio.charset.Charset;
import java.util.UUID;

public class FormationEncodingHelper {

    public static void encode(RegistryFriendlyByteBuf buf, IFormationNode node){
        if(node == null) {
            buf.writeBoolean(false);
            return;
        }
        buf.writeBoolean(true);
        ResourceLocation key = node.getFormationKey();
        buf.writeInt(key.toString().length());
        buf.writeCharSequence(key.toString(), Charset.defaultCharset());

        buf.writeInt(node.getFormationId().toString().length());
        buf.writeCharSequence(node.getFormationId().toString(),Charset.defaultCharset());

        node.encode(buf);

    }
    public static IFormationNode decode(RegistryFriendlyByteBuf buf,BlockPos core){
        if(!buf.readBoolean()) return null;
        String keyString = (String) buf.readCharSequence(buf.readInt(),Charset.defaultCharset());

        ResourceLocation key = ResourceLocation.bySeparator(keyString,':');

        String uuid = (String) buf.readCharSequence(buf.readInt(),Charset.defaultCharset());

        IFormationNode node = FormationRegistry.FORMATION_REGISTRY.get(key).getNewFormationNode(core, UUID.fromString(uuid));
        node.decode(buf);
        return node;
    }
}
