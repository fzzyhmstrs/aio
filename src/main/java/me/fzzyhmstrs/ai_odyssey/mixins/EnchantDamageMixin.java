package me.fzzyhmstrs.ai_odyssey.mixins;


import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public abstract class EnchantDamageMixin {

    private static final int AQUATIC_INDEX = 4;

    @Shadow @Final public int typeIndex;
    @Shadow @Final private static int[] BASE_POWERS;
    @Shadow @Final private static int[] POWERS_PER_LEVEL;

    @Shadow public abstract int getMinPower(int level);

    @Inject(method = "getMinPower", at = @At(value = "HEAD"), cancellable = true)
    private void getMinPower(int level, CallbackInfoReturnable<Integer> cir) {
        if(this.typeIndex == AQUATIC_INDEX){
            int i = 0; //adding a modifier to max the maximum levels a bit harder to show up in the imbuing table
            if (level == 6) i = 8; // may tweak to 6/12
            if (level >= 7) i = 16;
            cir.setReturnValue(5 + ((level - 1) * 8) + i);
        }
    }

    @Inject(method = "getMaxPower", at = @At(value = "HEAD"), cancellable = true)
    private void getMaxPower(int level, CallbackInfoReturnable<Integer> cir) {
        if(this.typeIndex == AQUATIC_INDEX){
            cir.setReturnValue(this.getMinPower(level) + 20);
        }
    }

    @Inject(method = "getMaxLevel", at = @At(value = "HEAD"), cancellable = true)
    private void getMaxLevel(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(7);
    }

    @Inject(method = "getAttackDamage", at = @At(value = "HEAD"), cancellable = true)
    private void getAttackDamage(int level, EntityGroup group, CallbackInfoReturnable<Float> cir){
        if (typeIndex == 4 && group == EntityGroup.AQUATIC){
            cir.setReturnValue(level * 2.5F);
        }
    }
}
