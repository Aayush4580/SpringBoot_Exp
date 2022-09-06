package com.example.demo.dto;

import java.io.IOException;

public interface ProgressCallable {
	void onProgess(int percentage) throws IOException;
}
