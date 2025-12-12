package net.lucent.formation_arrays.data_components;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.data_components.components.AccessTokenComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(
            Registries.DATA_COMPONENT_TYPE,
            FormationArrays.MOD_ID);
    public static final Supplier<DataComponentType<AccessTokenComponent>> ACCESS_CONTROL_DATA_COMPONENT = REGISTRAR.registerComponentType(
            "access_control_data_component",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(AccessTokenComponent.CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(AccessTokenComponent.STREAM_CODEC)
    );

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                           UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return REGISTRAR.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        REGISTRAR.register(eventBus);
    }
}
