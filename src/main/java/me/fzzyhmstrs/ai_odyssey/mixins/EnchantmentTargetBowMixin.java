package me.fzzyhmstrs.ai_odyssey.mixins;

import me.fzzyhmstrs.ai_odyssey.item.HarpoonLauncherItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/enchantment/EnchantmentTarget$11")
public abstract class EnchantmentTargetBowMixin {

    @Inject(method = "isAcceptableItem(Lnet/minecraft/item/Item;)Z", at = @At(value = "HEAD"), cancellable = true)
    private void harpoonLauncherBowEnchant(Item item, CallbackInfoReturnable<Boolean> cir){
        if (item instanceof HarpoonLauncherItem){
            cir.setReturnValue(true);
        }
    }
}
