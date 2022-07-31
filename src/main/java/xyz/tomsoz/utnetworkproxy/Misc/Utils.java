package xyz.tomsoz.utnetworkproxy.Misc;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Utils {
    public static BaseComponent chat(String textToTranslate) {
        return new ComponentBuilder(textToTranslate.replaceAll("&", "§")).getCurrentComponent();
    }
}
