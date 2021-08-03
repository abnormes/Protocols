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
        String homeDir = "/data";

        ftpServer.addUserAccount(new UserAccount(username, password, homeDir));
        ftpServer.setFileSystem(initUnixFileSystem(homeDir));
    }

    private FileSystem initUnixFileSystem(String homeDir) {
        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry(homeDir));
        fileSystem.add(new FileEntry(homeDir + "/test.txt", "test rows"));
        fileSystem.add(new FileEntry(homeDir + "/test2.txt", "fileSystem.add(new FileEntry(homeDir + \"/test.txt\", \"test rows\"));"));

        return fileSystem;
    }

    @Test
    @DisplayName("1. Test for check connection method")
    void test1CheckMethodImpl() {
        try {
            startFtpClient();
            startFtpServer();

            Assertions.fail("Problems with connection method implementation");
        } catch (IOException e) {
            System.out.println("Test #1 passed");
        }
    }

    @Test
    @DisplayName("2. Test for check server connection")
    void test2ServerConnection() {
        try {
            startFtpServer();
            startFtpClient();

            System.out.println("Test #2 passed");
        } catch (IOException e) {
            Assertions.fail("Problems with server connection");
        }
    }

    @Test
    @DisplayName("3. Check files list")
    void test3checkFiles() {
        try {
            startFtpServer();
            startFtpClient();

            ftpClient.getFilesList();
        } catch (IOException e) {
            Assertions.fail("Problems with getList implementation");
        }
    }

    private void startFtpServer() {
        ftpServer.start();
    }

    private void startFtpClient() throws IOException {
        ftpClient.connect();
    }

    @AfterAll
    void teardown() {
        try {
            ftpClient.disconnect();
            ftpServer.stop();
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
