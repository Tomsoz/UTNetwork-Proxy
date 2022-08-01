package tk.utnetwork.utnetworkproxy;

import com.google.common.collect.HashBiMap;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import tk.utnetwork.utnetworkproxy.Commands.Administraton.*;
import tk.utnetwork.utnetworkproxy.Commands.General.GList;
import tk.utnetwork.utnetworkproxy.Commands.General.Hub;
import tk.utnetwork.utnetworkproxy.Commands.General.Message;
import tk.utnetwork.utnetworkproxy.Commands.General.Reply;
import tk.utnetwork.utnetworkproxy.Commands.Staff.Find;
import tk.utnetwork.utnetworkproxy.Commands.Staff.Server;
import tk.utnetwork.utnetworkproxy.Commands.Staff.StaffChat;
import tk.utnetwork.utnetworkproxy.Events.ChatEvent;
import tk.utnetwork.utnetworkproxy.Events.JoinEvent;
import tk.utnetwork.utnetworkproxy.Events.QuitEvent;
import tk.utnetwork.utnetworkproxy.Misc.ConfigManager;
import tk.utnetwork.utnetworkproxy.Misc.Utils;

import java.util.HashMap;
import java.util.UUID;

public final class UTNetworkProxy extends Plugin {
    Utils utils;
    ConfigManager config = new ConfigManager(this);
    public HashMap<String, String> replyUsers = new HashMap<String, String>();

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
        // ----------------
        // ADMINISTRATION
        // ----------------
        registerCommand(new Send(this, "send", "proxy.send", new String[] {}));
        registerCommand(new Alert(this, "alert", "proxy.alert", new String[] {}));
        registerCommand(new AlertRaw(this, "alertraw", "proxy.alertraw", new String[] {}));
        registerCommand(new AdminChat(this, "adminchat", "proxy.adminchat", new String[] {"ac"}));

        // ----------------
        // STAFF
        // ----------------
        registerCommand(new Find(this, "find", "proxy.find", new String[] {}));
        registerCommand(new Server(this, "server", "proxy.server", new String[] {}));
        registerCommand(new StaffChat(this, "staffchat", "proxy.staffchat", new String[] {"sc"}));

        // ----------------
        // GENERAL
        // ----------------
        registerCommand(new Hub(this, "hub", "proxy.hub", new String[] {"l", "lobby", "h"}));
        registerCommand(new GList(this, "glist", "proxy.glist", new String[] {}));
        registerCommand(new Message(this, "message", "proxy.message", new String[] {"msg", "whisper", "w"}));
        registerCommand(new Reply(this, "reply", "proxy.message", new String[] {"r"}));
    }

    public void registerEvents() {
        registerEvent(new JoinEvent(this));
        registerEvent(new QuitEvent(this));
        registerEvent(new ChatEvent(this));
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
