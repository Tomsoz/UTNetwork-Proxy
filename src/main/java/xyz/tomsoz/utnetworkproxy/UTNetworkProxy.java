package xyz.tomsoz.utnetworkproxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class UTNetworkProxy extends Plugin {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getConsole().sendMessage();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
