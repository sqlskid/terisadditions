package xyz.sqlskid.terisadditions.effects.ext;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class AcceleratedEffect extends MobEffect {
    public AcceleratedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF00FF);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int duration) {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {

    }
}
