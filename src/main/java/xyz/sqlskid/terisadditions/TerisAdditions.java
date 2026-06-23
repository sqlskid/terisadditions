package xyz.sqlskid.terisadditions;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import xyz.sqlskid.terisadditions.effects.TerisEffects;
import xyz.sqlskid.terisadditions.items.TerisItems;

import java.util.function.Supplier;

@Mod(TerisAdditions.MODID)
public class TerisAdditions {
    public static final String MODID = "terisadditions";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Supplier<CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register("teriadditions", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID + ".creativetab"))
            .icon(() -> new ItemStack(TerisItems.ACCELA.asItem()))
            .displayItems((params, output) -> {
                output.accept(TerisItems.ACCELA.asItem());
            })
            .build()
    );

    public TerisAdditions(IEventBus modEventBus, ModContainer modContainer) {
        CREATIVE_TABS.register(modEventBus);
        TerisEffects.register(modEventBus);

        TerisItems.register(modEventBus);
    }
}
