package xyz.sqlskid.terisadditions.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.sqlskid.terisadditions.TerisAdditions;
import xyz.sqlskid.terisadditions.effects.TerisEffects;
import xyz.sqlskid.terisadditions.items.ext.AccelaItem;

public class TerisItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TerisAdditions.MODID);

    private static FoodProperties createAccelaProperties() {
        return new FoodProperties.Builder()
                .nutrition(0)
                .saturationModifier(0f)
                .alwaysEdible()
                .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 3), 1.0f)
                .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 3), 1.0f)
                .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0), 1.0f)
                .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.5f)
                .effect(new MobEffectInstance(TerisEffects.ACCELERATED_EFFECT, 6000, 0), 1f)
                .build();
    }

    public static final DeferredItem<Item> ACCELA = ITEMS.register("accela",
            () -> new AccelaItem(new Item.Properties().food(createAccelaProperties()).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
