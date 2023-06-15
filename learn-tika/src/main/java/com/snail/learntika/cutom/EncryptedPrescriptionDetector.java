//package com.snail.learntika.cutom;
//
//import org.apache.tika.detect.Detector;
//import org.apache.tika.metadata.Metadata;
//import org.apache.tika.mime.MediaType;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.security.Key;
//
//public class EncryptedPrescriptionDetector implements Detector {
//    public MediaType detect(InputStream stream, Metadata metadata)
//            throws IOException {
//        Key key = Pharmacy.getKey();
//        MediaType type = MediaType.OCTET_STREAM;
//        InputStream lookahead =
//                new LookaheadInputStream(stream, 1024);
//        try {
//            Cipher cipher = Cipher.getInstance("RSA");
//            Listing 11.1 Custom type detector for encrypted prescription documents
//            Pharmacy’s
//            B private key
//
//            C of stream
//            D Decrypt stream
//            cipher.init(Cipher.DECRYPT_MODE, key);
//            InputStream decrypted =
//                    new CipherInputStream(lookahead, cipher);
//            QName name = new XmlRootExtractor()
//                    .extractRootElement(decrypted);
//            if (name != null
//                    && "http://example.com/
//            xpd ".equals(name.getNamespaceURI())
//                    && "prescription".equals(name.getLocalPart())){
//                type =
//                        MediaType.application("x-prescription");
//            }
//        } catch (GeneralSecurityException e) {
//// unable to decrypt, fall through
//        } finally {
//            lookahead.close();
//        }
//        return type;
//    }
//}