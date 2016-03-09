package com.gargorg.common.Utils;

import java.util.Scanner;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/*
 * This class provides functionality to encrypt and decrypt any string with the help of StandardPBEStringEncryptor
 */
public class EncryptionDecryptionUtility {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setPassword("password");

		System.out.println("Which operation you want to perform");
		System.out.println("1. Encryption");
		System.out.println("2. Decryption");
		System.out.println("Enter your choice:");
		int choice = sc.nextInt();
		System.out.println("Enter string for encryption/decryption:");
		String str = sc.next();
		switch(choice) {
		case 1:
			System.out.println("Encrypted string is:"+standardPBEStringEncryptor.encrypt(str));
		case 2:
			System.out.println("Decrypted string is:"+standardPBEStringEncryptor.decrypt(str));
		}
		sc.close();
	}
}