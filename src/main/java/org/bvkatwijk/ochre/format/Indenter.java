package org.bvkatwijk.ochre.format;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Indenter {

	private final String indentation;

	public String indent(String code) {
		return Arrays.stream(code
				.split("\n"))
				.map(it -> indentLine(it))
				.map(it -> trimEmpty(it))
				.collect(Collectors.joining("\n"));
	}

	private String indentLine(String line) {
		return this.indentation + line;
	}

	private String trimEmpty(String it) {
		if (it.trim().isEmpty()) {
			return "";
		}
		return it;
	}
}
