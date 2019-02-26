package website.petrov.noue.common.component;

import android.net.TrafficStats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.net.SocketFactory;

public class SafeSocketFactory extends SocketFactory {
    private final SocketFactory delegate;

    public SafeSocketFactory() {
        this.delegate = SocketFactory.getDefault();
    }

    @Override
    @NonNull
    public Socket createSocket() throws IOException {
        final Socket socket = delegate.createSocket();
        return configureSocket(socket);
    }

    @Override
    @NonNull
    public Socket createSocket(@Nullable String host, int port) throws IOException {
        final Socket socket = delegate.createSocket(host, port);
        return configureSocket(socket);
    }

    @Override
    @NonNull
    public Socket createSocket(@Nullable String host, int port, @NonNull InetAddress localAddress,
                                         int localPort) throws IOException {
        final Socket socket = delegate.createSocket(host, port, localAddress, localPort);
        return configureSocket(socket);
    }

    @Override
    @NonNull
    public Socket createSocket(@NonNull InetAddress host, int port) throws IOException {
        final Socket socket = delegate.createSocket(host, port);
        return configureSocket(socket);
    }

    @Override
    @NonNull
    public Socket createSocket(@NonNull InetAddress host, int port, @NonNull InetAddress localAddress,
                                         int localPort) throws IOException {
        final Socket socket = delegate.createSocket(host, port, localAddress, localPort);
        return configureSocket(socket);
    }

    @Contract("_ -> param1")
    @NonNull
    private Socket configureSocket(@NonNull Socket socket) throws SocketException {
        TrafficStats.setThreadStatsTag(6973100);
        TrafficStats.tagSocket(socket);
        return socket;
    }
}
