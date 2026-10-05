package com.animacionesglitch.client;

import com.animacionesglitch.AnimacionesGlitch;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = AnimacionesGlitch.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBus {
        @SubscribeEvent
        public static void registerOverlay(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("glitch_animation", (gui, graphics, partialTick, width, height) -> {
                // si hay una pantalla abierta, la dibuja ScreenEvent.Render.Post
                if (Minecraft.getInstance().screen == null) {
                    GlitchRenderer.render(graphics, width, height);
                }
            });
        }
    }

    @Mod.EventBusSubscriber(modid = AnimacionesGlitch.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBus {
        @SubscribeEvent
        public static void onScreenRender(ScreenEvent.Render.Post event) {
            GlitchRenderer.render(event.getGuiGraphics(), event.getScreen().width, event.getScreen().height);
        }

        @SubscribeEvent
        public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
            ClientAnimation.clear();
        }
    }
}
