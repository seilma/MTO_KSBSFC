package SMS;

import java.net.URL;

import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.OutboundWapSIMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

public class SendMessage {

    public void sendMessage() throws Exception {
        OutboundNotification outboundNotification = new OutboundNotification();
        System.out.println("Sample of Send message from a serial gsm modem.");
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());
        SerialModemGateway gateway = new SerialModemGateway("modem.com14",
                "COM14", 460800, "Huawei", "");
        gateway.setInbound(false);
        gateway.setOutbound(true);
        // gateway.setSimPin("");
        Service.getInstance().setOutboundMessageNotification(
                outboundNotification);
        Service.getInstance().addGateway(gateway);
        Service.getInstance().startService();
        System.out.println();
        System.out.println("Modem Information:");
        System.out.println("  Manufacturer: " + gateway.getManufacturer());
        System.out.println("  Model: " + gateway.getModel());
        System.out.println("  Serial No: " + gateway.getSerialNo());
        System.out.println("  SIM IMSI: " + gateway.getImsi());
        System.out.println("  Signal Level: " + gateway.getSignalLevel()
                + " dBm");
        System.out.println("  Battery Level: " + gateway.getBatteryLevel()
                + "%");

        // Send a message synchronously.
        OutboundMessage msg = new OutboundMessage("+94123456789",
                "SMS test: sample message from StackOverflow");

        Service srvice = Service.getInstance();
        // Service.getInstance().sendMessage(msg);
        System.out.println(msg);
        // Or, send out a WAP SI message.
        OutboundWapSIMessage wapMsg = new OutboundWapSIMessage("+21651021649",
                new URL("http://stackoverflow.com/"),
                "WAP test: sample message from StackOverflow!");
        // gateway.setFrom("chandpriyankara");
        // wapMsg.setFrom("chandpriyankara");
        srvice.queueMessage(wapMsg);

        Service.getInstance().stopService();
    }

    /**
     * Outbound Message informations handler
     * 
     * @author chandpriyankara
     * 
     */
    public class OutboundNotification implements IOutboundMessageNotification {
        public void process(AGateway gateway, OutboundMessage msg) {
            System.out.println("Outbound handler called from Gateway: "
                    + gateway.getGatewayId());
            System.out.println(msg);
        }
    }

    public static void main(String args[]) {
        SendMessage app = new SendMessage();
        try {
            app.sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}