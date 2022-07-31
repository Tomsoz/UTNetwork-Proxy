package xyz.tomsoz.utnetworkproxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import xyz.tomsoz.utnetworkproxy.Misc.Utils;

public final class UTNetworkProxy extends Plugin {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getConsole().sendMessage(Utils.chat("&aThe plugin has enabled successfully."));
        ProxyServer.getInstance().getConsole().sendMessage(Utils.chat("&aTomsoz is a smelly rat and looks like a bitch, (all said by racistmonke)."));
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getConsole().sendMessage(Utils.chat("&cThe plugin has disabled successfully."));
    }
}
