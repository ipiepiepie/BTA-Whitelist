package xyz.ipiepiepie.whitelist.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.ipiepiepie.whitelist.WhitelistMod;
import xyz.ipiepiepie.whitelist.config.Data;

import java.util.UUID;

@Mixin(value = PlayerList.class, remap = false)
public class WhitelistCheckMixin {

	@Redirect(method = "getPlayerForLogin", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/PlayerList;isAllowedToLogin(Ljava/util/UUID;)Z"))
	private boolean btawhitelist_getPlayerForLogin(PlayerList instance, UUID uuid, @Local(argsOnly = true, ordinal = 0) String username) {
		return instance.isAllowedToLogin(uuid) || Data.whitelist.contains(username);
	}

}
