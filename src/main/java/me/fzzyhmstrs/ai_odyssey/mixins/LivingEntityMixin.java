package me.fzzyhmstrs.ai_odyssey.mixins;

import me.fzzyhmstrs.ai_odyssey.registry.RegisterStatus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
    @Shadow public abstract boolean clearStatusEffects();
    @Shadow public abstract void setHealth(float v);

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);


    @Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        if (source.isOutOfWorld()) {
            cir.setReturnValue(false);
        }
        boolean bl = this.hasStatusEffect(RegisterStatus.INSTANCE.getUNDEATH());
        if (bl) {
            this.setHealth(0.5f);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 80, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400, 0));
            this.world.sendEntityStatus(this, (byte)35);
            this.removeStatusEffect(RegisterStatus.INSTANCE.getUNDEATH());
            cir.setReturnValue(true);
        }

    }


}
