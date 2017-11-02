package com.guina.loratracker.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncodeUtil
{
    public static void main(String[] args) throws Exception
    {
        System.out.println(md5Encrypt16("��Ҫ����MD5���ܵ��ַ���"));
    }

    /**
     * MD5����16λ
     * @param encryptStr Ҫ��������
     * @return ����16λ���ܽ��
     * ZhaoLi
     */
    public static String md5Encrypt16(String encryptStr)
    {
        return md5Encrypt32(encryptStr).substring(8, 24);
    }

    /**
     * MD5����32λ
     * @param encryptStr Ҫ��������
     * @return 32λ���ܽ��
     * ZhaoLi
     */
    public static String md5Encrypt32(String encryptStr)
    {
        MessageDigest md5;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++)
            {
                int val = (md5Bytes[i]) & 0xff;
                if (val < 16)
                {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString().toLowerCase();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /** 
     * ���base64ʵ��md5���� 
     * @param msg �������ַ��� 
     * @return ��ȡmd5��תΪbase64 
     * @throws Exception 
     */
    public static String md5EncryptBase64(String msg) throws Exception
    {
        return msg == null ? null : base64Encode(md5(msg));
    }

    /** 
     * ��byte[]תΪ���ֽ��Ƶ��ַ��� 
     * @param bytes byte[] 
     * @param radix ����ת�����Ƶķ�Χ����Character.MIN_RADIX��Character.MAX_RADIX��������Χ���Ϊ10���� 
     * @return ת������ַ��� 
     */
    public static String binary(byte[] bytes, int radix)
    {
        return new BigInteger(1, bytes).toString(radix);// �����1��������  
    }

    /** 
     * base 64 encode 
     * @param bytes �������byte[] 
     * @return ������base 64 code 
     */
    public static String base64Encode(byte[] bytes)
    {
        return new BASE64Encoder().encode(bytes);
    }

    /** 
     * base 64 decode 
     * @param base64Code �������base 64 code 
     * @return ������byte[] 
     * @throws Exception 
     */
    public static byte[] base64Decode(String base64Code)
    {
        try
        {
            return base64Code == null ? null : new BASE64Decoder().decodeBuffer(base64Code);
        } catch (IOException e)
        {
            throw new RuntimeException("��������", e);
        }
    }

    /** 
     * ��ȡbyte[]��md5ֵ 
     * @param bytes byte[] 
     * @return md5 
     * @throws Exception 
     */
    public static byte[] md5(byte[] bytes)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("��������", e);
        }
        md.update(bytes);
        return md.digest();
    }

    /** 
     * ��ȡ�ַ���md5ֵ 
     * @param msg  
     * @return md5 
     * @throws Exception 
     */
    public static byte[] md5(String msg)
    {
        return msg == null ? null : md5(msg.getBytes());
    }
}
