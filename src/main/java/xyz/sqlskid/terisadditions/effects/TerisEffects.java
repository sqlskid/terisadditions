package xyz.sqlskid.terisadditions.effects;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.sqlskid.terisadditions.TerisAdditions;
import xyz.sqlskid.terisadditions.effects.ext.AcceleratedEffect;

public class TerisEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, TerisAdditions.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> ACCELERATED_EFFECT =
            MOB_EFFECTS.register("accelerated_effect", AcceleratedEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
