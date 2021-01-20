package BadSockets;

import ChannelModels.ChannelModel;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Class that inherits from BadSocket.
 */
public class BadServerSocket extends ServerSocket {

    /**
     * Constructor for BadServerSocket with channel
     *
     * @param channelModel channel model for each socket
     * @param port port
     * @throws IOException standard ServerSocket exception
     */
    public BadServerSocket(ChannelModel channelModel, int port) throws IOException {
        super(port);
        this.channelModel = channelModel;
    }

    /**
     * Constructor for BadServerSocket with standard channel
     *
     * @param port port
     * @throws IOException standard ServerSocket exception
     */
    public BadServerSocket(int port) throws IOException {
        super(port);
        this.channelModel = null;
    }

    /**
     * Overload for SocketServer in order to give BadSockets instead of standard Sockets.
     *
     * @return BadSocket bound to the request
     * @throws IOException from Socket.implAccept call
     */
    @Override
    public BadSocket accept() throws IOException {
        BadSocket b;
        if(channelModel != null)
           b = new BadSocket();
        else
            b = new BadSocket(channelModel);
        this.implAccept(b);

        return b;
    }

    ChannelModel channelModel;
}
