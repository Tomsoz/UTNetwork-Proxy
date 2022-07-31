package tk.utnetwork.utnetworkproxy.Misc;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;

public class Utils {
    public static BaseComponent chat(String textToTranslate) {
        return new ComponentBuilder(textToTranslate.replaceAll("&", "§")).getCurrentComponent();
    }
}
