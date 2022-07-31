package tk.utnetwork.utnetworkproxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import tk.utnetwork.utnetworkproxy.Commands.Administraton.Send;
import tk.utnetwork.utnetworkproxy.Misc.ConfigManager;
import tk.utnetwork.utnetworkproxy.Misc.Utils;

public final class UTNetworkProxy extends Plugin {
    Utils utils;
    ConfigManager config = new ConfigManager(this);

    @Override
    public void onEnable() {
        setupConfig();

        utils = new Utils(this);

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    public void registerCommands() {
        registerCommand(new Send(this, "send", "proxy.send", new String[] {}));
    }

    public void registerEvents() {

    }

    public void setupConfig() {
        config.initialise();
    }

    public ConfigManager getConfigManager() {
        return config;
    }

    private void registerCommand(Command cmd) {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, cmd);
    }

    private void registerEvent(Listener listener) {
        ProxyServer.getInstance().getPluginManager().registerListener(this, listener);
    }
}
