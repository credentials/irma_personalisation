package nl.ru.cs.irma.irmawriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.CharBuffer;
import java.util.Properties;

import org.jasypt.util.text.BasicTextEncryptor;


public class EncryptedLoader {
	BasicTextEncryptor encryptor;
	String filename;
	
	public EncryptedLoader(char[] cs, String filename) {
		encryptor = new BasicTextEncryptor();
		encryptor.setPasswordCharArray(cs);
		this.filename = filename;
	}
	
	public Properties load() throws IOException {
		StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new FileReader(filename));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        
        String decrypted = encryptor.decrypt(fileData.toString());
		
		Properties props = new Properties();
		props.load(new StringReader(decrypted));
		return props;
	}
	
	public void save(Properties properties) throws IOException {
		StringWriter stringWriter = new StringWriter();
		properties.store(stringWriter, "");
		
		String encrypted = encryptor.encrypt(stringWriter.toString());
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		bw.write(encrypted);
		bw.close();
	}
}
