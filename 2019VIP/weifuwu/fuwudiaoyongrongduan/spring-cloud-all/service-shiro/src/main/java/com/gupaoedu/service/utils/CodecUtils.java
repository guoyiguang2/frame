package com.gupaoedu.service.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * 主要使用主流的几种加密，如base64，AES，DES，SHA-1等
 * https://blog.csdn.net/kkkkkkkkim/article/details/54585480
 *
 * /***
 *  * MD5是可以解密的（网上很多人说不可逆的，所以实践才是检验真理的唯一标准）
 *  * 1996年后被证实存在弱点，
 *  * 可以被加以破解，
 *  * 对于需要高度安全性的数据，
 *  * 专家一般建议改用其他算法，如SHA-2。
 *  * 2004年，证实MD5算法无法防止碰撞（collision），因此不适用于安全性认证，如SSL公开密钥认证或是数字签名等用途。
 *  *
 *  */
public class CodecUtils {


    private CodecUtils() {
    }

    private final static Logger Log = LoggerFactory.getLogger(CodecUtils.class);
    /** Base64 编码 */
    private static final Base64 B64 = new Base64();
    /** 安全的随机数源 */
    private static final SecureRandom RANDOM = new SecureRandom();
    /** SHA-1加密 */
    private static MessageDigest SHA_1 = null;


    static {
        init();
    }

