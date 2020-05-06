package com.ebdaa.katkot.pojo.utils.security;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encrypted {
    public static String funEncryption(String text)
    {
        String encryptedPassword1="";
        try {
            //encryptedPassword1 = Security.encrypt("INSERT INTO [dbo].[tblAndroidCustomers]([TheUserID],[FullName],[DeviceID],[UserPassword],[PhoneNumber],[UserID],[BranchID],[Port],[UserType])VALUES( 2,'','po','09','7524215' ,1 ,1,1,2)","koxskfruv78dslbsxu177");
            encryptedPassword1 = Security.encrypt(text.trim(),"koxskfruv78dslbsxu177");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        Log.i("encryptedPassword1",encryptedPassword1);
        return encryptedPassword1;
    }
}
