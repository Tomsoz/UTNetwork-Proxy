package tk.utnetwork.utnetworkproxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import tk.utnetwork.utnetworkproxy.Misc.Utils;

public final class UTNetworkProxy extends Plugin {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getConsole().sendMessage(Utils.chat("&aThe plugin has enabled successfully."));
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getConsole().sendMessage(Utils.chat("&cThe plugin has disabled successfully."));
    }
}
