package controller;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword {
    public String encript(String password,String key){
        return enciptPassword(password,key);
    }
    public String dncript(String password,String key) {
        return deciptPassword(password,key);
    }
//    加密
    private  String enciptPassword(String clearText,String originKey) {
//        1.获取加密算法工具类对象
        String encode=null;
        try {
            Cipher cipher=Cipher.getInstance("DES");
//        2.对工具类对象初始化
//        mode：加密/解密模式
//        key：对原始密钥处理之后的密钥
            SecretKeySpec key=getKey(originKey);
            cipher.init(Cipher.ENCRYPT_MODE,key);
//        3用加密工具类对象对明文进行加密
            byte[] doFinal=cipher.doFinal(clearText.getBytes());
            encode= Base64.encode(doFinal);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return encode;
    }
//    解密
    private  String deciptPassword(String cipherText,String originKey)  {
//        1.获取加密算法工具类对象
        byte[] doFinal=null;
        try {
            Cipher cipher=Cipher.getInstance("DES");
//        2.对工具类对象初始化
//        mode：加密/解密模式
//        key：对原始密钥处理之后的密钥
            Key key=getKey(originKey);
            cipher.init(Cipher.DECRYPT_MODE,key);
//        3用加密工具类对象对明文进行加密
            byte[] decode=Base64.decode(cipherText);
            doFinal=cipher.doFinal(decode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(doFinal);
    }
    private static SecretKeySpec getKey(String originKey){
//        根据给定的字节数组构造一个密钥
        byte[] buffer=new byte[8];
        byte[] originBytes=originKey.getBytes();
        for(int i=0;i<originBytes.length&&i<8;i++){
            buffer[i]=originBytes[i];
        }
        SecretKeySpec key=new SecretKeySpec(buffer,"DES");
      return key;
    }
}