package info.abnormes.ftp.impl;

import info.abnormes.ftp.FtpClient;

import java.io.IOException;

public class SimpleFtpClient implements FtpClient {

    private String server;
    private Integer port;
    private String username;
    private String password;

    /**
     * Constructor with custom server and port
     *
     * @param server connection address
     * @param port connection port
     * @param username ftp username
     * @param password ftp password
     */
    public SimpleFtpClient(String server, Integer port, String username, String password) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor for custom server with default port (21)
     *
     * @param server connection address
     * @param username ftp username
     * @param password ftp password
     */
    public SimpleFtpClient(String server, String username, String password) {
        this.server = server;
        this.username = username;
        this.password = password;
        this.port = 21;
    }

    /**
     * Constructor for local servers with 'localhost' address and default port (21)
     *
     * @param username ftp username
     * @param password ftp password
     */
    public SimpleFtpClient(String username, String password) {
        this.server = "localhost";
        this.port = 21;
        this.username = username;
        this.password = password;
    }

    @Override
    public void connect() throws IOException {
        throw new IOException("Exception");
    }

    @Override
    public void disconnect() {

    }
}
