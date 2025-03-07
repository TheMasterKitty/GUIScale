package themasterkitty.guiscale;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Config implements ModMenuApi {
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "guiscale.config.json");
    public static int guiscale = 0;

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                writer.write("0");
            } catch (Exception ignored) { }
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE); Scanner scan = new Scanner(reader)) {
            guiscale = scan.nextInt();
        } catch (Exception ignored) { }
    }
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("GUI Scale"));

            ConfigCategory s = builder.getOrCreateCategory(Text.literal("Settings"));
            ConfigEntryBuilder sb = builder.entryBuilder();

            s.addEntry(sb.startIntSlider(Text.literal("GuiScale"), guiscale, 0, 4)
                    .setDefaultValue(0)
                    .setTooltip(Text.literal("0 is default"))
                    .setSaveConsumer(value -> guiscale = value)
                    .build());

            builder.setSavingRunnable(() -> {
                try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                    writer.write(String.valueOf(guiscale));
                } catch (Exception ignored) { }
            });
            return builder.build();
        };
    }
}