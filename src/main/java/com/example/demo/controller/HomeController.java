package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.AnotherNameOnly;
import com.example.demo.dto.NameOnly;
import com.example.demo.dto.RunCreateTemplateDTO;
import com.example.demo.dto.SegmentRefData;
import com.example.demo.dto.SegmentResult;
import com.example.demo.repository.DepartmentRepository;

@RestController
@CrossOrigin
public class HomeController {

	@Value("${welcome.message}")
	private String message;

	// @Autowired
	// private DepartmentRepository departmentRepository;

	@GetMapping("/")
	public String serverUp() throws Exception {
		return "server up";
	}

	@GetMapping("/createTemplate1")
	public List<SegmentRefData> createTemplateData() throws Exception {
		try {
			List<SegmentRefData> segmentRefDatas = getSegmentRefData();
			List<SegmentResult> segmentResults = getSegmentResult();
			for (SegmentRefData refData : segmentRefDatas) {
				List<String> list = new ArrayList<String>();
				for (SegmentResult result : segmentResults) {
					if (refData.getSegmentName().equalsIgnoreCase(result.getSegmentName())) {
						list.add(result.getMdlParameters() + " rank: " + result.getRank());
					}
				}
				refData.setDropdownValues(list);
			}

			return segmentRefDatas;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);
		}
	}

	private static List<SegmentRefData> getSegmentRefData() {
		List<SegmentRefData> segmentResults = new ArrayList<SegmentRefData>();
		segmentResults.add(new SegmentRefData("NON_LEGAL_CARD", new ArrayList<>()));
		segmentResults.add(new SegmentRefData("NON_LEGAL_WHOLESALE", new ArrayList<>()));

		return segmentResults;
	}

	private static List<SegmentResult> getSegmentResult() {
		List<SegmentResult> segmentResults = new ArrayList<SegmentResult>();
		segmentResults
				.add(new SegmentResult(123, "NON_LEGAL_WHOLESALE", "NON_LEGAL_WHOLESALE_174", "NON_LEGAL_DATA_2", 4.5));
		segmentResults.add(new SegmentResult(23, "NON_LEGAL_CARD", "NON_LEGAL_CARD_174", "NON_LEGAL_CARD_1", 1.5));
		segmentResults.add(
				new SegmentResult(123, "NON_LEGAL_WHOLESALE", "NON_LEGAL_WHOLESALE_174", "NON_LEGAL_DATA_3", 5.75));
		segmentResults.add(
				new SegmentResult(123, "NON_LEGAL_WHOLESALE", "NON_LEGAL_WHOLESALE_174", "NON_LEGAL_DATA_1", null));
		segmentResults
				.sort(Comparator.comparing(SegmentResult::getRank, Comparator.nullsLast(Comparator.naturalOrder())));

		return segmentResults;
	}

	@GetMapping("/createTemplate")
	public List<RunCreateTemplateDTO> createTemplate() {
		List<RunCreateTemplateDTO> createTemplateDTOs = new ArrayList<RunCreateTemplateDTO>();
		RunCreateTemplateDTO createTemplateDTO = new RunCreateTemplateDTO();
		createTemplateDTO.setRegressionVal("");
		createTemplateDTO.setDefaultSegment("data");
		createTemplateDTO.setData(new RunCreateTemplateDTO.CheckBox(true, false));
		createTemplateDTO.setBusiness(new RunCreateTemplateDTO.CheckBox(false, false));
		RunCreateTemplateDTO.CheckBox regressionCheckbox = new RunCreateTemplateDTO.CheckBox();
		regressionCheckbox.setChecked(true);
		regressionCheckbox.setIsDisable(true);
		createTemplateDTO.setRegression(regressionCheckbox);
		createTemplateDTOs.add(createTemplateDTO);
		RunCreateTemplateDTO createTemplateDTO1 = new RunCreateTemplateDTO();
		createTemplateDTO1.setRegressionVal("");
		createTemplateDTO1.setDefaultSegment("business");
		createTemplateDTO1.setData(new RunCreateTemplateDTO.CheckBox(true, false));
		createTemplateDTO1.setBusiness(new RunCreateTemplateDTO.CheckBox(false, false));

		createTemplateDTO1.setRegression(new RunCreateTemplateDTO.CheckBox(false, false));
		createTemplateDTOs.add(createTemplateDTO1);

		return createTemplateDTOs;
	}

//	@GetMapping("/test")
//	public NameOnly checkNamedRepo() {
//		NameOnly obj = departmentRepository.findByNativeQuery(1);
////		ModelMapper modelMapper = new ModelMapper();
////		NameO bookDTO = modelMapper.map(book, BookDto.class);
//		return obj;
//	}

	// @GetMapping("/test2")
	// public List<NameOnly> checkNamedRep2o() {
	// 	List<AnotherNameOnly> anotherNameOnlies = departmentRepository.findByNativeQueryAnother(1);
	// 	List<NameOnly> list = new ArrayList<>();
	// 	for (AnotherNameOnly anotherNameOnly : anotherNameOnlies) {
	// 		NameOnly nameOnly = new NameOnly();
	// 		nameOnly.setProduct_name("Object " + anotherNameOnly.getProdName());
	// 		nameOnly.setProduct_desc("Object " + anotherNameOnly.getProdDesc());
	// 		list.add(nameOnly);
	// 	}
	// 	return list;
	// }

}
