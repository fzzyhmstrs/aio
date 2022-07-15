package me.fzzyhmstrs.ai_odyssey.mixins;

import me.fzzyhmstrs.ai_odyssey.scepter.EntangledSatchelAugment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
    private void writeEntangledInventory(NbtCompound nbt, CallbackInfo ci){
        if (EntangledSatchelAugment.Companion.checkUserIsEntangled((PlayerEntity)(Object)this)){
            EntangledSatchelAugment.Companion.EntangledChestInventory inventory = EntangledSatchelAugment.Companion.getOrCreateEntangledInventory((PlayerEntity)(Object)this);
            if (!inventory.isEmpty()){
                nbt.put("entangled_inventory",inventory.toNbtList());
            }
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
    private void readEntangledInventory(NbtCompound nbt, CallbackInfo ci){
        if (nbt.contains("entangled_inventory")) {
            NbtList list = nbt.getList("entangled_inventory",10);
            EntangledSatchelAugment.Companion.EntangledChestInventory inventory = EntangledSatchelAugment.Companion.getOrCreateEntangledInventory((PlayerEntity)(Object)this);
            inventory.readNbtList(list);
        }
    }

}
