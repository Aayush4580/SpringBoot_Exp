package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunCreateTemplateDTO {
	private String defaultSegment;
	private String segmentTitle;
	private String regressionVal;
	@Builder.Default
	private List<String> dropdownValues = new ArrayList<String>();
	@Builder.Default
	private CheckBox data = new CheckBox();
	@Builder.Default
	private CheckBox business = new CheckBox();
	@Builder.Default
	private CheckBox regression = new CheckBox();

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CheckBox {
		private Boolean isDisable = false;
		private Boolean checked = false;
	}

}
