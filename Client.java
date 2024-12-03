import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 4000;

        // Load the client truststore
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream("clientTruststore.jks"), "passserver123".toCharArray()); // Update with your truststore password

        // Initialize TrustManagerFactory with the client's truststore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // Initialize SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Create SSL Socket
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket(host, port);

        // Communication with the server
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        output.println("Hello, server! Secure communication established.");
        String response = input.readLine();
        System.out.println("Received from server: " + response);

        socket.close();
    }
}
