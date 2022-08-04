package tk.utnetwork.utnetworkproxy.Events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
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
        if (!e.getTag().equalsIgnoreCase("spigot:messaging")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("CommandAsPlayer" )) {
            if (e.getReceiver() instanceof ProxiedPlayer) {
                ProxiedPlayer receiver = (ProxiedPlayer) e.getReceiver();
                String cmd = in.readUTF();
                receiver.chat("/" + cmd);
            }
//            the receiver is a server when the proxy talks to a server
//            if (e.getReceiver() instanceof Server) {
//                Server receiver = (Server) e.getReceiver();
//                do things
//            }
        }
    }
}
