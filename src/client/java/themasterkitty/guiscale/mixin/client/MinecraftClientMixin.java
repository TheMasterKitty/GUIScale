package themasterkitty.guiscale.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import themasterkitty.guiscale.Config;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Inject(at = @At("HEAD"), method = "setScreen")
	private void setScreen(Screen screen, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen == null && screen != null)
            MinecraftClient.getInstance()
                    .getWindow()
                    .setScaleFactor(Config.guiscale == 0 ?
                            MinecraftClient.getInstance().getWindow().calculateScaleFactor(
                                    0, MinecraftClient.getInstance().forcesUnicodeFont()
                            )
                            : Config.guiscale);
        if (screen == null) MinecraftClient.getInstance().onResolutionChanged();
	}
}