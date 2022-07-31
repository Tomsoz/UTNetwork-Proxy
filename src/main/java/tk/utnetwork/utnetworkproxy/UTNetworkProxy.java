package tk.utnetwork.utnetworkproxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import tk.utnetwork.utnetworkproxy.Commands.Administraton.Send;

public final class UTNetworkProxy extends Plugin {

    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    public void registerCommands() {
        registerCommand(new Send("send", "proxy.send", new String[] {}));
    }

    public void registerEvents() {

    }

    private void registerCommand(Command cmd) {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, cmd);
    }

    private void registerEvent(Listener listener) {
        ProxyServer.getInstance().getPluginManager().registerListener(this, listener);
    }
}
