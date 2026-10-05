# Animaciones Glitch (Forge 1.20.1)

Mod solo de animaciones (no incluye sistema de vidas).

| Quién la ve | Pierde una vida | Pierde todas las vidas |
|---|---|---|
| El jugador afectado | HAS PERDIDO UNA VIDA (corazón roto) | ELIMINADO |
| Los demás jugadores | UNA VIDA PERDIDA | JUGADOR ELIMINADO |

Cada imagen dura 5 segundos, sale en el centro con el tamaño del inventario (176x166) y aparece/desaparece con glitch rojo.

## Cómo activarlo desde tu mod de vidas
Desde Java:
    GlitchAPI.playerLostLife(serverPlayer);
    GlitchAPI.playerEliminated(serverPlayer);
O lanzando eventos:
    MinecraftForge.EVENT_BUS.post(new PlayerLifeLostEvent(serverPlayer));
    MinecraftForge.EVENT_BUS.post(new PlayerEliminatedEvent(serverPlayer));

## Comandos (nivel 2 / OP)
    /animacionglitch vida_perdida <jugador>
    /animacionglitch eliminado <jugador>
    /animacionglitch probar vida_propia|eliminado_propio|vida_otros|eliminado_otros   (solo para ti)

## Obtener el .jar con GitHub
1. Sube TODO el contenido de esta carpeta a tu repositorio (incluida la carpeta oculta .github/workflows/build.yml).
   Si la web no sube .github, usa "Add file > Create new file", escribe `.github/workflows/build.yml` y pega su contenido.
2. Ve a la pestaña Actions y espera que termine "Compilar mod" (unos 3-5 min).
3. Descarga el .jar en Releases ("Última versión") o en Actions > el run > Artifacts.
4. Pon el .jar en la carpeta mods del servidor y de TODOS los jugadores.
