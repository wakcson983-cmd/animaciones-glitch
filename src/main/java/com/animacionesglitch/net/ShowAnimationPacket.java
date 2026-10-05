package com.animacionesglitch.net;

import com.animacionesglitch.client.ClientAnimation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShowAnimationPacket {
    private final AnimationType type;

    public ShowAnimationPacket(AnimationType type) {
        this.type = type;
    }

    public static void encode(ShowAnimationPacket msg, FriendlyByteBuf buf) {
        buf.writeEnum(msg.type);
    }

    public static ShowAnimationPacket decode(FriendlyByteBuf buf) {
        return new ShowAnimationPacket(buf.readEnum(AnimationType.class));
    }

    public static void handle(ShowAnimationPacket msg, Supplier<NetworkEvent.Context> ctx) {
        final AnimationType type = msg.type;
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAnimation.play(type)));
        ctx.get().setPacketHandled(true);
    }
}
