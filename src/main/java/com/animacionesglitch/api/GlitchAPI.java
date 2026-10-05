package com.animacionesglitch.api;

import com.animacionesglitch.net.AnimationType;
import com.animacionesglitch.net.Network;
import com.animacionesglitch.net.ShowAnimationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public final class GlitchAPI {
    private GlitchAPI() {}

    /** El jugador pierde una vida: el ve "HAS PERDIDO UNA VIDA", los demas "UNA VIDA PERDIDA". */
    public static void playerLostLife(ServerPlayer player) {
        send(player, AnimationType.VIDA_PERDIDA_PROPIA, AnimationType.VIDA_PERDIDA_OTROS);
    }

    /** El jugador pierde todas sus vidas: el ve "ELIMINADO", los demas "JUGADOR ELIMINADO". */
    public static void playerEliminated(ServerPlayer player) {
        send(player, AnimationType.ELIMINADO_PROPIO, AnimationType.JUGADOR_ELIMINADO_OTROS);
    }

    private static void send(ServerPlayer player, AnimationType own, AnimationType others) {
        Network.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ShowAnimationPacket(own));
        for (ServerPlayer other : player.server.getPlayerList().getPlayers()) {
            if (other != player) {
                Network.CHANNEL.send(PacketDistributor.PLAYER.with(() -> other), new ShowAnimationPacket(others));
            }
        }
    }
}
