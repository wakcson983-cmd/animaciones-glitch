package com.animacionesglitch.client;

import com.animacionesglitch.net.AnimationType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;

import java.util.Random;

public class GlitchRenderer {
    private static final long GLITCH_IN_MS = 800L;
    private static final long GLITCH_OUT_MS = 800L;
    // tamano del inventario del jugador (en pixeles de GUI)
    private static final int MAX_W = 176;
    private static final int MAX_H = 166;
    private static final int STRIPS = 16;

    public static void render(GuiGraphics g, int sw, int sh) {
        long t = ClientAnimation.elapsed();
        if (t < 0) return;
        AnimationType type = ClientAnimation.getCurrent();
        if (type == null) return;

        float in = 0f, out = 0f, mid = 0f;
        if (t < GLITCH_IN_MS) {
            in = 1f - (float) t / GLITCH_IN_MS;
        } else if (t > ClientAnimation.DURATION_MS - GLITCH_OUT_MS) {
            out = 1f - (float) (ClientAnimation.DURATION_MS - t) / GLITCH_OUT_MS;
        }
        long bucket = t / 55L;
        Random rnd = new Random(bucket * 7919L + type.ordinal() * 104729L);
        // micro-glitches ocasionales en la parte estable
        if (in == 0f && out == 0f && rnd.nextFloat() < 0.04f) mid = 0.25f + rnd.nextFloat() * 0.2f;
        float intensity = Math.max(Math.max(in, out), mid);

        float scale = Math.min((float) MAX_W / type.texW, (float) MAX_H / type.texH);
        int w = Math.round(type.texW * scale);
        int h = Math.round(type.texH * scale);
        int x0 = (sw - w) / 2;
        int y0 = (sh - h) / 2;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // tinte rojo de pantalla
        if (intensity > 0f) {
            int a = (int) (intensity * (rnd.nextFloat() * 0.18f + 0.04f) * 255);
            g.fill(0, 0, sw, sh, (a << 24) | 0xFF0000);
        }

        boolean skipMain = intensity > 0.45f && rnd.nextFloat() < intensity * 0.5f;
        // al inicio y al final la imagen parpadea y se corta mas
        if (!skipMain) {
            // ghost rojo desplazado
            if (intensity > 0f) {
                g.setColor(1f, 0.08f, 0.08f, 0.55f * intensity + 0.1f);
                drawStrips(g, type, x0, y0, w, h, intensity * 1.6f, rnd, true);
            }
            g.setColor(1f, 1f, 1f, 1f);
            drawStrips(g, type, x0, y0, w, h, intensity, rnd, false);
        }
        g.setColor(1f, 1f, 1f, 1f);

        if (intensity > 0f) {
            // barras rojas horizontales
            int bars = 1 + (int) (intensity * 6);
            for (int i = 0; i < bars; i++) {
                int by = y0 - 10 + rnd.nextInt(h + 20);
                int bh = 1 + rnd.nextInt(3);
                int bx = x0 - 30 + rnd.nextInt(30);
                int bw = w + 30 + rnd.nextInt(30);
                int a = 70 + rnd.nextInt(130);
                g.fill(bx, by, bx + bw, by + bh, (a << 24) | 0xFF1010);
            }
            // bloques de ruido
            int blocks = (int) (intensity * 14);
            for (int i = 0; i < blocks; i++) {
                int bx = x0 + rnd.nextInt(w);
                int by = y0 + rnd.nextInt(h);
                int bw = 3 + rnd.nextInt(14);
                int bh = 1 + rnd.nextInt(4);
                int color = rnd.nextBoolean() ? 0xCCFF1A1A : 0xAA330000;
                g.fill(bx, by, bx + bw, by + bh, color);
            }
        }

        RenderSystem.disableBlend();
    }

    private static void drawStrips(GuiGraphics g, AnimationType type, int x0, int y0, int w, int h,
                                   float intensity, Random rnd, boolean ghost) {
        for (int i = 0; i < STRIPS; i++) {
            int dy0 = y0 + Math.round((float) i * h / STRIPS);
            int dy1 = y0 + Math.round((float) (i + 1) * h / STRIPS);
            int ty0 = Math.round((float) i * type.texH / STRIPS);
            int ty1 = Math.round((float) (i + 1) * type.texH / STRIPS);
            int dh = dy1 - dy0;
            int th = ty1 - ty0;
            if (dh <= 0 || th <= 0) continue;

            int dx = 0;
            if (intensity > 0f && rnd.nextFloat() < Math.min(1f, intensity * 0.85f + (ghost ? 0.15f : 0f))) {
                dx = Math.round((rnd.nextFloat() - 0.5f) * 2f * intensity * w * 0.22f);
            }
            if (ghost && dx == 0) dx = Math.round(intensity * 3f);
            g.blit(type.texture, x0 + dx, dy0, w, dh, 0f, (float) ty0, type.texW, th, type.texW, type.texH);
        }
    }
}
