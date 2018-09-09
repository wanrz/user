package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Test {
	public static void main(String[] args) throws IOException {
//		System.out.println(16<<3);
//		System.out.println(16>>1);
//		System.out.println(8>>2);
//		ByteBuffer buffer=ByteBuffer.allocate(8);
//		buffer.putInt(16<<3);
//		System.out.println(Arrays.toString(buffer.array()));
		File file=new File("D:\\SVN.txt");
		BufferedReader br=new BufferedReader(new FileReader(file));
		while(br.ready()){
			System.out.println(br.readLine());
		}
//		BufferedWriter bw =new BufferedWriter(out, sz)
		new FileWriter(new File("D:\\1234.txt"));
	}
}
