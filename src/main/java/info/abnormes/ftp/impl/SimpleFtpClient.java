package info.abnormes.ftp.impl;

import info.abnormes.ftp.FtpClient;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class SimpleFtpClient implements FtpClient {

    private final String server;
    private final Integer port;
    private final String username;
    private final String password;

    private URLConnection urlConnection;
    private InputStream inputStream;

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
        String ftpUrl = getFtpUrl();
        urlConnection = new URL(ftpUrl).openConnection();
        inputStream = urlConnection.getInputStream();
    }

    private String getFtpUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder   .append("ftp://")
                        .append(username)
                        .append(":")
                        .append(password)
                        .append("@")
                        .append(server)
                        .append(":")
                        .append(port);

        return stringBuilder.toString();
    }

    @Override
    public void getFilesList() {
        String result = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
        System.out.println(result);
    }

    @Override
    public void disconnect() throws IOException {
        inputStream.close();
    }
}
