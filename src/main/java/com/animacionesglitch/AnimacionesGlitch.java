package com.animacionesglitch;

import com.animacionesglitch.api.GlitchAPI;
import com.animacionesglitch.api.PlayerEliminatedEvent;
import com.animacionesglitch.api.PlayerLifeLostEvent;
import com.animacionesglitch.net.AnimationType;
import com.animacionesglitch.net.Network;
import com.animacionesglitch.net.ShowAnimationPacket;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.PacketDistributor;

@Mod(AnimacionesGlitch.MODID)
public class AnimacionesGlitch {
    public static final String MODID = "animacionesglitch";

    public AnimacionesGlitch() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(Network::register);
    }

    /** Otros mods pueden lanzar este evento en MinecraftForge.EVENT_BUS. */
    @SubscribeEvent
    public void onLifeLost(PlayerLifeLostEvent event) {
        GlitchAPI.playerLostLife(event.getPlayer());
    }

    /** Otros mods pueden lanzar este evento en MinecraftForge.EVENT_BUS. */
    @SubscribeEvent
    public void onEliminated(PlayerEliminatedEvent event) {
        GlitchAPI.playerEliminated(event.getPlayer());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("animacionglitch")
                .requires(src -> src.hasPermission(2));

        root.then(Commands.literal("vida_perdida")
                .then(Commands.argument("jugador", EntityArgument.player()).executes(ctx -> {
                    GlitchAPI.playerLostLife(EntityArgument.getPlayer(ctx, "jugador"));
                    return 1;
                })));

        root.then(Commands.literal("eliminado")
                .then(Commands.argument("jugador", EntityArgument.player()).executes(ctx -> {
                    GlitchAPI.playerEliminated(EntityArgument.getPlayer(ctx, "jugador"));
                    return 1;
                })));

        // /animacionglitch probar <tipo>: muestra una animacion solo a quien ejecuta el comando
        LiteralArgumentBuilder<CommandSourceStack> test = Commands.literal("probar");
        for (AnimationType type : AnimationType.values()) {
            test.then(Commands.literal(type.commandName).executes(ctx -> {
                ServerPlayer p = ctx.getSource().getPlayerOrException();
                Network.CHANNEL.send(PacketDistributor.PLAYER.with(() -> p), new ShowAnimationPacket(type));
                return 1;
            }));
        }
        root.then(test);

        event.getDispatcher().register(root);
    }
}
