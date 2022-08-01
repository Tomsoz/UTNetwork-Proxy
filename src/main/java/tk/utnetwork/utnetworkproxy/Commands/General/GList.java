package tk.utnetwork.utnetworkproxy.Commands.General;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tk.utnetwork.utnetworkproxy.Misc.Utils;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

import java.util.ArrayList;

public class GList extends Command {
    UTNetworkProxy plugin;
    public GList(UTNetworkProxy plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        int maxPlayers = 0;
        for (ListenerInfo listener : ProxyServer.getInstance().getConfigurationAdapter().getListeners()){
            maxPlayers = listener.getMaxPlayers();
            break;
        }
        sender.sendMessage(Utils.chat("%pTotal Online Players: %s" + ProxyServer.getInstance().getPlayers().size() + "/" + maxPlayers));
        for (ServerInfo s : ProxyServer.getInstance().getServersCopy().values()) {
            ArrayList<String> players = new ArrayList<>();
            for (ProxiedPlayer p : s.getPlayers()) {
                players.add("%s" + p.getDisplayName() + "%p");
            }
            String list = players.toString();
            list = list.substring(1);
            list = list.substring(0, list.length() - 1);
            sender.sendMessage(Utils.chat("%s" + s.getName() + "%p (%s" + s.getPlayers().size() + "%p): " + list));
        }
    }
}
