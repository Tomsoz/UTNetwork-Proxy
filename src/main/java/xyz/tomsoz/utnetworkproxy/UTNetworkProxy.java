package xyz.tomsoz.utnetworkproxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import xyz.tomsoz.utnetworkproxy.Misc.Utils;

public final class UTNetworkProxy extends Plugin {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getConsole().sendMessage(Utils.chat("&aThe plugin has enabled successfully, tomsoz is so smelly and dumb that hes smelly and dumb."));
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getConsole().sendMessage(Utils.chat("&cThe plugin has disabled successfully, tomsoz is even more smellier, the last shower he took was 452 weeks ago."));
    }
}
