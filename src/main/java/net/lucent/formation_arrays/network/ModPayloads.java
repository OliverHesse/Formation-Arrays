package net.lucent.formation_arrays.network;

import net.lucent.formation_arrays.FormationArrays;
import net.lucent.formation_arrays.network.client_bound.*;
import net.lucent.formation_arrays.network.server_bound.RequestNearbyCoresPayload;
import net.lucent.formation_arrays.network.server_bound.RequestNearbyFormationsPayload;
import net.lucent.formation_arrays.network.server_bound.UpdateAccessLevel;
import net.lucent.formation_arrays.network.server_bound.UpdateFormationConnectionStatus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModPayloads {

    public static void registerServerPayloads(PayloadRegistrar registrar){
        registrar.playToServer(
                RequestNearbyCoresPayload.TYPE,
                RequestNearbyCoresPayload.STREAM_CODEC,
                RequestNearbyCoresPayload::handlePayload

        );
        registrar.playToServer(
                UpdateAccessLevel.TYPE,
                UpdateAccessLevel.STREAM_CODEC,
                UpdateAccessLevel::handlePayload
        );
        registrar.playToServer(
                RequestNearbyFormationsPayload.TYPE,
                RequestNearbyFormationsPayload.STREAM_CODEC,
                RequestNearbyFormationsPayload::handlePayload
        );
        registrar.playToServer(
                UpdateFormationConnectionStatus.TYPE,
                UpdateFormationConnectionStatus.STREAM_CODEC,
                UpdateFormationConnectionStatus::handlePayload
        );
    }
    public static void  registerClientPayloads(PayloadRegistrar registrar){
        registrar.playToClient(
                UpdateNearbyCores.TYPE,
                UpdateNearbyCores.STREAM_CODEC,
                UpdateNearbyCores::handlePayload
        );
        registrar.playToClient(
                SyncAccessToken.TYPE,
                SyncAccessToken.STREAM_CODEC,
                SyncAccessToken::handlePayload
        );
        registrar.playToClient(
                UpdateNearbyFormations.TYPE,
                UpdateNearbyFormations.STREAM_CODEC,
                UpdateNearbyFormations::handlePayload
        );
        registrar.playToClient(
                SyncCoreSlotData.TYPE,
                SyncCoreSlotData.STREAM_CODEC,
                SyncCoreSlotData::handlePayload
        );
        registrar.playToClient(
                SyncFormationCoreSlotData.TYPE,
                SyncFormationCoreSlotData.STREAM_CODEC,
                SyncFormationCoreSlotData::handlePayload
        );
    }
    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar =event.registrar(FormationArrays.MOD_ID).versioned("1.0");
        registerServerPayloads(registrar);
        registerClientPayloads(registrar);
    }
}
