package abnormes.ftp;

import info.abnormes.ftp.FtpClient;
import info.abnormes.ftp.impl.SimpleFtpClient;
import org.junit.jupiter.api.*;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FtpClientIntegrationTest {

    private FakeFtpServer ftpServer;
    private FtpClient ftpClient;

    @BeforeAll
    void setup() {
        String username = "test_user";
        String password = "qwerty";

        initFtpClient(username, password);
        initFtpServer(username, password);
    }

    private void initFtpClient(String username, String password) {
        ftpClient = new SimpleFtpClient(username, password);
    }

    private void initFtpServer(String username, String password) {
        ftpServer = new FakeFtpServer();

        ftpServer.addUserAccount(new UserAccount(username, password, "/data"));
        ftpServer.setFileSystem(initUnixFileSystem());
    }

    private FileSystem initUnixFileSystem() {
        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/test.txt", "test rows"));

        return fileSystem;
    }

    @Test
    @DisplayName("Test for check connection method")
    @Order(1)
    void checkMethodImpl() {
        try {
            startFtpClient();
            startFtpServer();

            Assertions.fail("Problems with connection method implementation");
        } catch (IOException e) {
            System.out.println("Test #1 passed");
        }
    }

    private void startFtpServer() {
        ftpServer.start();
    }

    private void startFtpClient() throws IOException {
        ftpClient.connect();
    }

    @Test
    @DisplayName("Test for check server connection")
    @Order(2)
    void testServerConnection() {
        try {
            startFtpServer();
            startFtpClient();

            System.out.println("Test #1 passed");
        } catch (IOException e) {
            Assertions.fail("Problems with server connection");
        }
    }

    @AfterAll
    void teardown() {
        ftpClient.disconnect();
        ftpServer.stop();
    }
}
