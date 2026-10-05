package com.animacionesglitch.api;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

/** Lanzalo en MinecraftForge.EVENT_BUS cuando un jugador pierde todas sus vidas. */
public class PlayerEliminatedEvent extends Event {
    private final ServerPlayer player;

    public PlayerEliminatedEvent(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }
}
