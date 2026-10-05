package com.animacionesglitch.client;

import com.animacionesglitch.net.AnimationType;
import net.minecraft.Util;

public class ClientAnimation {
    public static final long DURATION_MS = 5000L;

    private static AnimationType current = null;
    private static long startMs = 0L;

    public static void play(AnimationType type) {
        current = type;
        startMs = Util.getMillis();
    }

    public static void clear() {
        current = null;
    }

    public static AnimationType getCurrent() {
        return current;
    }

    /** Milisegundos transcurridos, o -1 si no hay animacion activa. */
    public static long elapsed() {
        if (current == null) return -1L;
        long e = Util.getMillis() - startMs;
        if (e >= DURATION_MS) {
            current = null;
            return -1L;
        }
        return Math.max(0L, e);
    }
}
