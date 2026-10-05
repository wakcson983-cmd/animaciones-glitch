package com.animacionesglitch.net;

import com.animacionesglitch.AnimacionesGlitch;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Network {
    private static final String PROTOCOL = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(AnimacionesGlitch.MODID, "main"),
            () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    public static void register() {
        CHANNEL.registerMessage(0, ShowAnimationPacket.class,
                ShowAnimationPacket::encode, ShowAnimationPacket::decode, ShowAnimationPacket::handle);
    }
}
