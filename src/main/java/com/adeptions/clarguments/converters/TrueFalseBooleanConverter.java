/*
 * Copyright 2017 Martin Rowlinson. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.adeptions.clarguments.converters;

import com.adeptions.clarguments.*;
import com.adeptions.clarguments.arguments.*;

import static com.adeptions.clarguments.BadArgReason.*;

/**
 * Utility value converter for converting raw "true"/"false" values to boolean
 */
public class TrueFalseBooleanConverter implements ArgumentValueConverter<Boolean> {
	private boolean allowNulls;
	private Boolean assumeNullValue;

	/**
	 * Constructs an TrueFalseBooleanConverter with default values
	 */
	public TrueFalseBooleanConverter() {
		this.allowNulls = allowNulls;
	}

	/**
	 * Constructs an TrueFalseBooleanConverter with specified allowNulls
	 * @param allowNulls whether nulls should be allowed
	 */
	public TrueFalseBooleanConverter(boolean allowNulls) {
		this.allowNulls = allowNulls;
	}

	/**
	 * Constructs an TrueFalseBooleanConverter with specified allowNulls and assumeNullValue
	 * @param allowNulls whether nulls should be allowed
	 * @param assumeNullValue when the raw value is null (and nulls are allowed), the value to be assumed
	 */
	public TrueFalseBooleanConverter(boolean allowNulls, Boolean assumeNullValue) {
		this.allowNulls = allowNulls;
		this.assumeNullValue = assumeNullValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean convert(int tokenPosition, String rawValue, Argument argument, ArgName specifiedArgName) throws BadArgException {
		if (allowNulls && rawValue == null) {
			return assumeNullValue;
		}
		if (!"true".equals(rawValue) && !"false".equals(rawValue)) {
			throw new BadArgException(INVALID_VALUE, tokenPosition, "Value \"" + rawValue + "\" is invalid - must be either \"true\" or \"false\"", argument, specifiedArgName);
		}
		return "true".equals(rawValue);
	}
}
