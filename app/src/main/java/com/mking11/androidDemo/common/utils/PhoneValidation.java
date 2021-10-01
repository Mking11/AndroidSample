package com.mking11.androidDemo.common.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneValidation {

    public boolean isPhoneNumberValid(String phoneNumber, String countryCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            Boolean result = phoneUtil.isValidNumber(numberProto);

//            System.out.println("result phone" + result);
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
//            System.err.println("Number Parse Exception  was thrown" + e.toString());
        }
        return false;

    }
}
