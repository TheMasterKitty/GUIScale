package themasterkitty.guiscale;

import net.fabricmc.api.ClientModInitializer;

public class GUIScale implements ClientModInitializer {
    @Override
	public void onInitializeClient() {
        Config.load();
	}
}