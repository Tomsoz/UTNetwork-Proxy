package tk.utnetwork.utnetworkproxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import tk.utnetwork.utnetworkproxy.Commands.Administraton.*;
import tk.utnetwork.utnetworkproxy.Commands.General.GList;
import tk.utnetwork.utnetworkproxy.Events.JoinEvent;
import tk.utnetwork.utnetworkproxy.Events.QuitEvent;
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
        registerCommand(new Alert(this, "alert", "proxy.alert", new String[] {}));
        registerCommand(new AlertRaw(this, "alertraw", "proxy.alertraw", new String[] {}));
        registerCommand(new Find(this, "find", "proxy.find", new String[] {}));
        registerCommand(new Server(this, "server", "proxy.server", new String[] {}));

        registerCommand(new GList(this, "glist", "proxy.glist", new String[] {}));
    }

    public void registerEvents() {
        registerEvent(new JoinEvent(this));
        registerEvent(new QuitEvent(this));
    }

    public void setupConfig() {
        config.initialise();
    }

    public ConfigManager getConfigManager() {
        return config;
    }
    public Utils getUtils() {
        return utils;
    }

    private void registerCommand(Command cmd) {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, cmd);
    }

    private void registerEvent(Listener listener) {
        ProxyServer.getInstance().getPluginManager().registerListener(this, listener);
    }
}
