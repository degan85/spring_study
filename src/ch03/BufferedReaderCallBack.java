package ch03;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallBack {
	Integer doSomethingWithReader(BufferedReader rd) throws IOException;
}
