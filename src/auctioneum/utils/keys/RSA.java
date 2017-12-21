package auctioneum.utils.keys;


import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class RSA {


    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA","BC");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    public void savePubKey(PublicKey publicKey, String filePath){
        try {
            X509EncodedKeySpec x509ks = new X509EncodedKeySpec(
                    publicKey.getEncoded());
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(x509ks.getEncoded());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void savePrivKey(PrivateKey privateKey, String filePath){
        try {
            PKCS8EncodedKeySpec pkcsKeySpec = new PKCS8EncodedKeySpec(
                    privateKey.getEncoded());
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(pkcsKeySpec.getEncoded());
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static PublicKey getPublicKeyFromString(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(X509publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sign(String message, PrivateKey privateKey){
        try {
            Signature signer = Signature.getInstance("SHA256withRSA");
            signer.initSign(privateKey);
            signer.update(message.getBytes());
            return Base64.getEncoder().encodeToString(signer.sign());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static boolean verify(String message, String signature, PublicKey publicKey){
        try {
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(message.getBytes());
            return verifier.verify(Base64.getDecoder().decode(signature));
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }



}
