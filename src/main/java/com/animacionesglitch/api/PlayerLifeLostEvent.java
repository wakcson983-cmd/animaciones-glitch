package com.animacionesglitch.api;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

/** Lanzalo en MinecraftForge.EVENT_BUS cuando un jugador pierde una vida (pero no todas). */
public class PlayerLifeLostEvent extends Event {
    private final ServerPlayer player;

    public PlayerLifeLostEvent(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }
}
