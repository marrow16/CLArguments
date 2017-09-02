package com.adeptions.arguments;

import java.util.*;

public interface Argument<T> {
	ArgumentDefinition<T> getDefinition();

	boolean isSpecified();
	void setSpecified();
	T getValue();
	void setRawValue(String rawValue, ArgName specifiedArgName) throws ArgParsingException;
	void addValue(T value);
	List<T> getAllValues();

	Arguments getParentArguments();
	void setParentArguments(Arguments arguments);
}