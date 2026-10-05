package com.animacionesglitch.net;

import com.animacionesglitch.AnimacionesGlitch;
import net.minecraft.resources.ResourceLocation;

public enum AnimationType {
    VIDA_PERDIDA_PROPIA("vida_propia", "has_perdido_una_vida", 640, 423),
    ELIMINADO_PROPIO("eliminado_propio", "eliminado", 640, 485),
    VIDA_PERDIDA_OTROS("vida_otros", "una_vida_perdida", 640, 357),
    JUGADOR_ELIMINADO_OTROS("eliminado_otros", "jugador_eliminado", 640, 317);

    public final String commandName;
    public final ResourceLocation texture;
    public final int texW;
    public final int texH;

    AnimationType(String commandName, String file, int texW, int texH) {
        this.commandName = commandName;
        this.texture = new ResourceLocation(AnimacionesGlitch.MODID, "textures/gui/" + file + ".png");
        this.texW = texW;
        this.texH = texH;
    }
}
