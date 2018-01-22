//package sslserver;
//
//import database.Database;
//import general.T;
//import general.OtpStats;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.security.Key;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.Certificate;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLServerSocket;
//import javax.net.ssl.SSLServerSocketFactory;
//import javax.net.ssl.SSLSocket;
//import javax.net.ssl.TrustManagerFactory;
//
//public class SSLserver extends Thread {
//
//    private SSLServerSocket providerSocket; 
//    private SSLSocket connection;
//    private Thread actionsForClient;
//    private Database db;
//    private HashMap <String, OtpStats> otpUsers;
//
//    public SSLserver() {
//        otpUsers = new HashMap();
//        db = new Database();
//    }
//
//    @Override
//    public void run() {
//        try {
//            createServerSocket();
//            try {
//                while (true) {
//                    waitForConnection();
//                    actionsForClient = new ActionsForClient(connection, db, otpUsers);
//                    actionsForClient.start();
//                }
//            } catch (Exception e) {
//                System.out.println("Server closed by exception");
//                e.printStackTrace();
//            } finally {
//                System.out.println("Server closed");
//                closeCrap();
//            }
//        } catch (Exception e) {
//            System.out.println("ServerSocket was not created");
//            e.printStackTrace();
//        }
//
//    }
//
//    private void createServerSocket() throws IOException {
//        try {
////            String keystoreName = "sslsec.jks";
////            String keystorePwd = "sslsec";
//            char[] keystorePwdArray = "sslsec".toCharArray();
//            char[] keystorePwdArray2 = "sslsec".toCharArray();
//
////            System.setProperty("javax.net.ssl.trustStore", keystoreName);
////            System.setProperty("javax.net.ssl.trustStorePassword", keystorePwd);
//
//            KeyStore keyStore = KeyStore.getInstance("JKS");
//            FileInputStream is = new FileInputStream(T.CERT_PATH);
//
//            keyStore.load(is, keystorePwdArray);
//            is.close();
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
//            kmf.init(keyStore, keystorePwdArray2);
//
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
//            tmf.init(keyStore);
//
//            SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//            SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
//
//            providerSocket = (SSLServerSocket) ssf.createServerSocket(T.PORT);
//            
//            printKeyPair(keyStore, "sslsec", "sslsec");
//
//        } catch (Exception e) {
//            System.out.println("Create Socket exception");
//            e.printStackTrace();
//
//        }
//    }
//    
//    public void printKeyPair(final KeyStore keystore, final String alias, final String password) {
//        final Key key;
//        try {
//            key = (PrivateKey) keystore.getKey(alias, password.toCharArray());
//            final Certificate cert = keystore.getCertificate(alias);
//            final PublicKey publicKey = cert.getPublicKey();
//
//            System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
//            System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        
//    }
//
//    private void waitForConnection() throws IOException {
//        System.out.println("Message: Waiting for connection");
//        providerSocket.setNeedClientAuth(true);
//        connection = (SSLSocket) providerSocket.accept();
//    }
//
//    private void closeCrap() {
//        System.out.println("Message: " + "Closing Connections in server main...");
//        try {
//            if (connection != null) {
//                connection.close();
//            }
//            if (providerSocket != null) {
//                providerSocket.close();
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }
//
//}
