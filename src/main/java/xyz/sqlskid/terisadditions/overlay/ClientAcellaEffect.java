package xyz.sqlskid.terisadditions.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import xyz.sqlskid.terisadditions.TerisAdditions;
import xyz.sqlskid.terisadditions.effects.TerisEffects;

import java.util.List;

@EventBusSubscriber(modid = TerisAdditions.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientAcellaEffect {

    // The pool of text that flies around the screen
    private static final List<String> FLOATING_TEXTS = List.of(
            "YOU'RE ACCELERATED",
            "HIGHER CONSCIOUSNESS",
            "WIRED",
            "FAST",
            "SPEED DEMON"
    );

    @SubscribeEvent
    public static void onRenderGui(RenderGuiLayerEvent.Post event) {
        if (!event.getName().equals(VanillaGuiLayers.CAMERA_OVERLAYS)) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && player.hasEffect(TerisEffects.ACCELERATED_EFFECT)) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            Font font = mc.font;

            int screenWidth = event.getGuiGraphics().guiWidth();
            int screenHeight = event.getGuiGraphics().guiHeight();
            long systemTime = System.currentTimeMillis();

            long colorTime = systemTime / 100;
            int r = (int) ((Math.sin(colorTime) + 1) * 127);
            int g = (int) ((Math.sin(colorTime + 2) + 1) * 127);
            int b = (int) ((Math.sin(colorTime + 4) + 1) * 127);
            int tintColor = (20 << 24) | (r << 16) | (g << 8) | b;

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            guiGraphics.fill(0, 0, screenWidth, screenHeight, tintColor);

            for (int i = 0; i < FLOATING_TEXTS.size(); i++) {
                String text = FLOATING_TEXTS.get(i);

                double timeOffset = i * 15.0;
                double speedModifier = 0.001 + (i * 0.0005);

                double angleX = systemTime * speedModifier + timeOffset;
                double angleY = systemTime * (speedModifier * 1.3) + timeOffset;

                int textWidth = font.width(text);
                int textHeight = font.lineHeight;

                int x = (int) ((Math.sin(angleX) + 1) / 2 * (screenWidth - textWidth - 20)) + 10;
                int y = (int) ((Math.cos(angleY) + 1) / 2 * (screenHeight - textHeight - 20)) + 10;

                int textR = (int) ((Math.sin(colorTime + i) + 1) * 127);
                int textG = (int) ((Math.cos(colorTime - i) + 1) * 50);
                int textB = 255;
                int textColor = (255 << 24) | (textR << 16) | (textG << 8) | textB;

                int padding = 2;
                guiGraphics.fill(x - padding, y - padding, x + textWidth + padding, y + textHeight + padding, 0xAA000000);

                guiGraphics.drawString(font, text, x, y, textColor, false);
            }

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    @SubscribeEvent
    public static void onComputeFOV(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();

        //FOV changes
        if (player.hasEffect(TerisEffects.ACCELERATED_EFFECT)) {

            float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();

            float smoothTime = (player.tickCount + partialTick) * 0.2f;
            float fovPulse = 1.0f + (float) Math.sin(smoothTime) * 0.1f;
            event.setNewFovModifier(event.getFovModifier() * fovPulse);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Minecraft mc = Minecraft.getInstance();

        //Small jitters
        if (player.level().isClientSide() && player == mc.player) {

            if (player.hasEffect(TerisEffects.ACCELERATED_EFFECT)) {
                 if (player.getRandom().nextFloat() < 0.02f) {

                    float twitchYaw = (player.getRandom().nextFloat() - 0.5f) * 6.0f;
                    float twitchPitch = (player.getRandom().nextFloat() - 0.5f) * 4.0f;

                    player.setYRot(player.getYRot() + twitchYaw);
                    player.setXRot(player.getXRot() + twitchPitch);

                    player.yRotO += twitchYaw;
                    player.xRotO += twitchPitch;
                }
            }
        }
    }
}
