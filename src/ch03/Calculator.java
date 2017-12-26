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
//			if(br != null) { // BufferReader 오브젝트가 생성되기 전에 예외가 발생할 수도 있음
//				try{ br.close(); } 
//				catch(IOException e) {System.out.println(e.getMessage()); }
//			}
//		}
//	}
//	--------------------------------------------

	
	/*
	 * 더하기 말고 숫자를 이용한 기능들 추가 요청
	 * 템플릿/콜백 패턴 적용하자
	 * 템플릿과 콜백의 경계를 정하고 
	 * 템플릿이 콜백에게, 콜백이 템플릿에게 각각 전달하려는 내용이 무엇인지 파악
	 * 그에 따라 인터페이스를 정의해야 함
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
			int ret = callback.doSomethingWithReader(br);	// 콜백 오브젝트 호출
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(br != null) { // BufferReader 오브젝트가 생성되기 전에 예외가 발생할 수도 있음
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
			while((line = br.readLine()) != null) {	// 파일의 각 라인을 루프를 돌면서 가져오는 것도 템플릿이 담당
				res = callback.doSomethingWithLine(line, res);	// 각 라인의 내용을 가지고 계산하는 작업만 콜백에게 맡김
			}
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(br != null) { // BufferReader 오브젝트가 생성되기 전에 예외가 발생할 수도 있음
				try{ br.close(); } 
				catch(IOException e) {System.out.println(e.getMessage()); }
			}
		}
	}
	
}












