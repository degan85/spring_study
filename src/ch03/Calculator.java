package ch03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

//	1
//	public Integer calSum(String filepath) throws IOException {
//		BufferedReader br = null;
//		
//		try {
//			br = new BufferedReader(new FileReader(filepath));
//			Integer sum = 0;
//			String line = null;
//			while((line = br.readLine()) != null) {
//				sum += Integer.valueOf(line);
//			}
//			return sum;
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(br != null) { // BufferReader ������Ʈ�� �����Ǳ� ���� ���ܰ� �߻��� ���� ����
//				try{ br.close(); } 
//				catch(IOException e) {System.out.println(e.getMessage()); }
//			}
//		}
//	}
//	--------------------------------------------

	
	/*
	 * ���ϱ� ���� ���ڸ� �̿��� ��ɵ� �߰� ��û
	 * ���ø�/�ݹ� ���� ��������
	 * ���ø��� �ݹ��� ��踦 ���ϰ� 
	 * ���ø��� �ݹ鿡��, �ݹ��� ���ø����� ���� �����Ϸ��� ������ �������� �ľ�
	 * �׿� ���� �������̽��� �����ؾ� ��
	 */
//	2
//	public Integer calSum(String filepath) throws IOException {
//		
//		BufferedReaderCallBack sumCallBack =  new BufferedReaderCallBack() {
//			public Integer doSomethingWithReader(BufferedReader rd) throws NumberFormatException, IOException {
//				Integer sum = 0;
//				String line = null;
//				while((line = rd.readLine()) != null) {
//					sum += Integer.valueOf(line);
//				}
//				return sum;
//			}
//		};
//		return fileReadTemplate(filepath, sumCallBack);
//	}
	
	public Integer calSum(String filepath) throws IOException {
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			public Integer doSomethingWithLine(String line, Integer value) {
				return value + Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filepath, sumCallback, 0);
	}

	public Integer calcMultiply(String filepath) throws IOException {
		LineCallback<Integer> multiplyCallback = new LineCallback<Integer>() {
			public Integer doSomethingWithLine(String line, Integer value) {
				return value *= Integer.valueOf(line);
			}
		};		
		return lineReadTemplate(filepath, multiplyCallback, 1);
	}
	
	public String concatenate(String filepath) throws IOException {
		LineCallback<String> concatenateCallback = new LineCallback<String>() {
			public String doSomethingWithLine(String line, String value) {
				return value + line;
			}
		};
		return lineReadTemplate(filepath, concatenateCallback, "");
	}
	
	private Integer fileReadTemplate(String filepath, BufferedReaderCallBack callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			int ret = callback.doSomethingWithReader(br);	// �ݹ� ������Ʈ ȣ��
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(br != null) { // BufferReader ������Ʈ�� �����Ǳ� ���� ���ܰ� �߻��� ���� ����
				try{ br.close(); } 
				catch(IOException e) {System.out.println(e.getMessage()); }
			}
		}
	}
	
	private <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			T res = initVal;	
			String line = null;
			while((line = br.readLine()) != null) {	// ������ �� ������ ������ ���鼭 �������� �͵� ���ø��� ���
				res = callback.doSomethingWithLine(line, res);	// �� ������ ������ ������ ����ϴ� �۾��� �ݹ鿡�� �ñ�
			}
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(br != null) { // BufferReader ������Ʈ�� �����Ǳ� ���� ���ܰ� �߻��� ���� ����
				try{ br.close(); } 
				catch(IOException e) {System.out.println(e.getMessage()); }
			}
		}
	}
	
}