    /** 初始化 */
    private static void init() {
        try {
            SHA_1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }


    private static final String key0 = "FECOI()*&<MNCXZPKL";
    private static final Charset charset = Charset.forName("UTF-8");
    private static byte[] keyBytes = key0.getBytes(charset);


    /***
     * 自定义加密解密算法
     */
    public static String encode(String enc) {
        byte[] b = enc.getBytes(charset);
        for (int i = 0, size = b.length; i < size; i++) {
            for (byte keyBytes0 : keyBytes) {
                b[i] = (byte) (b[i] ^ keyBytes0);
            }
        }
        return new String(b);
    }

    public static String decode(String dec) {
        byte[] e = dec.getBytes(charset);
        byte[] dee = e;
        for (int i = 0, size = e.length; i < size; i++) {
            for (byte keyBytes0 : keyBytes) {
                e[i] = (byte) (dee[i] ^ keyBytes0);
            }
        }
        return new String(e);
    }



    /**
     * SHA-1加密
     *
     * @param str
     *            明文
     * @return 密文
     */
    public static String sha1(String str) {
        return new String(B64.encode(SHA_1.digest(str.getBytes())));
    }

    /**
     * SHA-1加密(Url安全)
     *
     * @param str 明文
     * @return  密文
     */
    public static String sha1Url(String str) {
        return new String(Base64.encodeBase64URLSafeString(SHA_1.digest(str.getBytes())));
    }

    /**
     * Base64编码
     *
     * @param bs
     *            byte数组
     * @return 编码后的byte数组
     */
    public static byte[] b64Encode(byte[] bs) {
        return B64.encode(bs);
    }

    /**
     * Base64编码字符串
     *
     * @param str
     *            需要编码的字符串
     * @return 编码后的字符串
     */
    public static String b64Encode(String str) {
        if (null != str) {
            return new String(B64.encode(str.getBytes()));
        }
        return null;
    }

    /**
     * Base64编码字符串(Url安全)
     *
     * @param str
     *            需要编码的字符串
     * @return 编码后的字符串
     */
    public static String b64Url(String str) {
        if (null != str) {
            return Base64.encodeBase64URLSafeString(str.getBytes());
        }
        return null;
    }

    /**
     * Base64解码
     *
     * @param bs
     *            byte数组
     * @return 解码后的byte数组
     */
    public static byte[] b64Decode(byte[] bs) {
        return B64.decode(bs);
    }

    /**
     * Base64解码字符串
     *
     * @param str
     *            需要解码的字符串
     * @return 解码后的字符串
     */
    public static String b64Decode(String str) {
        if (null != str) {
            byte[] bs = B64.decode(str.getBytes());
            if (null != bs) {
                return new String(bs);
            }
        }
        return null;
    }

    /**
     * 生成32位MD5密文
     *
     * <pre>
     * org.apache.commons.codec.digest.DigestUtils
     * </pre>
     *
     * @param str
     *            明文
     * @return 密文
     */
    public static String md5(String str) {
        if (null != str) {
            return DigestUtils.md5Hex(str);
        }
        return null;
    }

    /** DES加密算法 */
    private static final String DES_ALGORITHM = "DESede"; // 可用 DES,DESede,Blowfish
    /** DES默认加密 */
    private static Cipher DES_CIPHER_ENC = null;
    /** DES默认解密 */
    private static Cipher DES_CIPHER_DEC = null;

    static {
        // 添加JCE算法
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        // 初始化默认DES加密
        try {
            // 密钥
            SecretKey desKey = new SecretKeySpec(new byte[] { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74,
                    (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 }, DES_ALGORITHM);
            // 初始化默认加密
            DES_CIPHER_ENC = Cipher.getInstance(DES_ALGORITHM);
            DES_CIPHER_ENC.init(Cipher.ENCRYPT_MODE, desKey, RANDOM);
            // 初始化默认解密
            DES_CIPHER_DEC = Cipher.getInstance(DES_ALGORITHM);
            DES_CIPHER_DEC.init(Cipher.DECRYPT_MODE, desKey, RANDOM);
        } catch (Exception e) {
            System.out.println("DES默认加密解密初始化失败");
            throw new RuntimeException("DES默认加密解密初始化失败：" + e.getMessage(), e);
        }
    }

    /**
     * DES加密(默认密钥)
     *
     * @param str
     *            需要加密的明文
     * @return 加密后的密文(base64编码字符串)
     */
    public static String desEncryp(String str) {
        return desEncryp(str, false);
    }

    /**
     * DES加密(默认密钥)
     *
     * @param str
     *            需要加密的明文
     * @return 加密后的密文(base64编码字符串,Url安全)
     */
    public static String desEncrypUrl(String str) {
        return desEncryp(str, true);
    }

    /**
     * DES加密(默认密钥)
     *
     * @param str
     *            需要加密的明文
     * @param urlSafety
     *            密文是否需要Url安全
     * @return 加密后的密文(str为null返回null)
     */
    public static String desEncryp(String str, boolean urlSafety) {
        if (null != str) {
            try {
//                byte[] bytes = DES_CIPHER_ENC.doFinal(str.getBytes(StringUtils.UTF8));// 加密
                byte[] bytes = DES_CIPHER_ENC.doFinal(str.getBytes("UTF-8"));// 加密

                if (urlSafety) {
                    return Base64.encodeBase64URLSafeString(bytes);
                } else {
                    return new String(B64.encode(bytes));
                }
            } catch (Exception e) {
                Log.error("DES加密失败, 密文：" + str + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * DES解密(默认密钥)
     *
     * @param str
     *            需要解密的密文(base64编码字符串)
     * @return 解密后的明文
     */
    public static String desDecrypt(String str) {
        if (null != str) {
            try {
                byte[] bytes = DES_CIPHER_DEC.doFinal(B64.decode(str));// 解密
//                return new String(bytes, StringUtils.UTF8);
                return new String(bytes, "UTF-8");


            } catch (Exception e) {
                Log.error("DES解密失败, 密文：" + str + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * DES加密
     *
     * @param str
     *            需要加密的明文
     * @param key
     *            密钥(长度小于24字节自动补足，大于24取前24字节)
     * @return 加密后的密文(base64编码字符串)
     */
    public static String desEncryp(String str, String key) {
        return desEncryp(str, key, false);
    }

    /**
     * DES加密
     *
     * @param str
     *            需要加密的明文
     * @param key
     *            密钥(长度小于24字节自动补足，大于24取前24字节)
     * @return 加密后的密文(base64编码字符串,Url安全)
     */
    public static String desEncrypUrl(String str, String key) {
        return desEncryp(str, key, true);
    }

    /**
     * DES加密
     *
     * @param str
     *            需要加密的明文
     * @param key
     *            密钥(长度小于24字节自动补足，大于24取前24字节)
     * @param urlSafety
     *            密文是否需要Url安全
     * @return 加密后的密文(str/key为null返回null)
     */
    public static String desEncryp(String str, String key, boolean urlSafety) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance(DES_ALGORITHM);
                c.init(Cipher.ENCRYPT_MODE, desKey(key), RANDOM);
                // 加密
                //byte[] bytes = c.doFinal(str.getBytes(StringUtils.UTF8));// 加密
                byte[] bytes = c.doFinal(str.getBytes("UTF-8"));// 加密


                // 返回b64处理后的字符串
                if (urlSafety) {
                    return Base64.encodeBase64URLSafeString(bytes);
                } else {
                    return new String(B64.encode(bytes));
                }
            } catch (Exception e) {
                Log.error("DES加密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param str
     *            需要解密的密文(base64编码字符串)
     * @param key
     *            密钥(长度小于24字节自动补足，大于24取前24字节)
     * @return 解密后的明文
     */
    public static String desDecrypt(String str, String key) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance(DES_ALGORITHM);
                c.init(Cipher.DECRYPT_MODE, desKey(key), RANDOM);
                byte[] bytes = c.doFinal(B64.decode(str));
               // return new String(bytes, StringUtils.UTF8);
                return new String(bytes, "UTF-8");

            } catch (BadPaddingException e) {
                Log.error("DES解密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            } catch (Exception e) {
                Log.error("DES解密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /** DES密钥 */
    private static SecretKey desKey(String key) {
        byte[] bs = key.getBytes();
        if (bs.length != 24) {
            bs = Arrays.copyOf(bs, 24);// 处理数组长度为24
        }
        return new SecretKeySpec(bs, DES_ALGORITHM);
    }

    /** AES加密算法 */
    private static final String AES_ALGORITHM = "AES";

    /**
     * AES加密
     *
     * @param str
     *            需要加密的明文
     * @param key
     *            密钥
     * @return 加密后的密文(str/key为null返回null)
     */
    public static String aesEncryp(String str, String key) {
        return aesEncryp(str, key, false);
    }

    /**
     * AES加密
     *
     * @param str
     *            需要加密的明文
     * @param key
     *            密钥
     * @param urlSafety
     *            密文是否需要Url安全
     * @return 加密后的密文(str/key为null返回null)
     */
    public static String aesEncryp(String str, String key, boolean urlSafety) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.ENCRYPT_MODE, aesKey(key), RANDOM);
//                byte[] bytes = c.doFinal(str.getBytes(StringUtils.UTF8));// 加密
                byte[] bytes = c.doFinal(str.getBytes("UTF-8"));// 加密

                if (urlSafety) {
                    return Base64.encodeBase64URLSafeString(bytes);
                } else {
                    return new String(B64.encode(bytes));
                }
            } catch (Exception e) {
                Log.error("AES加密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param str
     *            需要解密的密文(base64编码字符串)
     * @param key
     *            密钥
     * @return 解密后的明文
     */
    public static String aesDecrypt(String str, String key) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.DECRYPT_MODE, aesKey(key), RANDOM);
//                return new String(c.doFinal(B64.decode(str)), StringUtils.UTF8);// 解密
                return new String(c.doFinal(B64.decode(str)), "UTF-8");// 解密

            } catch (BadPaddingException e) {
                Log.error("AES解密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            } catch (Exception e) {
                Log.error("AES解密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /** AES密钥 */
    private static SecretKeySpec aesKey(String key) {
        byte[] bs = key.getBytes();
        if (bs.length != 16) {
            bs = Arrays.copyOf(bs, 16);// 处理数组长度为16
        }
        return new SecretKeySpec(bs, AES_ALGORITHM);
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            //引用  java.security.MessageDigest公共类
            // getInstance("MD5")方法 设置加密格式
            md5 = MessageDigest.getInstance("MD5");  //此句是核心
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * MD5加码 生成32位md5码 加密解密算法[可逆] 执行一次加密，执行两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }



    public static void main(String[] args) {
        String s = "you are right";
        String enc = encode(s);
        String dec = decode(enc);
        System.out.println("加密后：" + enc);
        System.out.println("解密后：" + dec);

        //测试  MD5 加密解密
        String md = new String("AAAAAAAAAA");
        System.out.println("原始：" + md);
        System.out.println("MD5后：" + string2MD5(md));
        System.out.println("加密的：" + convertMD5(md));
        System.out.println("解密的：" + convertMD5(convertMD5(md)));
    }



}
