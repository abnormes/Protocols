package info.abnormes.ftp;

import java.io.IOException;

/**
 * Test FTP client, should have only two options, connect and disconnect to FTP server
 */
public interface FtpClient {

    // Method to connect to server, if connect is failed, should return false
    void connect() throws IOException;

    // Method to disconnect from server, if disconnection is failed, should return false
    void disconnect() throws IOException;

    void getFilesList();
}
