package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RunCreateTemplateDTO {
	private String defaultSegment;
	private String regressionVal;
	private List<String> dropdownValues = new ArrayList<String>();
	private CheckBox data = new CheckBox();
	private CheckBox business = new CheckBox();
	private CheckBox regression = new CheckBox();

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CheckBox {
		private Boolean isDisable = false;
		private Boolean checked = false;
	}

}
