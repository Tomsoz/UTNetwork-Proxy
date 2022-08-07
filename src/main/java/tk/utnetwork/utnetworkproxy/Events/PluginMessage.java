package tk.utnetwork.utnetworkproxy.Events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.utnetwork.utnetworkproxy.UTNetworkProxy;

public class PluginMessage implements Listener {
    UTNetworkProxy plugin;
    public PluginMessage(UTNetworkProxy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("CommandAsPlayer" )) {
            if (e.getReceiver() instanceof ProxiedPlayer) {
                ProxiedPlayer receiver = (ProxiedPlayer) e.getReceiver();
                String cmd = in.readUTF();
                ProxyServer.getInstance().getPluginManager().dispatchCommand(receiver, cmd);
            }
        }

        if (subChannel.equalsIgnoreCase("MaxPlayerCountRequest" )) {
            if (e.getReceiver() instanceof ProxiedPlayer) {
                ProxiedPlayer receiver = (ProxiedPlayer) e.getReceiver();
                String server = in.readUTF();
                ServerInfo s = ProxyServer.getInstance().getServerInfo(server);
                if (s == null) return;
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("MaxPlayerCountRequest");
                out.writeUTF(s.getName());
                out.writeUTF(receiver.getServer().getInfo().getName());

                s.sendData("spigot:messaging", out.toByteArray());
            }
        }

        if (subChannel.equalsIgnoreCase("MaxPlayerCountResponse" )) {
            if (e.getReceiver() instanceof ProxiedPlayer) {
                ProxiedPlayer receiver = (ProxiedPlayer) e.getReceiver();
                String servers = in.readUTF();
                String[] serversList = servers.split("_____");
                ServerInfo from = ProxyServer.getInstance().getServerInfo(serversList[0]);
                ServerInfo to = ProxyServer.getInstance().getServerInfo(serversList[1]);
                if (from == null || to == null) return;
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("MaxPlayerCountResponse");
                out.writeUTF(from.getName());

                to.sendData("spigot:messaging", out.toByteArray());
            }
        }
    }
}
